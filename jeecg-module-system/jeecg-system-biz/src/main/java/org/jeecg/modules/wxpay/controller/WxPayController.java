package org.jeecg.modules.wxpay.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.system.SystemUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.orders.entity.OrdersPaid;
import org.jeecg.modules.orders.entity.OrdersUnpaid;
import org.jeecg.modules.orders.mapper.OrdersPaidMapper;
import org.jeecg.modules.orders.mapper.OrdersUnpaidMapper;
import org.jeecg.modules.orders.service.IOrdersPaidService;
import org.jeecg.modules.orders.service.IOrdersUnpaidService;
import org.jeecg.modules.product.entity.Product;
import org.jeecg.modules.product.mapper.ProductMapper;
import org.jeecg.modules.product.service.IProductService;
import org.jeecg.modules.user.userinfo.entity.WxClientUserinfo;
import org.jeecg.modules.user.userinfo.mapper.WxClientUserinfoMapper;
import org.jeecg.modules.user.userinfo.service.IWxClientUserinfoService;
import org.jeecg.modules.wxpay.entity.WxPayConfig;
import org.jeecg.modules.wxpay.util.IpUtil;
import org.jeewx.api.wxstore.order.model.OrderInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author QiaoQi
 * @version 1.0
 */

@Api(tags = "微信小程序微信支付")
@RestController
@Slf4j
@RequestMapping("/wxpay/userpay")
public class WxPayController {

    @Autowired
    private IProductService productService;

    @Autowired
    private IOrdersUnpaidService ordersUnpaidService;

    @Autowired
    private IOrdersPaidService ordersPaidService;

    @Autowired
    private IWxClientUserinfoService wxClientUserinfoService;

    @PostMapping("/getOrder")
    @Transactional
    public Result<Map<String, String>> getOrder(HttpServletRequest http, @RequestBody OrdersUnpaid ordersInfo) throws Exception {
        String productId = http.getParameter("productId");
        String openid = http.getHeader("openid");
        log.info("ordersInfo===========================" + ordersInfo);
        //log.info("productId================================" + productId);
        //log.info("openid================================" + openid);

        // 查询该openid的用户信息
        LambdaQueryWrapper<WxClientUserinfo> queryUserWrapper = new LambdaQueryWrapper<>();
        queryUserWrapper.eq(WxClientUserinfo::getOpenid, openid);
        WxClientUserinfo wxClientUserinfo = wxClientUserinfoService.getOne(queryUserWrapper);

        // 查询该产品的基本信息
        Product product = productService.getById(productId);
        Double price = product.getProEvaluate();

        // 根据产品id和openid生成未付款订单
        OrdersUnpaid ordersUnpaid = new OrdersUnpaid();
        BeanUtils.copyProperties(ordersInfo, ordersUnpaid);
        ordersUnpaid.setProductId(productId).setUserId(wxClientUserinfo.getId()).setStatus(0);
        // 插入并返回该订单的id
        String orderId = "";
        boolean save = ordersUnpaidService.save(ordersUnpaid);
        if (save) {
            orderId = ordersUnpaid.getId();
        }

        // 填充接口请求的数据
        WxPayConfig wxPayConfig = new WxPayConfig();
        WXPay wxPay = new WXPay(wxPayConfig);
        HashMap<String, String> map = new HashMap<>();
        map.put("body", "随心游产品支付测试"); // 产品描述
        map.put("total_fee", String.valueOf((int) Math.round((ordersUnpaid.getPayingMoney() * 100)))); // 产品价格（单位：分）
        map.put("out_trade_no", orderId); // 商户订单号(这里就是未支付订单表中的id字段)
        map.put("notify_url", "http://you.xiuxiu365.cn:27000/jeecg-boot/wxpay/userpay/wxPayCallback"); // 通知地址
        map.put("trade_type", "JSAPI"); // 交易类型
        map.put("openid", openid); // 用户标识
        map.put("spbill_create_ip", IpUtil.getIpAddress(http)); // 终端IP
        Map<String, String> requestData = wxPay.fillRequestData(map);
        // 调用统一下单接口
        Map<String, String> respData = wxPay.unifiedOrder(requestData, 3000, 3000);

        // 打印并取出接口返回数据
        log.info(JSONObject.toJSONString(respData));
        String returnCode = respData.get("return_code");
        String resultCode = respData.get("result_code");
        String returnMsg = respData.get("return_msg");
        String prepayId = respData.get("prepay_id"); // 拿到prepayId


        if (CollUtil.isEmpty(respData) || !returnCode.equals("SUCCESS") ||
                !resultCode.equals("SUCCESS") || !returnMsg.equals("OK") || null == prepayId) {
            //微信支付下单失败
            return Result.error(respData.get("err_code_des"));
        }
        //下单成功去获取调起微信支付参数
        Map<String, String> returnMap = wechatCreatePay(prepayId);
        log.info(JSONObject.toJSONString(returnMap));
        if (CollUtil.isEmpty(returnMap) || returnMap.size() == 0) {
            return Result.error("支付调起参数异常");
        }
        return Result.ok(returnMap);
    }

