package org.jeecg.modules.Insure.controller;

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
import org.jeecg.modules.Insure.entity.Insure;
import org.jeecg.modules.Insure.service.IInsureService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.product.entity.PriceDate;
import org.jeecg.modules.product.entity.Tag;
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
 * @Description: 保险
 * @Author: jeecg-boot
 * @Date:   2023-08-01
 * @Version: V1.0
 */
@Api(tags="保险")
@RestController
@RequestMapping("/Insure/insure")
@Slf4j
public class InsureController extends JeecgController<Insure, IInsureService> {
	@Autowired
	private IInsureService insureService;
	
	/**
	 * 分页列表查询
	 *
	 * @param insure
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "保险-分页列表查询")
	@ApiOperation(value="保险-分页列表查询", notes="保险-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<Insure>> queryPageList(Insure insure,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<Insure> queryWrapper = QueryGenerator.initQueryWrapper(insure, req.getParameterMap());
		Page<Insure> page = new Page<Insure>(pageNo, pageSize);
		IPage<Insure> pageList = insureService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param insure
	 * @return
	 */
	@AutoLog(value = "保险-添加")
	@ApiOperation(value="保险-添加", notes="保险-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody Insure insure) {
		insureService.save(insure);
		String id = insure.getId();
		return Result.OK("添加成功！",id);
	}
	
	/**
	 *  编辑
	 *
	 * @param insure
	 * @return
	 */
	@AutoLog(value = "保险-编辑")
	@ApiOperation(value="保险-编辑", notes="保险-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody Insure insure) {
		insureService.updateById(insure);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "保险-通过id删除")
	@ApiOperation(value="保险-通过id删除", notes="保险-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		insureService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "保险-批量删除")
	@ApiOperation(value="保险-批量删除", notes="保险-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.insureService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "保险-通过id查询")
	@ApiOperation(value="保险-通过id查询", notes="保险-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<Insure> queryById(@RequestParam(name="id",required=true) String id) {
		Insure insure = insureService.getById(id);
		if(insure==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(insure);
	}

	 /**
	  *   修改
	  *
	  * @param insures
	  * @return
	  */
	 @AutoLog(value = "后台查询日程价格表-修改")
	 @ApiOperation(value="后台查询日程价格表-修改", notes="后台查询日程价格表-修改")
	 @PostMapping(value = "/updateInsureList")
	 public Result<String> updateInsureList(@RequestBody List<Insure> insures) {
	 	if (insures.size()<=0){
	 		return Result.error("保存失败！");
		}
		 for (Insure insure : insures) {
		 	insureService.updateById(insure);

		 }
		 return Result.OK("保存成功！");
	 }

	 /**
	  * 通过proId查询
	  *
	  * @param proId
	  * @return
	  */
	 //@AutoLog(value = "保险-通过proId查询")
	 @ApiOperation(value="保险-通过proId查询", notes="保险-通过proId查询")
	 @GetMapping(value = "/queryListByProId")
	 public Result<List<Insure>> queryListByProId(@RequestParam(name="proId",required=true) String proId) {
		 List<Insure> list = insureService.list(new LambdaQueryWrapper<Insure>().eq(Insure::getProid, proId).orderByAsc(Insure::getPrice));
		 if(list==null || list.size() <=0) {
			 return Result.error("未找到对应数据");
		 }
		 return Result.OK(list);
	 }

    /**
    * 导出excel
    *
    * @param request
    * @param insure
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Insure insure) {
        return super.exportXls(request, insure, Insure.class, "保险");
    }

//	 /**
//	  *  保存列表通过productId
//	  *
//	  * @param productId
//	  * @return
//	  */
//	 @AutoLog(value = "产品标签表-保存列表通过productId")
//	 @ApiOperation(value="产品标签表-保存列表通过productId", notes="产品标签表-保存列表通过productId")
//	 @RequestMapping(value = "/update", method = {RequestMethod.POST,RequestMethod.POST})
//	 public Result<String> update(@RequestBody Map<String, Object> map) {
//		 String proId = (String) map.get("proId");
//		 List<String> valueList = (List<String>) map.get("value");
//		 insureService.remove(new LambdaQueryWrapper<Insure>().eq(Insure::getProid,proId));
//		 if(valueList.size() > 0){
//			 valueList.forEach(i -> {
//				 Insure insure = new Insure();
//				 insure.setProid(proId).setContent(i);
//				 insure.save(tag);
//			 });
//			 return Result.OK("修改成功！");
//		 }
//		 return Result.error("编辑失败!");
//	 }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, Insure.class);
    }

}
