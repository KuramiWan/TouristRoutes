package org.jeecg.modules.product.controller;

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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.product.entity.CostDescription;
import org.jeecg.modules.product.service.ICostDescriptionService;
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
 * @Description: 费用说明
 * @Author: jeecg-boot
 * @Date:   2023-07-14
 * @Version: V1.0
 */
@Api(tags="费用说明")
@RestController
@RequestMapping("/core/costDescription")
@Slf4j
public class CostDescriptionController extends JeecgController<CostDescription, ICostDescriptionService> {
	@Autowired
	private ICostDescriptionService costDescriptionService;
	
	/**
	 * 分页列表查询
	 *
	 * @param costDescription
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "费用说明-分页列表查询")
	@ApiOperation(value="费用说明-分页列表查询", notes="费用说明-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<CostDescription>> queryPageList(CostDescription costDescription,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<CostDescription> queryWrapper = QueryGenerator.initQueryWrapper(costDescription, req.getParameterMap());
		Page<CostDescription> page = new Page<CostDescription>(pageNo, pageSize);
		IPage<CostDescription> pageList = costDescriptionService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param costDescription
	 * @return
	 */
	@AutoLog(value = "费用说明-添加")
	@ApiOperation(value="费用说明-添加", notes="费用说明-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody CostDescription costDescription) {
		costDescriptionService.save(costDescription);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param costDescription
	 * @return
	 */
	@AutoLog(value = "费用说明-编辑")
	@ApiOperation(value="费用说明-编辑", notes="费用说明-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody CostDescription costDescription) {
		costDescriptionService.updateById(costDescription);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "费用说明-通过id删除")
	@ApiOperation(value="费用说明-通过id删除", notes="费用说明-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		costDescriptionService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "费用说明-批量删除")
	@ApiOperation(value="费用说明-批量删除", notes="费用说明-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.costDescriptionService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "费用说明-通过id查询")
	@ApiOperation(value="费用说明-通过id查询", notes="费用说明-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<CostDescription> queryById(@RequestParam(name="id",required=true) String id) {
		CostDescription costDescription = costDescriptionService.getById(id);
		if(costDescription==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(costDescription);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param costDescription
    */
    @RequiresPermissions("core:cost_description:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CostDescription costDescription) {
        return super.exportXls(request, costDescription, CostDescription.class, "费用说明");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("core:cost_description:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, CostDescription.class);
    }

}