    // 创建微信支付返回参数
    public Map<String, String> wechatCreatePay(String prepayId) {
        try {
            WxPayConfig wxPayConfig = new WxPayConfig();
            Map<String, String> wxPayMap = new HashMap<String, String>();
            wxPayMap.put("appId", wxPayConfig.getAppID());
            wxPayMap.put("timeStamp", String.valueOf(System.currentTimeMillis()));
            wxPayMap.put("nonceStr", WXPayUtil.generateNonceStr());
            wxPayMap.put("package", "prepay_id=" + prepayId);
            wxPayMap.put("signType", "MD5");
            // 加密串中包括 appId timeStamp nonceStr package signType 5个参数, 通过sdk WXPayUtil类加密, 注意, 此处使用  MD5加密  方式
            String sign = WXPayUtil.generateSignature(wxPayMap, wxPayConfig.getKey());
            //  返回给前端调起微信支付的必要参数
            Map<String, String> map = new HashMap<>();
            map.put("prepay_id", prepayId);
            map.put("paySign", sign);
            map.putAll(wxPayMap);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 用户支付成功，微信服务器会调用的方法，来通知微信服务器业务完成
    @PostMapping("/wxPayCallback")
    public String wechatPayCallback(HttpServletRequest request, HttpServletResponse response) {
        try {
            InputStream is = request.getInputStream();
            //将InputStream转换成String
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            String resXml = sb.toString();
            log.info("微信支付异步通知请求包------");
            log.info(resXml);
            return wechatPayBack(resXml);
        } catch (Exception e) {
            log.error("微信支付回调通知失败", e);
            String result = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[报文为空]]></return_msg></xml> ";
            return result;
        }
    }


    public String wechatPayBack(String xmlStr) {
        String xmlBack = "";
        Map<String, String> notifyMap = null;
        try {
            WxPayConfig wxPayConfig = new WxPayConfig();
            WXPay wxpay = new WXPay(wxPayConfig);

            notifyMap = WXPayUtil.xmlToMap(xmlStr);         // 转换成map
            if (wxpay.isPayResultNotifySignatureValid(notifyMap)) {
                log.info("签名成功 \r\n" + JSONObject.toJSONString(notifyMap));
                // 签名正确
                // 注意特殊情况：订单已经退款，但收到了支付结果成功的通知，不应把商户侧订单状态从退款改成支付成功

                //状态
                String returnCode = notifyMap.get("return_code").trim();
                // 业务结果
                String resultCode = notifyMap.get("result_code").trim();
                //系统支付订单编号（订单号）
                String outTradeNo = notifyMap.get("out_trade_no").trim();
                //交易类型
                String tradeType = notifyMap.get("trade_type").trim();
                //微信支付订单号
                String transactionId = notifyMap.get("transaction_id").trim();
                //支付金额
                String cashFee = notifyMap.get("cash_fee").trim();
                //订单金额
                String totalFee = notifyMap.get("total_fee").trim();
                //支付时间
                String timeEnd = notifyMap.get("time_end").trim();
                //用户标识
                String openid = notifyMap.get("openid").trim();

                // 格式化时间
                SimpleDateFormat format = new SimpleDateFormat("yyyy-HH-dd HH:mm:ss");
                Date date = new SimpleDateFormat("yyyy-HH-dd HH:mm:ss").parse(format.format(new SimpleDateFormat("yyyyHHddHHmmss").parse(timeEnd)));

                // 根据商户订单号查出未支付的这一条订单记录，把状态修改为1，表示已支付
                LambdaUpdateWrapper<OrdersUnpaid> unpaidLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
                unpaidLambdaUpdateWrapper.eq(OrdersUnpaid::getId, outTradeNo).set(OrdersUnpaid::getStatus, 1);
                ordersUnpaidService.update(unpaidLambdaUpdateWrapper);

                // 根据未付款的订单表查出订单相关信息
                LambdaQueryWrapper<OrdersUnpaid> unpaidLambdaQueryWrapper = new LambdaQueryWrapper<>();
                unpaidLambdaQueryWrapper.eq(OrdersUnpaid::getId, outTradeNo);
                OrdersUnpaid ordersUnpaid = ordersUnpaidService.getOne(unpaidLambdaQueryWrapper);
                String productId = ordersUnpaid.getProductId();
                String userid = ordersUnpaid.getUserId();
                Date dateStarted = ordersUnpaid.getDateStarted();
                Date dateClosed = ordersUnpaid.getDateClosed();
                Integer adultCount = ordersUnpaid.getAdultCount();
                Integer childrenCount = ordersUnpaid.getChildrenCount();
                String note = ordersUnpaid.getNote();

                // 在已支付订单表里面添加一条记录
                OrdersPaid ordersPaid = new OrdersPaid();
                ordersPaid.setTransactionId(transactionId)
                        .setUserId(userid)
                        .setProductId(productId)
                        .setPaidMoney(Double.valueOf(cashFee))
                        .setPaidMethod("微信支付")
                        .setDateStarted(dateStarted)
                        .setDateClosed(dateClosed)
                        .setAdultCount(adultCount)
                        .setChildrenCount(childrenCount)
                        .setNote(note)
                        .setCreateTime(date);
                ordersPaidService.save(ordersPaid);

                // 通知微信官方接口，业务完成
                log.info("微信支付回调成功订单号: {}", notifyMap);
                xmlBack = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
                return xmlBack;
            } else {
                log.error("微信支付回调通知签名错误");
                xmlBack = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[报文为空]]></return_msg></xml> ";
                return xmlBack;
            }
        } catch (Exception e) {
            log.error("微信支付回调通知失败", e);
            xmlBack = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[报文为空]]></return_msg></xml> ";
        }
        return xmlBack;
    }

}
