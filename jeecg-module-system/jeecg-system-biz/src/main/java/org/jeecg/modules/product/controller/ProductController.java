package org.jeecg.modules.product.controller;

import java.util.*;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.util.oss.OssBootUtil;
import org.jeecg.modules.orders.entity.OrdersPaid;
import org.jeecg.modules.orders.service.IOrdersPaidService;
import org.jeecg.modules.product.entity.Product;
import org.jeecg.modules.product.entity.Schedule;
import org.jeecg.modules.product.entity.TemporaryUpload;
import org.jeecg.modules.product.mapper.ProductMapper;
import org.jeecg.modules.product.service.IProductService;
import org.jeecg.modules.product.service.IScheduleService;
import org.jeecg.modules.product.service.ITaskService;
import org.jeecg.modules.product.vo.ProductList;
import org.jeecg.modules.product.vo.ProductUpload;
import org.jeecg.modules.product.vo.ProductVo;
import org.jeecg.modules.product.vo.PurchaseCountVo;
import org.jeecg.modules.user.userinfo.entity.WxClientUserinfo;
import org.jeecg.modules.user.userinfo.service.IWxClientUserinfoService;
import org.jeecg.modules.user.userinfo.vo.UploadRequest;
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
 * @Description: 产品表
 * @Author: jeecg-boot
 * @Date: 2023-07-14
 * @Version: V1.0
 */
@Api(tags = "产品表")
@RestController
@RequestMapping("/core/product")
@Slf4j
public class ProductController extends JeecgController<Product, IProductService> {
    @Autowired
    private IProductService productService;

    @Autowired
    private IScheduleService scheduleService;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private IOrdersPaidService ordersPaidService;

    @Autowired
    private IWxClientUserinfoService wxClientUserinfoService;

    /**
     * 分页列表查询
     *
     * @param
     * @param pageNo
     * @param pageSize
     * @param
     * @return
     */
    //@AutoLog(value = "产品表-分页列表查询")
    @ApiOperation(value = "产品表-分页列表查询", notes = "产品表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<Product>> queryPageList(
            @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
//		QueryWrapper<Product> queryWrapper = QueryGenerator.initQueryWrapper(product, req.getParameterMap());
        Page<Product> page = new Page<Product>(pageNo, pageSize);
        IPage pageList = productService.page(page, new LambdaQueryWrapper<>());
        List<Product> records = pageList.getRecords();
        ArrayList<ProductList> productLists = new ArrayList<ProductList>();
        records.forEach(product -> {
            ProductList productList = new ProductList();
            List<Schedule> list = scheduleService.list(new LambdaQueryWrapper<Schedule>().eq(Schedule::getProId, product.getId()));
            int size = list.size();

            //查出该产品有多少购买量
            LambdaQueryWrapper<OrdersPaid> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(OrdersPaid::getProductId, product.getId());
            int count = ordersPaidService.list(wrapper).size();
            productList.setId(product.getId())
                    .setOrigin(product.getOrigin())
                    .setProEvaluate(product.getProEvaluate())
                    .setProMan(product.getProMan())
                    .setProPageTitle(product.getProPageTitle())
                    .setSellNumber(count)
                    .setProPageImg(product.getProPageImg())
                    .setSpots(size);
            productLists.add(productList);
        });
        pageList.setRecords(productLists);
        return Result.OK(pageList);
    }




    /**
     * 产品表-分页列表查询产品(出售数量降序查询)
     *
     * @param
     * @param pageNo
     * @param pageSize
     * @param
     * @return
     */
    //@AutoLog(value = "产品表-分页列表查询产品(出售数量降序查询))
    @ApiOperation(value = "产品表-分页列表查询产品(出售数量降序查询)", notes = "产品表-分页列表查询产品(出售数量降序查询)")
    @GetMapping(value = "/productList")
    public Result<IPage<Product>> queryProductPageList(
            @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Page<Product> page = new Page<Product>(pageNo, pageSize);
        IPage pageList = productService.getProductList(page);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param product
     * @return
     */
    @AutoLog(value = "产品表-添加")
    @ApiOperation(value = "产品表-添加", notes = "产品表-添加")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody Product product) {
        productService.save(product);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param product
     * @return
     */
    @AutoLog(value = "产品表-编辑")
    @ApiOperation(value = "产品表-编辑", notes = "产品表-编辑")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody Product product) {
        productService.updateById(product);
        return Result.OK("编辑成功!");
    }

    @AutoLog(value = "产品表-添加或修改")
    @ApiOperation(value = "产品表-添加或修改",notes = "产品表-添加或修改")
    @PostMapping(value = "/saveOrUpdate")
    public Result<List<String>> saveOrUpdate(@RequestBody Product product){
        //更新条件构造器
        UpdateWrapper<Product> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",product.getId());
        //先判断是否满足更新条件（是否有传进来的id的数据），若没有就走添加
        productService.saveOrUpdate(product,updateWrapper);

        //判断是否更新图片
        String pageImgHeader = product.getProPageImg().substring(0,5);
        String postersHeader = product.getPosters().substring(0,5);

        //测试
//        System.out.println("pageImgHeader:"+pageImgHeader);
//        System.out.println("posters:"+postersHeader);
//        System.out.println(pageImgHeader.equals("https"));
//        System.out.println(postersHeader.equals("https"));


        //先判断是否有更新图片
        //上传图床并更新数据库中的图片字段

        //最终返回的集合里面[0]是产品封面的url,[1]是产品海报的url

        List<String> urlList = new ArrayList<>();

        if(product.getProPageImg() != null && product.getProPageImg() != "" && !pageImgHeader.equals("https")) {
            ProductUpload productUpload = new ProductUpload();
            productUpload.setProductid(product.getId());
            productUpload.setBase64Data(product.getProPageImg());
            String fileDir = "suixinyou-wx-client/pages-product/产品封面/";
            String url = uploadImg(productUpload,fileDir);
            //若修改过图片则返回图片地址
            urlList.add(url);
        }else{
            urlList.add(product.getProPageImg());
        }
        if(product.getPosters() != null && product.getPosters() != "" && !postersHeader.equals("https")) {
            ProductUpload productUpload = new ProductUpload();
            productUpload.setProductid(product.getId());
            productUpload.setBase64Data(product.getPosters());
            String fileDir = "suixinyou-wx-client/pages-product/产品海报/";
            String url = uploadImg(productUpload,fileDir);
            //若修改过图片则返回图片地址
            urlList.add(url);
        }else{
            urlList.add(product.getPosters());
        }
        return Result.OK(urlList);
    }

