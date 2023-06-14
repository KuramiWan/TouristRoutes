package org.jeecg.modules.product.task.controller;

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
import org.jeecg.modules.product.task.entity.JourneyTask;
import org.jeecg.modules.product.task.service.IJourneyTaskService;

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
 * @Description: 旅行任务表
 * @Author: jeecg-boot
 * @Date:   2023-06-14
 * @Version: V1.0
 */
@Api(tags="旅行任务表")
@RestController
@RequestMapping("/task/journeyTask")
@Slf4j
public class JourneyTaskController extends JeecgController<JourneyTask, IJourneyTaskService> {
	@Autowired
	private IJourneyTaskService journeyTaskService;
	
	/**
	 * 分页列表查询
	 *
	 * @param journeyTask
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "旅行任务表-分页列表查询")
	@ApiOperation(value="旅行任务表-分页列表查询", notes="旅行任务表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<JourneyTask>> queryPageList(JourneyTask journeyTask,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<JourneyTask> queryWrapper = QueryGenerator.initQueryWrapper(journeyTask, req.getParameterMap());
		Page<JourneyTask> page = new Page<JourneyTask>(pageNo, pageSize);
		IPage<JourneyTask> pageList = journeyTaskService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param journeyTask
	 * @return
	 */
	@AutoLog(value = "旅行任务表-添加")
	@ApiOperation(value="旅行任务表-添加", notes="旅行任务表-添加")
//	@RequiresPermissions("task:journey_task:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody JourneyTask journeyTask) {
		journeyTaskService.save(journeyTask);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param journeyTask
	 * @return
	 */
	@AutoLog(value = "旅行任务表-编辑")
	@ApiOperation(value="旅行任务表-编辑", notes="旅行任务表-编辑")
//	@RequiresPermissions("task:journey_task:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody JourneyTask journeyTask) {
		journeyTaskService.updateById(journeyTask);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "旅行任务表-通过id删除")
	@ApiOperation(value="旅行任务表-通过id删除", notes="旅行任务表-通过id删除")
//	@RequiresPermissions("task:journey_task:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		journeyTaskService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "旅行任务表-批量删除")
	@ApiOperation(value="旅行任务表-批量删除", notes="旅行任务表-批量删除")
//	@RequiresPermissions("task:journey_task:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.journeyTaskService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "旅行任务表-通过id查询")
	@ApiOperation(value="旅行任务表-通过id查询", notes="旅行任务表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<JourneyTask> queryById(@RequestParam(name="id",required=true) String id) {
		JourneyTask journeyTask = journeyTaskService.getById(id);
		if(journeyTask==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(journeyTask);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param journeyTask
    */
//    @RequiresPermissions("task:journey_task:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, JourneyTask journeyTask) {
        return super.exportXls(request, journeyTask, JourneyTask.class, "旅行任务表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
//    @RequiresPermissions("task:journey_task:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, JourneyTask.class);
    }

}
