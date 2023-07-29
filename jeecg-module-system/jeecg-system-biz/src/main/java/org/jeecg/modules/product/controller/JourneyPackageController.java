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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.product.entity.BatchPackage;
import org.jeecg.modules.product.entity.JourneyPackage;
import org.jeecg.modules.product.service.IJourneyPackageService;
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
 * @Description: 行程套餐
 * @Author: jeecg-boot
 * @Date: 2023-07-14
 * @Version: V1.0
 */
@Api(tags = "行程套餐")
@RestController
@RequestMapping("/core/journeyPackage")
@Slf4j
public class JourneyPackageController extends JeecgController<JourneyPackage, IJourneyPackageService> {
    @Autowired
    private IJourneyPackageService journeyPackageService;

    /**
     * 分页列表查询
     *
     * @param journeyPackage
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "行程套餐-分页列表查询")
    @ApiOperation(value = "行程套餐-分页列表查询", notes = "行程套餐-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<JourneyPackage>> queryPageList(JourneyPackage journeyPackage,
                                                       @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                       @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                       HttpServletRequest req) {
        QueryWrapper<JourneyPackage> queryWrapper = QueryGenerator.initQueryWrapper(journeyPackage, req.getParameterMap());
        Page<JourneyPackage> page = new Page<JourneyPackage>(pageNo, pageSize);
        IPage<JourneyPackage> pageList = journeyPackageService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param journeyPackage
     * @return
     */
    @AutoLog(value = "行程套餐-添加")
    @ApiOperation(value = "行程套餐-添加", notes = "行程套餐-添加")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody JourneyPackage journeyPackage) {
        journeyPackageService.save(journeyPackage);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param journeyPackage
     * @return
     */
    @AutoLog(value = "行程套餐-编辑")
    @ApiOperation(value = "行程套餐-编辑", notes = "行程套餐-编辑")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody JourneyPackage journeyPackage) {
        journeyPackageService.updateById(journeyPackage);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "行程套餐-通过id删除")
    @ApiOperation(value = "行程套餐-通过id删除", notes = "行程套餐-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        journeyPackageService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "行程套餐-批量删除")
    @ApiOperation(value = "行程套餐-批量删除", notes = "行程套餐-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.journeyPackageService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "行程套餐-通过id查询")
    @ApiOperation(value = "行程套餐-通过id查询", notes = "行程套餐-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<JourneyPackage> queryById(@RequestParam(name = "id", required = true) String id) {
        JourneyPackage journeyPackage = journeyPackageService.getById(id);
        if (journeyPackage == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(journeyPackage);
    }

    /**
     * 通过产品id查询
     *
     * @param proId
     * @return
     */
    //@AutoLog(value = "行程套餐-通过产品id查询")
    @ApiOperation(value = "行程套餐-通过产品id查询", notes = "行程套餐-通过产品id查询")
    @GetMapping(value = "/queryByProId")
    public Result<List<JourneyPackage>> queryByProId(@RequestParam(name = "proId", required = true) String proId) {
        LambdaQueryWrapper<JourneyPackage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(JourneyPackage::getProId, proId);
        List<JourneyPackage> list = journeyPackageService.list(wrapper);
        if (list == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(list);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param journeyPackage
     */
    @RequiresPermissions("core:journey_package:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, JourneyPackage journeyPackage) {
        return super.exportXls(request, journeyPackage, JourneyPackage.class, "行程套餐");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequiresPermissions("core:journey_package:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, JourneyPackage.class);
    }

}
