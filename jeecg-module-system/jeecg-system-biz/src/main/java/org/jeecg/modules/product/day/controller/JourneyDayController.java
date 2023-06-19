package org.jeecg.modules.product.day.controller;

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
import org.jeecg.modules.product.day.bo.JourneyDayBo;
import org.jeecg.modules.product.day.entity.JourneyDay;
import org.jeecg.modules.product.day.service.IJourneyDayService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.product.task.entity.JourneyTask;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.BeanUtils;
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
 * @Description: 旅游日程表
 * @Author: jeecg-boot
 * @Date:   2023-06-14
 * @Version: V1.0
 */
@Api(tags="旅游日程表")
@RestController
@RequestMapping("/day/journeyDay")
@Slf4j
public class JourneyDayController extends JeecgController<JourneyDay, IJourneyDayService> {
	@Autowired
	private IJourneyDayService journeyDayService;
	
	/**
	 * 分页列表查询
	 *
	 * @param journeyDay
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "旅游日程表-分页列表查询")
	@ApiOperation(value="旅游日程表-分页列表查询", notes="旅游日程表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<JourneyDay>> queryPageList(JourneyDay journeyDay,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<JourneyDay> queryWrapper = QueryGenerator.initQueryWrapper(journeyDay, req.getParameterMap());
		Page<JourneyDay> page = new Page<JourneyDay>(pageNo, pageSize);
		IPage<JourneyDay> pageList = journeyDayService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param journeyDay
	 * @return
	 */
	@AutoLog(value = "旅游日程表-添加")
	@ApiOperation(value="旅游日程表-添加", notes="旅游日程表-添加")
//	@RequiresPermissions("day:journey_day:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody JourneyDay journeyDay) {
		journeyDayService.save(journeyDay);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param journeyDay
	 * @return
	 */
	@AutoLog(value = "旅游日程表-编辑")
	@ApiOperation(value="旅游日程表-编辑", notes="旅游日程表-编辑")
//	@RequiresPermissions("day:journey_day:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody JourneyDay journeyDay) {
		journeyDayService.updateById(journeyDay);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "旅游日程表-通过id删除")
	@ApiOperation(value="旅游日程表-通过id删除", notes="旅游日程表-通过id删除")
//	@RequiresPermissions("day:journey_day:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		journeyDayService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "旅游日程表-批量删除")
	@ApiOperation(value="旅游日程表-批量删除", notes="旅游日程表-批量删除")
//	@RequiresPermissions("day:journey_day:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.journeyDayService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "旅游日程表-通过id查询")
	@ApiOperation(value="旅游日程表-通过id查询", notes="旅游日程表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<JourneyDay> queryById(@RequestParam(name="id",required=true) String id) {
		JourneyDay journeyDay = journeyDayService.getById(id);
		if(journeyDay==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(journeyDay);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param journeyDay
    */
//    @RequiresPermissions("day:journey_day:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, JourneyDay journeyDay) {
        return super.exportXls(request, journeyDay, JourneyDay.class, "旅游日程表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
//    @RequiresPermissions("day:journey_day:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, JourneyDay.class);
    }




}
