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
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.product.entity.Schedule;
import org.jeecg.modules.product.service.IScheduleService;
import org.jeecg.modules.product.vo.ScheduleProVo;
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
 * @Description: 产品日程
 * @Author: jeecg-boot
 * @Date: 2023-07-14
 * @Version: V1.0
 */
@Api(tags = "产品日程")
@RestController
@RequestMapping("/core/schedule")
@Slf4j
public class ScheduleController extends JeecgController<Schedule, IScheduleService> {
    @Autowired
    private IScheduleService scheduleService;

    /**
     * 分页列表查询
     *
     * @param schedule
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "产品日程-分页列表查询")
    @ApiOperation(value = "产品日程-分页列表查询", notes = "产品日程-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<Schedule>> queryPageList(Schedule schedule,
                                                 @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                 @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                 HttpServletRequest req) {
//		QueryWrapper<Schedule> queryWrapper = QueryGenerator.initQueryWrapper(schedule, req.getParameterMap());
        Page<Schedule> page = new Page<Schedule>(pageNo, pageSize);
        IPage<Schedule> pageList = scheduleService.page(page, new LambdaQueryWrapper<>());
        return Result.OK(pageList);
    }


    /**
     * 给一个产品添加/编辑日程和任务
     *
     * @param scheduleProVo
     * @return
     */
    @AutoLog(value = "产品日程-添加/编辑日程和任务")
    @ApiOperation(value = "产品日程-添加/编辑日程和任务", notes = "产品日程-添加/编辑日程和任务")
    @PostMapping(value = "/addOrEdit")
    public Result<String> addOrEdit(@RequestBody ScheduleProVo scheduleProVo) {
        // 如果没有传过来日程id，说明这是添加操作, 否则是编辑操作
        //if (("").equals(scheduleProVo.getId()) || scheduleProVo.getId() == null) {
        //    boolean a = scheduleService.addScheduleAndTask(scheduleProVo);
        //    if (a) return Result.OK("添加成功！");
        //}
        log.info("scheduleProVo=====================" + scheduleProVo);
        boolean e = scheduleService.editScheduleAndTask(scheduleProVo);
        if (e) return Result.OK("编辑成功！");
        return Result.OK("异常错误！");
    }

    /**
     * 添加
     *
     * @param schedule
     * @return
     */
    @AutoLog(value = "产品日程-添加")
    @ApiOperation(value = "产品日程-添加", notes = "产品日程-添加")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody Schedule schedule) {
        scheduleService.save(schedule);
        return Result.OK("添加成功！");
    }

    /**
     * 根据产品id添加日程，并且返回日程id
     *
     * @param map
     * @return
     */
    @AutoLog(value = "产品日程-根据产品id添加日程")
    @ApiOperation(value = "产品日程-根据产品id添加日程", notes = "产品日程-根据产品id添加日程")
    @PostMapping(value = "/addByProId")
    public Result<List<String>> add(@RequestBody Map<String, String> map) {
        ArrayList<String> list = new ArrayList<>();
        String id = scheduleService.addScheduleByProId(map.get("proId"));
        list.add(id);
        return Result.OK(list);
    }

    /**
     * 编辑
     *
     * @param schedule
     * @return
     */
    @AutoLog(value = "产品日程-编辑")
    @ApiOperation(value = "产品日程-编辑", notes = "产品日程-编辑")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody Schedule schedule) {
        scheduleService.updateById(schedule);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过日程id删除日程和相关任务
     *
     * @param scheduleProVo
     * @return
     */
    @AutoLog(value = "产品日程-通过日程id删除日程和相关任务")
    @ApiOperation(value = "产品日程-通过日程id删除日程和相关任务", notes = "产品日程-通过日程id删除日程和相关任务")
    @DeleteMapping(value = "/deleteById")
    public Result<String> deleteById(@RequestBody ScheduleProVo scheduleProVo) {
        log.info("scheduleProVo========" + scheduleProVo);
        scheduleService.deleteScheduleAndTask(scheduleProVo);
        return Result.OK("删除成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "产品日程-通过id删除")
    @ApiOperation(value = "产品日程-通过id删除", notes = "产品日程-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        scheduleService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "产品日程-批量删除")
    @ApiOperation(value = "产品日程-批量删除", notes = "产品日程-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.scheduleService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "产品日程-通过id查询")
    @ApiOperation(value = "产品日程-通过id查询", notes = "产品日程-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<Schedule> queryById(@RequestParam(name = "id", required = true) String id) {
        Schedule schedule = scheduleService.getById(id);
        if (schedule == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(schedule);
    }

    /**
     * 通过proId查询
     *
     * @param proId
     * @return
     */
    @AutoLog(value = "产品日程-通过proId查询所有行程")
    @ApiOperation(value = "产品日程-通过proId查询所有行程", notes = "产品日程-通过proId查询所有行程")
    @GetMapping(value = "/queryListByProId")
    public Result<List<Schedule>> queryListByProId(@RequestParam(name = "proId", required = true) String proId) {
        List<Schedule> list = scheduleService.list(new LambdaQueryWrapper<Schedule>().eq(Schedule::getProId, proId));

        if (list.size() <= 0) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(list);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param schedule
     */
    @RequiresPermissions("core:schedule:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Schedule schedule) {
        return super.exportXls(request, schedule, Schedule.class, "产品日程");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequiresPermissions("core:schedule:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, Schedule.class);
    }

}
