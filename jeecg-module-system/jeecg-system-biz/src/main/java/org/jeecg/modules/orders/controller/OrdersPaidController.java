package org.jeecg.modules.orders.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.orders.entity.OrdersPaid;
import org.jeecg.modules.orders.service.IOrdersPaidService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

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
 * @Description: 已付款的订单表
 * @Author: jeecg-boot
 * @Date:   2023-07-13
 * @Version: V1.0
 */
@Api(tags="已付款的订单表")
@RestController
@RequestMapping("/orders/ordersPaid")
@Slf4j
public class OrdersPaidController extends JeecgController<OrdersPaid, IOrdersPaidService> {
	@Autowired
	private IOrdersPaidService ordersPaidService;
	
	/**
	 * 分页列表查询
	 *
	 * @param ordersPaid
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "已付款的订单表-分页列表查询")
	@ApiOperation(value="已付款的订单表-分页列表查询", notes="已付款的订单表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<OrdersPaid>> queryPageList(OrdersPaid ordersPaid,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<OrdersPaid> queryWrapper = QueryGenerator.initQueryWrapper(ordersPaid, req.getParameterMap());
		Page<OrdersPaid> page = new Page<OrdersPaid>(pageNo, pageSize);
		IPage<OrdersPaid> pageList = ordersPaidService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param ordersPaid
	 * @return
	 */
	@AutoLog(value = "已付款的订单表-添加")
	@ApiOperation(value="已付款的订单表-添加", notes="已付款的订单表-添加")
	@RequiresPermissions("orders:orders_paid:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody OrdersPaid ordersPaid) {
		ordersPaidService.save(ordersPaid);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param ordersPaid
	 * @return
	 */
	@AutoLog(value = "已付款的订单表-编辑")
	@ApiOperation(value="已付款的订单表-编辑", notes="已付款的订单表-编辑")
	@RequiresPermissions("orders:orders_paid:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody OrdersPaid ordersPaid) {
		ordersPaidService.updateById(ordersPaid);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "已付款的订单表-通过id删除")
	@ApiOperation(value="已付款的订单表-通过id删除", notes="已付款的订单表-通过id删除")
	@RequiresPermissions("orders:orders_paid:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		ordersPaidService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "已付款的订单表-批量删除")
	@ApiOperation(value="已付款的订单表-批量删除", notes="已付款的订单表-批量删除")
	@RequiresPermissions("orders:orders_paid:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.ordersPaidService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "已付款的订单表-通过id查询")
	@ApiOperation(value="已付款的订单表-通过id查询", notes="已付款的订单表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<OrdersPaid> queryById(@RequestParam(name="id",required=true) String id) {
		OrdersPaid ordersPaid = ordersPaidService.getById(id);
		if(ordersPaid==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(ordersPaid);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param ordersPaid
    */
    @RequiresPermissions("orders:orders_paid:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OrdersPaid ordersPaid) {
        return super.exportXls(request, ordersPaid, OrdersPaid.class, "已付款的订单表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("orders:orders_paid:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, OrdersPaid.class);
    }

}
