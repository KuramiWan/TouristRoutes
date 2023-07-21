package org.jeecg.modules.guide.controller;

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
import org.jeecg.modules.guide.entity.TouristGuide;
import org.jeecg.modules.guide.mapper.TouristGuideMapper;
import org.jeecg.modules.guide.service.ITouristGuideService;

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
 * @Description: 导游表
 * @Author: jeecg-boot
 * @Date: 2023-07-13
 * @Version: V1.0
 */
@Api(tags = "导游表")
@RestController
@RequestMapping("/guide/touristGuide")
@Slf4j
public class TouristGuideController extends JeecgController<TouristGuide, ITouristGuideService> {
    @Autowired
    private ITouristGuideService touristGuideService;
    @Autowired
    private TouristGuideMapper touristGuideMapper;

    /**
     * 分页列表查询
     *
     * @param touristGuide
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "导游表-分页列表查询")
    @ApiOperation(value = "导游表-分页列表查询", notes = "导游表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<TouristGuide>> getPageList(TouristGuide touristGuide,
                                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                   HttpServletRequest req) {
        IPage<TouristGuide> page = new Page<>(pageNo, pageSize);
        List<TouristGuide> collectPageList = touristGuideMapper.selectList(new LambdaQueryWrapper<TouristGuide>())
                .stream().skip((long) (pageNo - 1) * pageSize).limit(pageSize).collect(Collectors.toList());
        page.setRecords(collectPageList);
        page.setTotal(collectPageList.size());
        return Result.OK(page);
    }

    /**
     * 添加
     *
     * @param touristGuide
     * @return
     */
    @AutoLog(value = "导游表-添加")
    @ApiOperation(value = "导游表-添加", notes = "导游表-添加")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody TouristGuide touristGuide) {
        touristGuideService.save(touristGuide);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param touristGuide
     * @return
     */
    @AutoLog(value = "导游表-编辑")
    @ApiOperation(value = "导游表-编辑", notes = "导游表-编辑")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody TouristGuide touristGuide) {
        touristGuideService.updateById(touristGuide);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "导游表-通过id删除")
    @ApiOperation(value = "导游表-通过id删除", notes = "导游表-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        touristGuideService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "导游表-批量删除")
    @ApiOperation(value = "导游表-批量删除", notes = "导游表-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.touristGuideService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "导游表-通过id查询")
    @ApiOperation(value = "导游表-通过id查询", notes = "导游表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<TouristGuide> queryById(@RequestParam(name = "id", required = true) String id) {
        TouristGuide touristGuide = touristGuideService.getById(id);
        if (touristGuide == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(touristGuide);
    }


    /**
     * 导出excel
     *
     * @param request
     * @param touristGuide
     */
    @RequiresPermissions("guide:tourist_guide:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, TouristGuide touristGuide) {
        return super.exportXls(request, touristGuide, TouristGuide.class, "导游表");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequiresPermissions("guide:tourist_guide:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, TouristGuide.class);
    }

}
