package org.jeecg.modules.productService.controller;

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
import org.jeecg.modules.product.entity.CostDescription;
import org.jeecg.modules.productService.entity.ProductService;
import org.jeecg.modules.productService.service.IProductServiceService;

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
 * @Description: 客服产品表
 * @Author: jeecg-boot
 * @Date:   2023-08-16
 * @Version: V1.0
 */
@Api(tags="客服产品表")
@RestController
@RequestMapping("/productService/productService")
@Slf4j
public class ProductServiceController extends JeecgController<ProductService, IProductServiceService> {
	@Autowired
	private IProductServiceService productServiceService;
	
	/**
	 * 分页列表查询
	 *
	 * @param productService
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "客服产品表-分页列表查询")
	@ApiOperation(value="客服产品表-分页列表查询", notes="客服产品表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<ProductService>> queryPageList(ProductService productService,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ProductService> queryWrapper = QueryGenerator.initQueryWrapper(productService, req.getParameterMap());
		Page<ProductService> page = new Page<ProductService>(pageNo, pageSize);
		IPage<ProductService> pageList = productServiceService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param productService
	 * @return
	 */
	@AutoLog(value = "客服产品表-添加")
	@ApiOperation(value="客服产品表-添加", notes="客服产品表-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody ProductService productService) {
		productServiceService.save(productService);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param productService
	 * @return
	 */
	@AutoLog(value = "客服产品表-编辑")
	@ApiOperation(value="客服产品表-编辑", notes="客服产品表-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody ProductService productService) {
		productServiceService.updateById(productService);
		return Result.OK("编辑成功!");
	}

	 /**
	  *  编辑/修改客服电话
	  *
	  * @param map
	  * @return
	  */
	 @AutoLog(value = "费用说明-修改客服电话")
	 @ApiOperation(value="费用说明-修改客服电话", notes="费用说明-修改客服电话")
	 @RequestMapping(value = "/update", method = {RequestMethod.POST})
	 public Result<String> update(@RequestBody Map<String, Object> map) {
		 String proId = (String) map.get("proId");
		 String phone = (String) map.get("phone");

		 ProductService service = productServiceService.getOne(new LambdaQueryWrapper<ProductService>().eq(ProductService::getProId, proId));
		 if (service == null){
			 service = new ProductService();
			 service.setProId(proId);
		 }
		 service.setPhone(phone);

		 productServiceService.remove(new LambdaQueryWrapper<ProductService>().eq(ProductService::getProId,proId));
		 productServiceService.save(service);

		 return Result.OK("编辑成功!");
	 }
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "客服产品表-通过id删除")
	@ApiOperation(value="客服产品表-通过id删除", notes="客服产品表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		productServiceService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "客服产品表-批量删除")
	@ApiOperation(value="客服产品表-批量删除", notes="客服产品表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.productServiceService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "客服产品表-通过id查询")
	@ApiOperation(value="客服产品表-通过id查询", notes="客服产品表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<ProductService> queryById(@RequestParam(name="id",required=true) String id) {
		ProductService productService = productServiceService.getById(id);
		if(productService==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(productService);
	}

	 /**
	  * 通过proId查询
	  *
	  * @param proId
	  * @return
	  */
	 //@AutoLog(value = "客服产品表-通过proId查询")
	 @ApiOperation(value="客服产品表-通过proId查询", notes="客服产品表-通过proId查询")
	 @GetMapping(value = "/queryByProId")
	 public Result<ProductService> queryByProId(@RequestParam(name="proId",required=true) String proId) {
		 ProductService productService = productServiceService.getOne(new LambdaQueryWrapper<ProductService>().eq(ProductService::getProId,proId));
		 //if(productService==null) {
			// return Result.error("未找到对应数据");
		 //}
		 return Result.OK(productService);
	 }

    /**
    * 导出excel
    *
    * @param request
    * @param productService
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ProductService productService) {
        return super.exportXls(request, productService, ProductService.class, "客服产品表");
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
        return super.importExcel(request, response, ProductService.class);
    }

}
