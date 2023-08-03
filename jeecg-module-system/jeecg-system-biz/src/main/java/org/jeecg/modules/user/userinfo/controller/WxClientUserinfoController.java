package org.jeecg.modules.user.userinfo.controller;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oss.OssBootUtil;
import org.jeecg.modules.orders.entity.OrdersPaid;
import org.jeecg.modules.orders.entity.OrdersUnpaid;
import org.jeecg.modules.orders.service.IOrdersPaidService;
import org.jeecg.modules.orders.service.IOrdersUnpaidService;
import org.jeecg.modules.product.entity.Product;
import org.jeecg.modules.product.service.IProductService;
import org.jeecg.modules.user.userinfo.entity.WxClientUserinfo;
import org.jeecg.modules.user.userinfo.service.IWxClientUserinfoService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.user.userinfo.vo.OrderList;
import org.jeecg.modules.user.userinfo.vo.UploadRequest;
import org.jeecg.modules.user.userinfo.vo.WxClientUserinfoVo;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: 微信客户端用户信息表
 * @Author: jeecg-boot
 * @Date: 2023-07-17
 * @Version: V1.0
 */
@Api(tags = "微信客户端用户信息表")
@RestController
@RequestMapping("/userinfo/wxClientUserinfo")
@Slf4j
public class WxClientUserinfoController extends JeecgController<WxClientUserinfo, IWxClientUserinfoService> {

    @Value("${wx.client.app-id}")
    private String appId;

    @Value("${wx.client.app-secret}")
    private String appSecret;

    @Autowired
    private IWxClientUserinfoService iWxClientUserinfoService;

    @ApiOperation(value = "微信客户端用户信息表-获取用户全部订单信息", notes = "微信客户端用户信息表-获取用户全部订单信息")
    @GetMapping(value = "/orderList")
    public Result<List<IPage<OrderList>>> orderList(@RequestHeader("openid") String openid, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        String userid = iWxClientUserinfoService.getOne(new LambdaQueryWrapper<WxClientUserinfo>().eq(WxClientUserinfo::getOpenid, openid)).getId();
        // 获取未支付订单列表
        Page<OrdersUnpaid> unpaidPage = new Page<>(pageNo,pageSize);
        IPage<OrderList> unpaidPageList = iWxClientUserinfoService.unPaid(userid, unpaidPage);
        // 获取待出行订单列表
        Page<OrdersPaid> unGoPage = new Page<>(pageNo,pageSize);
        IPage<OrderList> unGoPageList = iWxClientUserinfoService.unGo(userid, unGoPage);
        // 获取待评价订单列表
        Page<OrdersPaid> unEvaluate = new Page<>(pageNo,pageSize);
        IPage<OrderList> unEvaluateList = iWxClientUserinfoService.unEvaluate(userid, unEvaluate);

        List<IPage<OrderList>> page = new ArrayList<>();
        page.add(unpaidPageList);page.add(unGoPageList);page.add(unEvaluateList);
        return Result.OK(page);
    }

    @ApiOperation(value = "微信客户端用户信息表-获取用户未付款订单信息", notes = "微信客户端用户信息表-获取用户未付款订单信息")
    @GetMapping(value = "/unPaidOrderList")
    public Result<IPage<OrderList>> unPaid(@RequestHeader("openid") String openid, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        String userid = iWxClientUserinfoService.getOne(new LambdaQueryWrapper<WxClientUserinfo>().eq(WxClientUserinfo::getOpenid, openid)).getId();
        Page<OrdersUnpaid> ordersUnpaidPage = new Page<>(pageNo,pageSize);
        // 获取未支付订单列表
        IPage<OrderList> orderListIPage = iWxClientUserinfoService.unPaid(userid, ordersUnpaidPage);
        return Result.OK(orderListIPage);
    }
    @ApiOperation(value = "微信客户端用户信息表-获取用户待出行订单信息", notes = "微信客户端用户信息表-获取用户待出行订单信息")
    @GetMapping(value = "/unGoOrderList")
    public Result<IPage<OrderList>> unGo(@RequestHeader("openid") String openid, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        String userid = iWxClientUserinfoService.getOne(new LambdaQueryWrapper<WxClientUserinfo>().eq(WxClientUserinfo::getOpenid, openid)).getId();
        Page<OrdersPaid> ordersPaidPage = new Page<>(pageNo,pageSize);
        // 获取待出行订单列表
        IPage<OrderList> orderListIPage = iWxClientUserinfoService.unGo(userid, ordersPaidPage);
        return Result.OK(orderListIPage);
    }
    @ApiOperation(value = "微信客户端用户信息表-获取用户待评价订单信息", notes = "微信客户端用户信息表-获取用户待评价订单信息")
    @GetMapping(value = "/unEvaluateOrderList")
    public Result<IPage<OrderList>> unEvaluate(@RequestHeader("openid") String openid, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        String userid = iWxClientUserinfoService.getOne(new LambdaQueryWrapper<WxClientUserinfo>().eq(WxClientUserinfo::getOpenid, openid)).getId();
        Page<OrdersPaid> ordersPaidPage = new Page<>(pageNo,pageSize);
        // 获取待评价订单列表
        IPage<OrderList> orderListIPage = iWxClientUserinfoService.unEvaluate(userid, ordersPaidPage);
        return Result.OK(orderListIPage);
    }




