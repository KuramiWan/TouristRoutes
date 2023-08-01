package org.jeecg.modules.purchaseNotes.controller;

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
import org.jeecg.modules.purchaseNotes.entity.PurchaseNotes;
import org.jeecg.modules.purchaseNotes.service.IPurchaseNotesService;

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
 * @Description: 购买须知
 * @Author: jeecg-boot
 * @Date:   2023-08-01
 * @Version: V1.0
 */
@Api(tags="购买须知")
@RestController
@RequestMapping("/purchaseNotes/purchaseNotes")
@Slf4j
public class PurchaseNotesController extends JeecgController<PurchaseNotes, IPurchaseNotesService> {
	@Autowired
	private IPurchaseNotesService purchaseNotesService;
	
	/**
	 * 分页列表查询
	 *
	 * @param purchaseNotes
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "购买须知-分页列表查询")
	@ApiOperation(value="购买须知-分页列表查询", notes="购买须知-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<PurchaseNotes>> queryPageList(PurchaseNotes purchaseNotes,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<PurchaseNotes> queryWrapper = QueryGenerator.initQueryWrapper(purchaseNotes, req.getParameterMap());
		Page<PurchaseNotes> page = new Page<PurchaseNotes>(pageNo, pageSize);
		IPage<PurchaseNotes> pageList = purchaseNotesService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param purchaseNotes
	 * @return
	 */
	@AutoLog(value = "购买须知-添加")
	@ApiOperation(value="购买须知-添加", notes="购买须知-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody PurchaseNotes purchaseNotes) {
		purchaseNotesService.save(purchaseNotes);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param purchaseNotes
	 * @return
	 */
	@AutoLog(value = "购买须知-编辑")
	@ApiOperation(value="购买须知-编辑", notes="购买须知-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody PurchaseNotes purchaseNotes) {
		purchaseNotesService.updateById(purchaseNotes);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "购买须知-通过id删除")
	@ApiOperation(value="购买须知-通过id删除", notes="购买须知-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		purchaseNotesService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "购买须知-批量删除")
	@ApiOperation(value="购买须知-批量删除", notes="购买须知-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.purchaseNotesService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "购买须知-通过id查询")
	@ApiOperation(value="购买须知-通过id查询", notes="购买须知-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<PurchaseNotes> queryById(@RequestParam(name="id",required=true) String id) {
		PurchaseNotes purchaseNotes = purchaseNotesService.getById(id);
		if(purchaseNotes==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(purchaseNotes);
	}

	 /**
	  * 通过proId查询
	  *
	  * @param proId
	  * @return
	  */
	 //@AutoLog(value = "购买须知-通过proId查询")
	 @ApiOperation(value="购买须知-通过proId查询", notes="购买须知-通过proId查询")
	 @GetMapping(value = "/queryByProId")
	 public Result<PurchaseNotes> queryByProId(@RequestParam(name="proId",required=true) String proId) {
		 PurchaseNotes purchaseNotes = purchaseNotesService.getOne(new LambdaQueryWrapper<PurchaseNotes>().eq(PurchaseNotes::getProId,proId));
		 if(purchaseNotes==null) {
			 return Result.error("未找到对应数据");
		 }
		 return Result.OK(purchaseNotes);
	 }

    /**
    * 导出excel
    *
    * @param request
    * @param purchaseNotes
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, PurchaseNotes purchaseNotes) {
        return super.exportXls(request, purchaseNotes, PurchaseNotes.class, "购买须知");
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
        return super.importExcel(request, response, PurchaseNotes.class);
    }

}
