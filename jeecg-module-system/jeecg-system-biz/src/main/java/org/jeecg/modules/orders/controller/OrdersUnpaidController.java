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
     * 分页列表查询未付款的订单表
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

}
