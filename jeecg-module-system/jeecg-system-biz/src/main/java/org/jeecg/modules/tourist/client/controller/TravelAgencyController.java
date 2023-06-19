package org.jeecg.modules.tourist.client.controller;

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

import org.jeecg.modules.tourist.client.entity.TravelAgency;
import org.jeecg.modules.tourist.client.service.ITravelAgencyService;
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
 * @Description: 旅行社信息表
 * @Author: jeecg-boot
 * @Date: 2023-06-18
 * @Version: V1.0
 */
@Api(tags = "旅行社信息表")
@RestController
@RequestMapping("/client/travelAgency")
@Slf4j
public class TravelAgencyController extends JeecgController<TravelAgency, ITravelAgencyService> {
    @Autowired
    private ITravelAgencyService travelAgencyService;

    /**
     * 分页列表查询
     *
     * @param travelAgency
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "旅行社信息表-分页列表查询")
    @ApiOperation(value = "旅行社信息表-分页列表查询", notes = "旅行社信息表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<TravelAgency>> queryPageList(TravelAgency travelAgency,
                                                     @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                     HttpServletRequest req) {
        QueryWrapper<TravelAgency> queryWrapper = QueryGenerator.initQueryWrapper(travelAgency, req.getParameterMap());
        Page<TravelAgency> page = new Page<TravelAgency>(pageNo, pageSize);
        IPage<TravelAgency> pageList = travelAgencyService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param travelAgency
     * @return
     */
    @AutoLog(value = "旅行社信息表-添加")
    @ApiOperation(value = "旅行社信息表-添加", notes = "旅行社信息表-添加")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody TravelAgency travelAgency) {
        travelAgencyService.save(travelAgency);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param travelAgency
     * @return
     */
    @AutoLog(value = "旅行社信息表-编辑")
    @ApiOperation(value = "旅行社信息表-编辑", notes = "旅行社信息表-编辑")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody TravelAgency travelAgency) {
        travelAgencyService.updateById(travelAgency);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "旅行社信息表-通过id删除")
    @ApiOperation(value = "旅行社信息表-通过id删除", notes = "旅行社信息表-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        travelAgencyService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "旅行社信息表-批量删除")
    @ApiOperation(value = "旅行社信息表-批量删除", notes = "旅行社信息表-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.travelAgencyService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "旅行社信息表-通过id查询")
    @ApiOperation(value = "旅行社信息表-通过id查询", notes = "旅行社信息表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<TravelAgency> queryById(@RequestParam(name = "id", required = true) String id) {
        TravelAgency travelAgency = travelAgencyService.getById(id);
        if (travelAgency == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(travelAgency);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param travelAgency
     */
    @RequiresPermissions("agency:travel_agency:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, TravelAgency travelAgency) {
        return super.exportXls(request, travelAgency, TravelAgency.class, "旅行社信息表");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequiresPermissions("agency:travel_agency:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, TravelAgency.class);
    }

}
