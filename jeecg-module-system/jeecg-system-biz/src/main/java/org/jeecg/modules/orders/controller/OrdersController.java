package org.jeecg.modules.orders.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.orders.entity.Orders;
import org.jeecg.modules.orders.resp.OrdersResp;
import org.jeecg.modules.orders.service.IOrdersRespService;
import org.jeecg.modules.orders.service.IOrdersService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

/**
 * @Description: 订单表
 * @Author: jeecg-boot
 * @Date: 2023-06-19
 * @Version: V1.0
 */
@Api(tags = "订单表")
@RestController
@RequestMapping("/orders/orders")
@Slf4j
public class OrdersController extends JeecgController<Orders, IOrdersService> {
    @Autowired
    private IOrdersService ordersService;

    @Autowired
    private IOrdersRespService ordersRespService;

    /**
     * 分页列表查询
     *
     * @param orders
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "订单表-分页列表查询")
    @ApiOperation(value = "订单表-分页列表查询", notes = "订单表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<Orders>> queryPageList(Orders orders,
                                               @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                               @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                               HttpServletRequest req) {
        QueryWrapper<Orders> queryWrapper = QueryGenerator.initQueryWrapper(orders, req.getParameterMap());
        Page<Orders> page = new Page<Orders>(pageNo, pageSize);
        IPage<Orders> pageList = ordersService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 分页列表查询(详细信息返回)
     *
     * @param orders
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "订单表-分页列表查询(详细信息返回)")
    @ApiOperation(value = "订单表-分页列表查询(详细信息返回)", notes = "订单表-分页列表查询(详细信息返回)")
    @GetMapping(value = "/getList")
    public Result<List<OrdersResp>> getList(Orders orders,
                                            @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                            HttpServletRequest req) throws InvocationTargetException, IllegalAccessException {
        QueryWrapper<Orders> queryWrapper = QueryGenerator.initQueryWrapper(orders, req.getParameterMap());
        Page<Orders> page = new Page<Orders>(pageNo, pageSize);
        List<OrdersResp> pageList = ordersRespService.getList(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param orders
     * @return
     */
    @AutoLog(value = "订单表-添加")
    @ApiOperation(value = "订单表-添加", notes = "订单表-添加")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody Orders orders) {
        ordersService.save(orders);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param orders
     * @return
     */
    @AutoLog(value = "订单表-编辑")
    @ApiOperation(value = "订单表-编辑", notes = "订单表-编辑")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody Orders orders) {
        ordersService.updateById(orders);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "订单表-通过id删除")
    @ApiOperation(value = "订单表-通过id删除", notes = "订单表-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        ordersService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "订单表-批量删除")
    @ApiOperation(value = "订单表-批量删除", notes = "订单表-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.ordersService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "订单表-通过id查询")
    @ApiOperation(value = "订单表-通过id查询", notes = "订单表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<Orders> queryById(@RequestParam(name = "id", required = true) String id) {
        Orders orders = ordersService.getById(id);
        if (orders == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(orders);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param orders
     */
    @RequiresPermissions("orders:orders:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Orders orders) {
        return super.exportXls(request, orders, Orders.class, "订单表");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequiresPermissions("orders:orders:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, Orders.class);
    }

}
