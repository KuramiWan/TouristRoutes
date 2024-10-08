package org.jeecg.modules.orders.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.orders.entity.OrdersPaid;
import org.jeecg.modules.orders.entity.OrdersUnpaid;
import org.jeecg.modules.orders.service.IOrdersPaidService;
import org.jeecg.modules.orders.service.IOrdersUnpaidService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.orders.vo.OrdersAllDetails;
import org.jeecg.modules.orders.vo.OrdersPaidDetails;
import org.jeecg.modules.orders.vo.OrdersUnpaidDetails;
import org.jeecg.modules.user.userinfo.entity.WxClientUserinfo;
import org.jeecg.modules.user.userinfo.service.IWxClientUserinfoService;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

/**
 * @Description: 未付款的订单表
 * @Author: jeecg-boot
 * @Date: 2023-07-13
 * @Version: V1.0
 */
@Api(tags = "未付款的订单表")
@RestController
@RequestMapping("/orders/ordersUnpaid")
@Slf4j
public class OrdersUnpaidController extends JeecgController<OrdersUnpaid, IOrdersUnpaidService> {
    @Autowired
    private IOrdersUnpaidService ordersUnpaidService;

    @Autowired
    private IOrdersPaidService ordersPaidService;

    @Autowired
    private IWxClientUserinfoService wxClientUserinfoService;


    /**
     * 后台分页查询所有未付款订单信息
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "后台分页查询所有未付款订单信息-分页列表查询", notes = "后台分页查询所有未付款订单信息-分页列表查询")
    @GetMapping(value = "/listAllUnpaid")
    public Result<IPage<OrdersUnpaidDetails>> listAllUnpaid(
            @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize
    ) {

        Page<OrdersUnpaid> page = new Page<OrdersUnpaid>(pageNo, pageSize);
        IPage<OrdersUnpaidDetails> unpaidDetails = ordersUnpaidService.getOrderUnpaid(page, null);
        return Result.OK(unpaidDetails);
    }

    /**
     * 分页列表查询
     *
     * @param ordersUnpaid
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "未付款的订单表-分页列表查询")
    @ApiOperation(value = "未付款的订单表-分页列表查询", notes = "未付款的订单表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<OrdersUnpaid>> queryPageList(OrdersUnpaid ordersUnpaid,
                                                     @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                     HttpServletRequest req) {
        //QueryWrapper<OrdersUnpaid> queryWrapper = QueryGenerator.initQueryWrapper(ordersUnpaid, req.getParameterMap());
        Page<OrdersUnpaid> page = new Page<OrdersUnpaid>(pageNo, pageSize);
        IPage<OrdersUnpaid> pageList = ordersUnpaidService.page(page, new LambdaQueryWrapper<>());
        return Result.OK(pageList);
    }

    /**
     * 分页列表查询全部的订单表
     *
     * @param pageNo
     * @param pageSize
     * @param openid
     * @return
     */
    //@AutoLog(value = "全部的订单表-分页列表查询全部的订单表")
    @ApiOperation(value = "全部的订单表-分页列表查询全部的订单表", notes = "全部的订单表-分页列表查询全部的订单表")
    @GetMapping(value = "/listAllOrders")
    public Result<List<Object>> listAllOrders(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                              @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                              @RequestHeader(name = "openid") String openid) {
        // 根据openid查出userid
        LambdaQueryWrapper<WxClientUserinfo> wxClientUserinfoLambdaQueryWrapper = new LambdaQueryWrapper<WxClientUserinfo>().eq(WxClientUserinfo::getOpenid, openid);
        WxClientUserinfo userinfo = wxClientUserinfoService.getOne(wxClientUserinfoLambdaQueryWrapper);
        Page<OrdersUnpaid> page = new Page<OrdersUnpaid>(pageNo, pageSize);
        IPage<OrdersUnpaidDetails> unpaidDetails = ordersUnpaidService.getOrderUnpaid(page, userinfo);

        Page<OrdersPaid> page2 = new Page<OrdersPaid>(pageNo, pageSize);
        IPage<OrdersPaidDetails> paidDetails = ordersUnpaidService.getOrderAllPaid(page2, userinfo);

        ArrayList<Object> list = new ArrayList<>();
        list.add(unpaidDetails);
        list.add(paidDetails);

        return Result.OK(list);
    }

