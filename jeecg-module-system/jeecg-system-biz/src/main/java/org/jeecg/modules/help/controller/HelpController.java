package org.jeecg.modules.help.controller;

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
import org.jeecg.modules.help.entity.Help;
import org.jeecg.modules.help.service.IHelpService;

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
 * @Description: 帮助中心表
 * @Author: jeecg-boot
 * @Date:   2023-08-12
 * @Version: V1.0
 */
@Api(tags="帮助中心表")
@RestController
@RequestMapping("/help/help")
@Slf4j
public class HelpController extends JeecgController<Help, IHelpService> {
	@Autowired
	private IHelpService helpService;
	
	/**
	 * 列表查询
	 *
	 * @return
	 */
	//@AutoLog(value = "帮助中心表-列表查询")
	@ApiOperation(value="帮助中心表-列表查询", notes="帮助中心表-列表查询")
	@GetMapping(value = "/list")
	public Result<List<Help>> queryList() {
		List<Help> list = helpService.list();
		return Result.OK(list);
	}



	 /**
	  * 列表查询
	  *
	  * @return
	  */
	 //@AutoLog(value = "帮助中心表-列表查询")
	 @ApiOperation(value="联系我们表-分页列表查询", notes="联系我们表-分页列表查询")
	 @GetMapping(value = "/listPage")
	 public Result<IPage<Help>> listPage(@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
											  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize) {
		 Page<Help> page = new Page<Help>(pageNo, pageSize);
		 IPage<Help> pageList = helpService.page(page);
		 return Result.OK(pageList);
	 }
	
	/**
	 *   添加
	 *
	 * @param help
	 * @return
	 */
	@AutoLog(value = "帮助中心表-添加")
	@ApiOperation(value="帮助中心表-添加", notes="帮助中心表-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody Help help) {
		helpService.save(help);
		String id = help.getId();
		return Result.OK("添加成功！",id);
	}
	
	/**
	 *  编辑
	 *
	 * @param help
	 * @return
	 */
	@AutoLog(value = "帮助中心表-编辑")
	@ApiOperation(value="帮助中心表-编辑", notes="帮助中心表-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody Help help) {
		helpService.updateById(help);
		return Result.OK("编辑成功!");
	}

	 /**
	  *  保存帮助中心数据
	  *
	  * @return
	  */
	 @AutoLog(value = "帮助中心表-保存帮助中心数据")
	 @ApiOperation(value="帮助中心表-保存帮助中心数据", notes="帮助中心表-保存帮助中心数据")
	 @PostMapping(value = "/updateHelps")
	 public Result<String> updateHelps(@RequestBody List<Help> helps) {
	 	if (helps.size() <= 0){
	 		return Result.error("数据为空！无法保存");
		}
		 for (Help help : helps) {
			 helpService.updateById(help);
		 }
		 return Result.OK("编辑成功!");
	 }
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "帮助中心表-通过id删除")
	@ApiOperation(value="帮助中心表-通过id删除", notes="帮助中心表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		helpService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "帮助中心表-批量删除")
	@ApiOperation(value="帮助中心表-批量删除", notes="帮助中心表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.helpService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "帮助中心表-通过id查询")
	@ApiOperation(value="帮助中心表-通过id查询", notes="帮助中心表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<Help> queryById(@RequestParam(name="id",required=true) String id) {
		Help help = helpService.getById(id);
		if(help==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(help);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param help
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Help help) {
        return super.exportXls(request, help, Help.class, "帮助中心表");
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
        return super.importExcel(request, response, Help.class);
    }

}