    // 获取用户的openid，并将已有的信息返回给小程序
    @GetMapping("/getOpenId")
    public Result<WxClientUserinfoVo> getOpenId(String code, String username) throws IOException {
        //请求微信接口获取openid
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        HashMap map = new HashMap();
        map.put("appid", appId);
        map.put("secret", appSecret);
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");
        String response = HttpUtil.post(url, map);
        JSONObject json = JSONUtil.parseObj(response);
        String openId = json.getStr("openid");
        String sessionKey = json.getStr("session_key");
        if (openId == null || openId.length() == 0)
            throw new RuntimeException("临时登录凭证错误");
        WxClientUserinfoVo wxClientUserinfoVo = iWxClientUserinfoService.login(openId, username, sessionKey);
        return Result.ok(wxClientUserinfoVo);
    }

    // 用户绑定手机号
    @GetMapping("/savePhone")
    public String savePhone(String id, String phone) {
        QueryWrapper<WxClientUserinfo> clientUserinfoQueryWrapper = new QueryWrapper<WxClientUserinfo>().eq("id", id);
        WxClientUserinfo clientUserinfoServiceOne = iWxClientUserinfoService.getOne(clientUserinfoQueryWrapper);
        clientUserinfoServiceOne.setPhone(phone);
        iWxClientUserinfoService.updateById(clientUserinfoServiceOne);
        return "success";
    }

    @RequestMapping("/uploadImg")
    public Result<String> uploadImg(@RequestBody UploadRequest uploadRequest) {
        try {
            String base64Data = uploadRequest.getBase64Data();
            // 将Base64数据转换为字节数组
            byte[] imageData = Base64.getDecoder().decode(base64Data);
            String fileDir = "suixinyou-wx-client/pages/user/用户头像/"; // 文件保存目录，根据实际情况调整
            String fileUrl = OssBootUtil.upload(uploadRequest.getOpenid(),imageData, fileDir);
            if (fileUrl != null) {
                LambdaQueryWrapper<WxClientUserinfo> queryWrapper = new LambdaQueryWrapper<WxClientUserinfo>().eq(WxClientUserinfo::getOpenid, uploadRequest.getOpenid());
                WxClientUserinfo target = iWxClientUserinfoService.getOne(queryWrapper);
                target.setAvatar(fileUrl);
                iWxClientUserinfoService.update(target,queryWrapper);
                return Result.ok(fileUrl);
            } else {
                return Result.error("上传失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("上传失败");
        }
    }
    /**
     * 分页列表查询
     *
     * @param wxClientUserinfo
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "微信客户端用户信息表-分页列表查询")
    @ApiOperation(value = "微信客户端用户信息表-分页列表查询", notes = "微信客户端用户信息表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<WxClientUserinfo>> queryPageList(WxClientUserinfo wxClientUserinfo,
                                                         @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                         @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                         HttpServletRequest req) {
        QueryWrapper<WxClientUserinfo> queryWrapper = QueryGenerator.initQueryWrapper(wxClientUserinfo, req.getParameterMap());
        Page<WxClientUserinfo> page = new Page<WxClientUserinfo>(pageNo, pageSize);
        IPage<WxClientUserinfo> pageList = iWxClientUserinfoService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param wxClientUserinfo
     * @return
     */
    @AutoLog(value = "微信客户端用户信息表-添加")
    @ApiOperation(value = "微信客户端用户信息表-添加", notes = "微信客户端用户信息表-添加")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody WxClientUserinfo wxClientUserinfo) {
        iWxClientUserinfoService.save(wxClientUserinfo);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param wxClientUserinfo
     * @return
     */
    @AutoLog(value = "微信客户端用户信息表-编辑")
    @ApiOperation(value = "微信客户端用户信息表-编辑", notes = "微信客户端用户信息表-编辑")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody WxClientUserinfo wxClientUserinfo) {
        iWxClientUserinfoService.updateById(wxClientUserinfo);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "微信客户端用户信息表-通过id删除")
    @ApiOperation(value = "微信客户端用户信息表-通过id删除", notes = "微信客户端用户信息表-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        iWxClientUserinfoService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "微信客户端用户信息表-批量删除")
    @ApiOperation(value = "微信客户端用户信息表-批量删除", notes = "微信客户端用户信息表-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.iWxClientUserinfoService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "微信客户端用户信息表-通过id查询")
    @ApiOperation(value = "微信客户端用户信息表-通过id查询", notes = "微信客户端用户信息表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<WxClientUserinfo> queryById(@RequestParam(name = "id", required = true) String id) {
        WxClientUserinfo wxClientUserinfo = iWxClientUserinfoService.getById(id);
        if (wxClientUserinfo == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(wxClientUserinfo);
    }


    /**
     * 通过open_id查询
     *
     * @param open_id
     * @return
     */
    //@AutoLog(value = "微信客户端用户信息表-通过id查询")
    @ApiOperation(value = "微信客户端用户信息表-通过id查询", notes = "微信客户端用户信息表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<WxClientUserinfo> queryById(@RequestParam(name = "id", required = true) String id) {
        WxClientUserinfo wxClientUserinfo = iWxClientUserinfoService.getById(id);
        if (wxClientUserinfo == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(wxClientUserinfo);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param wxClientUserinfo
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WxClientUserinfo wxClientUserinfo) {
        return super.exportXls(request, wxClientUserinfo, WxClientUserinfo.class, "微信客户端用户信息表");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, WxClientUserinfo.class);
    }

}