    /**
     * 分页列表查询未付款的订单表(废弃)
     *
     * @param pageNo
     * @param pageSize
     * @param openid
     * @return
     */
    //@AutoLog(value = "未付款的订单表-分页列表查询")
    @ApiOperation(value = "未付款的订单表-分页列表查询未付款的订单表", notes = "未付款的订单表-分页列表查询未付款的订单表")
    @GetMapping(value = "/listUnPaid")
    public Result<IPage<OrdersUnpaidDetails>> listUnPaid(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                         @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                         @RequestHeader(name = "openid") String openid) {
        // 根据openid查出userid
        LambdaQueryWrapper<WxClientUserinfo> wxClientUserinfoLambdaQueryWrapper = new LambdaQueryWrapper<WxClientUserinfo>().eq(WxClientUserinfo::getOpenid, openid);
        WxClientUserinfo userinfo = wxClientUserinfoService.getOne(wxClientUserinfoLambdaQueryWrapper);
        Page<OrdersUnpaid> page = new Page<OrdersUnpaid>(pageNo, pageSize);
        IPage<OrdersUnpaidDetails> unpaidDetails = ordersUnpaidService.getOrderUnpaid(page, userinfo);
        return Result.OK(unpaidDetails);
    }

    /**
     * 添加
     *
     * @param ordersUnpaid
     * @return
     */
    @AutoLog(value = "未付款的订单表-添加")
    @ApiOperation(value = "未付款的订单表-添加", notes = "未付款的订单表-添加")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody OrdersUnpaid ordersUnpaid) {
        ordersUnpaidService.save(ordersUnpaid);
        return Result.OK("添加成功！");
    }

    /**
     * 取消订单（状态为-1）
     *
     * @param id
     * @return
     */
    @AutoLog(value = "未付款的订单表-取消订单（状态为-1）")
    @ApiOperation(value = "未付款的订单表-取消订单（状态为-1）", notes = "未付款的订单表-取消订单（状态为-1）")
    @PostMapping(value = "/editUnOrder")
    public Result<String> editUnOrder(@RequestParam(name = "id", defaultValue = "1") String id) {
        OrdersUnpaid ordersUnpaid = ordersUnpaidService.getById(id);
        ordersUnpaid.setStatus(-1);
        ordersUnpaidService.updateById(ordersUnpaid);
        return Result.OK("更新成成功！");
    }

    /**
     * 编辑
     *
     * @param ordersUnpaid
     * @return
     */
    @AutoLog(value = "未付款的订单表-编辑")
    @ApiOperation(value = "未付款的订单表-编辑", notes = "未付款的订单表-编辑")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody OrdersUnpaid ordersUnpaid) {
        ordersUnpaidService.updateById(ordersUnpaid);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "未付款的订单表-通过id删除")
    @ApiOperation(value = "未付款的订单表-通过id删除", notes = "未付款的订单表-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        ordersUnpaidService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "未付款的订单表-批量删除")
    @ApiOperation(value = "未付款的订单表-批量删除", notes = "未付款的订单表-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.ordersUnpaidService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "未付款的订单表-通过id查询")
    @ApiOperation(value = "未付款的订单表-通过id查询", notes = "未付款的订单表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<OrdersUnpaid> queryById(@RequestParam(name = "id", required = true) String id) {
        OrdersUnpaid ordersUnpaid = ordersUnpaidService.getById(id);
        if (ordersUnpaid == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(ordersUnpaid);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param ordersUnpaid
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OrdersUnpaid ordersUnpaid) {
        return super.exportXls(request, ordersUnpaid, OrdersUnpaid.class, "未付款的订单表");
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
        return super.importExcel(request, response, OrdersUnpaid.class);
    }


    /**
     * 后台查询所有订单信息(未付款 status = 0 )
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    //@AutoLog(value = "未付款的订单表-分页列表查询")
    @ApiOperation(value = "后台查询所有订单信息(未付款 status = 0 和已付款 所有的)", notes = "后台查询所有订单信息(未付款 status = 0 和已付款 所有的)")
    @GetMapping(value = "/listAllUnPaidDetails")
    public Result<IPage<OrdersAllDetails>> listAllUnPaidDetails(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        Page<OrdersAllDetails> page = new Page<OrdersAllDetails>(pageNo, pageSize);
        IPage<OrdersAllDetails> unpaidDetails = ordersUnpaidService.getOrdersAllUnPaidDetails(page);
        return Result.OK(unpaidDetails);
    }


    /**
     * 后台查询所有订单信息(已付款 所有的)
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    //@AutoLog(value = "未付款的订单表-分页列表查询")
    @ApiOperation(value = "后台查询所有订单信息(未付款 status = 0 和已付款 所有的)", notes = "后台查询所有订单信息(未付款 status = 0 和已付款 所有的)")
    @GetMapping(value = "/listAllPaidDetails")
    public Result<IPage<OrdersAllDetails>> listAllPaidDetails(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                              @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        Page<OrdersAllDetails> page = new Page<OrdersAllDetails>(pageNo, pageSize);
        IPage<OrdersAllDetails> unpaidDetails = ordersUnpaidService.getOrdersAllPaidDetails(page);
        return Result.OK(unpaidDetails);
    }


    /**
     * 后台审核通过待确认订单
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "未付款的订单表-分页列表查询")
    @ApiOperation(value = "后台审核通过待确认订单", notes = "后台审核通过待确认订单")
    @GetMapping(value = "/toConfirmOrders")
    public Result<String> toConfirmOrders(@RequestParam String id) {
        OrdersPaid ordersPaid = ordersPaidService.getById(id);
        ordersPaid.setStatus(2);
        boolean b = ordersPaidService.updateById(ordersPaid);
        if (b) {
            return Result.OK("审核订单成功~");
        }

        return Result.error("审核订单失败~");
    }

    /**
     * 后台取消审核通过订单
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "未付款的订单表-分页列表查询")
    @ApiOperation(value = "后台取消审核通过订单", notes = "后台取消审核通过订单")
    @GetMapping(value = "/toCancelConfirmed")
    public Result<String> toCancelConfirmed(@RequestParam String id) {
        OrdersPaid ordersPaid = ordersPaidService.getById(id);
        ordersPaid.setStatus(1);
        boolean b = ordersPaidService.updateById(ordersPaid);
        if (b) {
            return Result.OK("取消审核成功~");
        }

        return Result.error("取消审核失败~");
    }

    /**
     * 后台未付款订单根据订单id搜索
     *
     * @param keyword
     * @return
     */
    @ApiOperation(value = "后台未付款订单根据订单id搜索", notes = "后台未付款订单根据订单id搜索")
    @GetMapping(value = "/searchByUnpaidOrdersId")
    public Result<IPage<OrdersAllDetails>> searchByUnpaidOrdersId(@RequestParam String keyword, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                  @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Page<OrdersAllDetails> page = new Page<OrdersAllDetails>(pageNo, pageSize);
        return Result.ok(ordersUnpaidService.getUnpaidOrdersBySearch(page, keyword));
    }

    /**
     * 后台已付款订单根据订单id搜索
     *
     * @param keyword
     * @return
     */
    @ApiOperation(value = "后台已付款订单根据订单id搜索", notes = "后台已付款订单根据订单id搜索")
    @GetMapping(value = "/searchByPaidOrdersId")
    public Result<IPage<OrdersAllDetails>> searchByPaidOrdersId(@RequestParam String keyword, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Page<OrdersAllDetails> page = new Page<OrdersAllDetails>(pageNo, pageSize);
        return Result.ok(ordersUnpaidService.getPaidOrdersBySearch(page, keyword));
    }

}
