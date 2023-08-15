package org.jeecg.modules.ordersFee.controller;

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
import org.jeecg.modules.ordersFee.entity.OrdersFee;
import org.jeecg.modules.ordersFee.service.IOrdersFeeService;

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
 * @Description: 订单的费用明细
 * @Author: jeecg-boot
 * @Date:   2023-08-15
 * @Version: V1.0
 */
@Api(tags="订单的费用明细")
@RestController
@RequestMapping("/ordersFee/ordersFee")
@Slf4j
public class OrdersFeeController extends JeecgController<OrdersFee, IOrdersFeeService> {
	@Autowired
	private IOrdersFeeService ordersFeeService;
	
	/**
	 * 分页列表查询
	 *
	 * @param ordersFee
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "订单的费用明细-分页列表查询")
	@ApiOperation(value="订单的费用明细-分页列表查询", notes="订单的费用明细-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<OrdersFee>> queryPageList(OrdersFee ordersFee,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<OrdersFee> queryWrapper = QueryGenerator.initQueryWrapper(ordersFee, req.getParameterMap());
		Page<OrdersFee> page = new Page<OrdersFee>(pageNo, pageSize);
		IPage<OrdersFee> pageList = ordersFeeService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param ordersFee
	 * @return
	 */
	@AutoLog(value = "订单的费用明细-添加")
	@ApiOperation(value="订单的费用明细-添加", notes="订单的费用明细-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody OrdersFee ordersFee) {
		ordersFeeService.save(ordersFee);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param ordersFee
	 * @return
	 */
	@AutoLog(value = "订单的费用明细-编辑")
	@ApiOperation(value="订单的费用明细-编辑", notes="订单的费用明细-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody OrdersFee ordersFee) {
		ordersFeeService.updateById(ordersFee);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "订单的费用明细-通过id删除")
	@ApiOperation(value="订单的费用明细-通过id删除", notes="订单的费用明细-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		ordersFeeService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "订单的费用明细-批量删除")
	@ApiOperation(value="订单的费用明细-批量删除", notes="订单的费用明细-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.ordersFeeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "订单的费用明细-通过id查询")
	@ApiOperation(value="订单的费用明细-通过id查询", notes="订单的费用明细-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<OrdersFee> queryById(@RequestParam(name="id",required=true) String id) {
		OrdersFee ordersFee = ordersFeeService.getById(id);
		if(ordersFee==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(ordersFee);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param ordersFee
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OrdersFee ordersFee) {
        return super.exportXls(request, ordersFee, OrdersFee.class, "订单的费用明细");
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
        return super.importExcel(request, response, OrdersFee.class);
    }

}
