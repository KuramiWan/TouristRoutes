package org.jeecg.modules.product.controller;

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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.product.entity.Product;
import org.jeecg.modules.product.entity.Schedule;
import org.jeecg.modules.product.mapper.ProductMapper;
import org.jeecg.modules.product.service.IProductService;
import org.jeecg.modules.product.service.IScheduleService;
import org.jeecg.modules.product.service.ITaskService;
import org.jeecg.modules.product.vo.ProductList;
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
 * @Description: 产品表
 * @Author: jeecg-boot
 * @Date:   2023-07-14
 * @Version: V1.0
 */
@Api(tags="产品表")
@RestController
@RequestMapping("/core/product")
@Slf4j
public class ProductController extends JeecgController<Product, IProductService> {
	@Autowired
	private IProductService productService;
	
	@Autowired
	private IScheduleService scheduleService;

	@Autowired
	private ProductMapper productMapper;
	/**
	 * 分页列表查询
	 *
	 * @param
	 * @param pageNo
	 * @param pageSize
	 * @param
	 * @return
	 */
	//@AutoLog(value = "产品表-分页列表查询")
	@ApiOperation(value="产品表-分页列表查询", notes="产品表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<Product>> queryPageList(
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize) {
//		QueryWrapper<Product> queryWrapper = QueryGenerator.initQueryWrapper(product, req.getParameterMap());
		Page<Product> page = new Page<Product>(pageNo, pageSize);
		IPage pageList = productService.page(page,new LambdaQueryWrapper<>());
		List<Product> records = pageList.getRecords();
		ArrayList<ProductList> productLists = new ArrayList<ProductList>();
		records.forEach(product ->{
			ProductList productList = new ProductList();
			List<Schedule> list = scheduleService.list(new LambdaQueryWrapper<Schedule>().eq(Schedule::getProId, product.getId()));
			int size = list.size();
			productList.setId(product.getId())
					.setOrigin(product.getOrigin())
					.setProEvaluate(product.getProEvaluate())
					.setProMan(product.getProMan())
					.setProPageTitle(product.getProPageTitle())
					.setSellNumber(0)
					.setSpots(size);
			productLists.add(productList);
		} );
		pageList.setRecords(productLists);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param product
	 * @return
	 */
	@AutoLog(value = "产品表-添加")
	@ApiOperation(value="产品表-添加", notes="产品表-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody Product product) {
		productService.save(product);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param product
	 * @return
	 */
	@AutoLog(value = "产品表-编辑")
	@ApiOperation(value="产品表-编辑", notes="产品表-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody Product product) {
		productService.updateById(product);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "产品表-通过id删除")
	@ApiOperation(value="产品表-通过id删除", notes="产品表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		productService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "产品表-批量删除")
	@ApiOperation(value="产品表-批量删除", notes="产品表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.productService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "产品表-通过id查询")
	@ApiOperation(value="产品表-通过id查询", notes="产品表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<Product> queryById(@RequestParam(name="id",required=true) String id) {
		Product product = productService.getById(id);
		if(product==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(product);
	}


	 /**
	  * 通过proName查询
	  *
	  * @param proName
	  * @return
	  */
	 //@AutoLog(value = "产品表-通过proName查询")
	 @ApiOperation(value="产品表-通过proName查询", notes="产品表-通过proName查询")
	 @GetMapping(value = "/queryByProName")
	 public Result<List<Product>> queryByProName(@RequestParam(name="proName",required=true) String proName) {
		 List<Product> products = productMapper.selectList(new LambdaQueryWrapper<Product>().like(Product::getProTitle, proName));
		 if(products==null || products.size() <=0) {
			 return Result.error("未找到对应数据");
		 }

		 return Result.OK(products);
	 }

    /**
    * 导出excel
    *
    * @param request
    * @param product
    */
    @RequiresPermissions("core:product:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Product product) {
        return super.exportXls(request, product, Product.class, "产品表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("core:product:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, Product.class);
    }

}