    public String uploadImg(ProductUpload productUpload,String fileDir) {
        try {
            String base64Img = productUpload.getBase64Data();
            // 将Base64数据转换为字节数组
            byte[] img = Base64.getDecoder().decode(base64Img);
            //String fileDir = "suixinyou-wx-client/pages-product/产品封面/"; // 文件保存目录，根据实际情况调整
            String fileUrl = OssBootUtil.upload(productUpload.getProductid(),img, fileDir);

            if (fileUrl != null) {
                LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<Product>().eq(Product::getId, productUpload.getProductid());
                Product target = productService.getOne(queryWrapper);
                target.setProPageImg(fileUrl);
                productService.update(target,queryWrapper);
                return fileUrl;
            }
            return "上传图片失败";
        } catch (Exception e) {
            e.printStackTrace();
            return "上传图片失败";
        }
    }

    @AutoLog(value = "产品表-上传图片返回url")
    @ApiOperation(value = "产品表-上传图片返回url",notes = "产品表-上传图片返回url")
    @PostMapping(value = "/temporaryUploadImg")
    public String temporaryUploadImg(TemporaryUpload temporaryUpload) {
        try {
            String base64Img = temporaryUpload.getBase64Data();
            // 将Base64数据转换为字节数组
            byte[] img = Base64.getDecoder().decode(base64Img);
            //String fileDir = "suixinyou-wx-client/pages-product/产品封面/"; // 文件保存目录，根据实际情况调整
            String fileDir = (temporaryUpload.getWitch() == 0) ? "suixinyou-wx-client/pages-product/产品封面/" : "suixinyou-wx-client/pages-product/产品海报/";
            String fileUrl = OssBootUtil.upload(temporaryUpload.getProductid(),img, fileDir);

            if (fileUrl != null) {
                LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<Product>().eq(Product::getId, temporaryUpload.getProductid());
                Product target = productService.getOne(queryWrapper);
                target.setProPageImg(fileUrl);
                productService.update(target,queryWrapper);
                return fileUrl;
            }
            return "上传图片失败";
        } catch (Exception e) {
            e.printStackTrace();
            return "上传图片失败";
        }
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "产品表-通过id删除")
    @ApiOperation(value = "产品表-通过id删除", notes = "产品表-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        productService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "产品表-批量删除")
    @ApiOperation(value = "产品表-批量删除", notes = "产品表-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.productService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "产品表-通过id查询")
    @ApiOperation(value = "产品表-通过id查询", notes = "产品表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<ProductVo> queryById(@RequestParam(name = "id", required = true) String id) {
        if (id == null) {
            return Result.error("未传入id");
        }
        ProductVo product = productService.queryById(id);
        if (product == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(product);
    }


    /**
     * 通过proName查询
     *
     * @param proName
     * @return
     */
    //@AutoLog(value = "产品表-通过proName查询")
    @ApiOperation(value = "产品表-通过proName查询", notes = "产品表-通过proName查询")
    @GetMapping(value = "/queryByProName")
    public Result<IPage<Product>> queryByProName(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                @RequestParam(name = "proName", required = true) String proName) {
        Page<Product> page = new Page<Product>(pageNo, pageSize);
        IPage pageList = productService.page(page, new LambdaQueryWrapper<Product>().like(Product::getLocalDetail,proName).orderByDesc(Product::getSoldNumber));
        if (pageList == null || pageList.getSize() <= 0) {
            return Result.error("未找到对应数据");
        }

        return Result.OK(pageList);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param product
     */
    @RequiresPermissions("core:product:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Product product) {
        return super.exportXls(request, product, Product.class, "产品表");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequiresPermissions("core:product:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, Product.class);
    }

    @ApiOperation(value = "产品表-通过产品id查询产品购买量", notes = "产品表-通过产品id查询产品购买量")
    @GetMapping(value = "/getPurchaseCount")
    public Result<PurchaseCountVo> getPurchaseCount(@RequestParam(name = "proId", required = true) String proId) {
        IPage<OrdersPaid> page = ordersPaidService
                .page(new Page<OrdersPaid>(1, 5), new LambdaQueryWrapper<OrdersPaid>().eq(OrdersPaid::getProductId, proId).orderByDesc(OrdersPaid::getCreateTime));
        Long total = page.getTotal();
        List<String> userIds = page.getRecords().stream().map(OrdersPaid::getUserId).collect(Collectors.toList());
        List<WxClientUserinfo> users = wxClientUserinfoService.listByIds(userIds);
        List<String> avatars = users.stream().map(WxClientUserinfo::getAvatar).collect(Collectors.toList());
        PurchaseCountVo purchaseCountVo = new PurchaseCountVo().setCount(total).setAvatars(avatars);
        return Result.OK(purchaseCountVo);
    }
}
