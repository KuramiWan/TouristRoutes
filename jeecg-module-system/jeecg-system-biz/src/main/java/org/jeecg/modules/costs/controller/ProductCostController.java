package org.jeecg.modules.costs.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.costs.entity.ProductCost;
import org.jeecg.modules.costs.service.IProductCostService;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

 /**
 * @Description: 产品费用说明
 * @Author: jeecg-boot
 * @Date:   2023-07-02
 * @Version: V1.0
 */
@Api(tags="产品费用说明")
@RestController
@RequestMapping("/Costs/productCost")
@Slf4j
public class ProductCostController extends JeecgController<ProductCost, IProductCostService> {
	@Autowired
	private IProductCostService productCostService;
	
	/**
	 * 分页列表查询
	 *
	 * @param productCost
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "产品费用说明-分页列表查询")
	@ApiOperation(value="产品费用说明-分页列表查询", notes="产品费用说明-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<ProductCost>> queryPageList(ProductCost productCost,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ProductCost> queryWrapper = QueryGenerator.initQueryWrapper(productCost, req.getParameterMap());
		Page<ProductCost> page = new Page<ProductCost>(pageNo, pageSize);
		IPage<ProductCost> pageList = productCostService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param productCost
	 * @return
	 */
	@AutoLog(value = "产品费用说明-添加")
	@ApiOperation(value="产品费用说明-添加", notes="产品费用说明-添加")
	@RequiresPermissions("Costs:product_cost:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody ProductCost productCost) {
		productCostService.save(productCost);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param productCost
	 * @return
	 */
	@AutoLog(value = "产品费用说明-编辑")
	@ApiOperation(value="产品费用说明-编辑", notes="产品费用说明-编辑")
	@RequiresPermissions("Costs:product_cost:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody ProductCost productCost) {
		productCostService.updateById(productCost);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "产品费用说明-通过id删除")
	@ApiOperation(value="产品费用说明-通过id删除", notes="产品费用说明-通过id删除")
	@RequiresPermissions("Costs:product_cost:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		productCostService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "产品费用说明-批量删除")
	@ApiOperation(value="产品费用说明-批量删除", notes="产品费用说明-批量删除")
	@RequiresPermissions("Costs:product_cost:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.productCostService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "产品费用说明-通过id查询")
	@ApiOperation(value="产品费用说明-通过id查询", notes="产品费用说明-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<ProductCost> queryById(@RequestParam(name="id",required=true) String id) {
		ProductCost productCost = productCostService.getById(id);
		if(productCost==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(productCost);
	}

	 /**
	  * 通过productId查询
	  *
	  * @param productId
	  * @return
	  */
//@AutoLog(value = "产品费用说明-通过productId查询")
	 @ApiOperation(value="产品费用说明-通过productId查询", notes="产品费用说明-通过productId查询")
	 @GetMapping(value = "/queryByProductId")
	 public Result<ProductCost> queryByProductId(@RequestParam(name="productId",required=true) String productId) {
		 ProductCost productCost = productCostService.queryByProductId(productId);
		 if(productCost==null) {
			 return Result.error("未找到对应数据");
		 }
		 return Result.OK(productCost);
	 }

    /**
    * 导出excel
    *
    * @param request
    * @param productCost
    */
    @RequiresPermissions("Costs:product_cost:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ProductCost productCost) {
        return super.exportXls(request, productCost, ProductCost.class, "产品费用说明");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("Costs:product_cost:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ProductCost.class);
    }

}
