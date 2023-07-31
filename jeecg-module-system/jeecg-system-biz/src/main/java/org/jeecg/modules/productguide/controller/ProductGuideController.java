package org.jeecg.modules.productguide.controller;

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
import org.jeecg.modules.guide.service.ITouristGuideService;
import org.jeecg.modules.productguide.entity.ProductGuide;
import org.jeecg.modules.productguide.service.IProductGuideService;

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
 * @Description: 产品导游关系表
 * @Author: jeecg-boot
 * @Date: 2023-07-13
 * @Version: V1.0
 */
@Api(tags = "产品导游关系表")
@RestController
@RequestMapping("/productguide/productGuide")
@Slf4j
public class ProductGuideController extends JeecgController<ProductGuide, IProductGuideService> {
    @Autowired
    private IProductGuideService productGuideService;

    @Autowired
    private ITouristGuideService touristGuideService;

    /**
     * 分页列表查询
     *
     * @param productGuide
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "产品导游关系表-分页列表查询")
    @ApiOperation(value = "产品导游关系表-分页列表查询", notes = "产品导游关系表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<ProductGuide>> queryPageList(ProductGuide productGuide,
                                                     @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                     HttpServletRequest req) {
        QueryWrapper<ProductGuide> queryWrapper = QueryGenerator.initQueryWrapper(productGuide, req.getParameterMap());
        Page<ProductGuide> page = new Page<ProductGuide>(pageNo, pageSize);
        IPage<ProductGuide> pageList = productGuideService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param productGuide
     * @return
     */
    @AutoLog(value = "产品导游关系表-添加")
    @ApiOperation(value = "产品导游关系表-添加", notes = "产品导游关系表-添加")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody ProductGuide productGuide) {
        productGuideService.save(productGuide);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param productGuide
     * @return
     */
    @AutoLog(value = "产品导游关系表-编辑")
    @ApiOperation(value = "产品导游关系表-编辑", notes = "产品导游关系表-编辑")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody ProductGuide productGuide) {
        productGuideService.updateById(productGuide);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "产品导游关系表-通过id删除")
    @ApiOperation(value = "产品导游关系表-通过id删除", notes = "产品导游关系表-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        productGuideService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "产品导游关系表-批量删除")
    @ApiOperation(value = "产品导游关系表-批量删除", notes = "产品导游关系表-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.productGuideService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "产品导游关系表-通过id查询")
    @ApiOperation(value = "产品导游关系表-通过id查询", notes = "产品导游关系表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<ProductGuide> queryById(@RequestParam(name = "id", required = true) String id) {
        ProductGuide productGuide = productGuideService.getById(id);
        if (productGuide == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(productGuide);
    }

    /**
     * 通过产品id查询导游信息
     *
     * @param productId
     * @return
     */
    //@AutoLog(value = "产品导游关系表-通过id查询")
    @ApiOperation(value = "产品导游关系表-通过产品id查询导游信息", notes = "产品导游关系表-通过产品id查询导游信息")
    @GetMapping(value = "/queryByProId")
    public Result<List<TouristGuide>> queryByProId(@RequestParam(name = "productId", required = true) String productId) {
        List<ProductGuide> list = productGuideService.list(new LambdaQueryWrapper<ProductGuide>().eq(ProductGuide::getProductId, productId));
        List<String> ids = list.stream().map(ProductGuide::getGuideId).collect(Collectors.toList());
        return Result.OK(touristGuideService.listByIds(ids));
    }

    /**
     * 导出excel
     *
     * @param request
     * @param productGuide
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ProductGuide productGuide) {
        return super.exportXls(request, productGuide, ProductGuide.class, "产品导游关系表");
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
        return super.importExcel(request, response, ProductGuide.class);
    }

}
