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
import org.jeecg.modules.product.service.IBatchPackageService;
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
 * @Description: 批次套餐
 * @Author: jeecg-boot
 * @Date: 2023-07-14
 * @Version: V1.0
 */
@Api(tags = "批次套餐")
@RestController
@RequestMapping("/core/batchPackage")
@Slf4j
public class BatchPackageController extends JeecgController<BatchPackage, IBatchPackageService> {
    @Autowired
    private IBatchPackageService batchPackageService;

    /**
     * 分页列表查询
     *
     * @param batchPackage
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "批次套餐-分页列表查询")
    @ApiOperation(value = "批次套餐-分页列表查询", notes = "批次套餐-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<BatchPackage>> queryPageList(BatchPackage batchPackage,
                                                     @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                     HttpServletRequest req) {
        QueryWrapper<BatchPackage> queryWrapper = QueryGenerator.initQueryWrapper(batchPackage, req.getParameterMap());
        Page<BatchPackage> page = new Page<BatchPackage>(pageNo, pageSize);
        IPage<BatchPackage> pageList = batchPackageService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param batchPackage
     * @return
     */
    @AutoLog(value = "批次套餐-添加")
    @ApiOperation(value = "批次套餐-添加", notes = "批次套餐-添加")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody BatchPackage batchPackage) {
        batchPackageService.save(batchPackage);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param batchPackage
     * @return
     */
    @AutoLog(value = "批次套餐-编辑")
    @ApiOperation(value = "批次套餐-编辑", notes = "批次套餐-编辑")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody BatchPackage batchPackage) {
        batchPackageService.updateById(batchPackage);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "批次套餐-通过id删除")
    @ApiOperation(value = "批次套餐-通过id删除", notes = "批次套餐-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        batchPackageService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "批次套餐-批量删除")
    @ApiOperation(value = "批次套餐-批量删除", notes = "批次套餐-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.batchPackageService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "批次套餐-通过id查询")
    @ApiOperation(value = "批次套餐-通过id查询", notes = "批次套餐-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<BatchPackage> queryById(@RequestParam(name = "id", required = true) String id) {
        BatchPackage batchPackage = batchPackageService.getById(id);
        if (batchPackage == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(batchPackage);
    }

    /**
     * 通过产品id查询
     *
     * @param proId
     * @return
     */
    //@AutoLog(value = "批次套餐-通过id查询")
    @ApiOperation(value = "批次套餐-通过id查询", notes = "批次套餐-通过id查询")
    @GetMapping(value = "/queryByProId")
    public Result<List<BatchPackage>> queryByProId(@RequestParam(name = "proId", required = true) String proId) {
        LambdaQueryWrapper<BatchPackage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BatchPackage::getProId, proId);
        List<BatchPackage> list = batchPackageService.list(wrapper);
        if (list == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(list);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param batchPackage
     */
    @RequiresPermissions("core:batch_package:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BatchPackage batchPackage) {
        return super.exportXls(request, batchPackage, BatchPackage.class, "批次套餐");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequiresPermissions("core:batch_package:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, BatchPackage.class);
    }

}
