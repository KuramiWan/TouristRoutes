package org.jeecg.modules.product.controller;

import java.text.SimpleDateFormat;
import java.util.*;
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

import org.jeecg.modules.product.entity.PriceDate;
import org.jeecg.modules.product.mapper.PriceDateMapper;
import org.jeecg.modules.product.service.IPriceDateService;
import org.jeecg.modules.product.vo.DateDetail;
import org.jeecg.modules.product.vo.PriceDateList;
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
 * @Description: 每天的产品价格表
 * @Author: jeecg-boot
 * @Date:   2023-07-14
 * @Version: V1.0
 */
@Api(tags="每天的产品价格表")
@RestController
@RequestMapping("/core/priceDate")
@Slf4j
public class PriceDateController extends JeecgController<PriceDate, IPriceDateService> {
	@Autowired
	private IPriceDateService priceDateService;

	@Autowired
	private PriceDateMapper priceDateMapper;
	
	/**
	 * 分页列表查询
	 *
	 * @param priceDate
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "每天的产品价格表-分页列表查询")
	@ApiOperation(value="每天的产品价格表-分页列表查询", notes="每天的产品价格表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<PriceDate>> queryPageList(PriceDate priceDate,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<PriceDate> queryWrapper = QueryGenerator.initQueryWrapper(priceDate, req.getParameterMap());
		Page<PriceDate> page = new Page<PriceDate>(pageNo, pageSize);
		IPage<PriceDate> pageList = priceDateService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param priceDate
	 * @return
	 */
	@AutoLog(value = "每天的产品价格表-添加")
	@ApiOperation(value="每天的产品价格表-添加", notes="每天的产品价格表-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody PriceDate priceDate) {
		priceDateService.save(priceDate);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param priceDate
	 * @return
	 */
	@AutoLog(value = "每天的产品价格表-编辑")
	@ApiOperation(value="每天的产品价格表-编辑", notes="每天的产品价格表-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody PriceDate priceDate) {
		priceDateService.updateById(priceDate);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "每天的产品价格表-通过id删除")
	@ApiOperation(value="每天的产品价格表-通过id删除", notes="每天的产品价格表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		priceDateService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "每天的产品价格表-批量删除")
	@ApiOperation(value="每天的产品价格表-批量删除", notes="每天的产品价格表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.priceDateService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "每天的产品价格表-通过id查询")
	@ApiOperation(value="每天的产品价格表-通过id查询", notes="每天的产品价格表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<PriceDate> queryById(@RequestParam(name="id",required=true) String id) {
		PriceDate priceDate = priceDateService.getById(id);
		if(priceDate==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(priceDate);
	}


	 /**
	  * 通过proId批量查询
	  *
	  * @param proId
	  * @return
	  */
	 //@AutoLog(value = "每天的产品价格表-通过proId批量查询")
	 @ApiOperation(value="每天的产品价格表-通过proId批量查询", notes="每天的产品价格表-通过proId批量查询")
	 @GetMapping(value = "/queryByProIds")
	 public Result<List<PriceDateList>> queryByProIds(@RequestParam(name="proId",required=true) String proId) {
		 List<PriceDate> priceDates = priceDateMapper.selectList(new LambdaQueryWrapper<PriceDate>().eq(PriceDate::getProId, proId));
		 if(priceDates==null) {
			 return Result.error("未找到对应数据");
		 }
		 List<PriceDateList> priceDateLists = new ArrayList<PriceDateList>();

		 priceDates.forEach(priceDate -> {
			 PriceDateList priceDateList = new PriceDateList();
			 DateDetail dateDetail = new DateDetail();
			 Integer pdMaxMan = priceDate.getPdMaxMan();
			 Integer pdEnrollment = priceDate.getPdEnrollment();
			 Date pdDate = priceDate.getPdDate();
			 if(pdMaxMan == pdEnrollment){
				 priceDateList.setPdFull("已满");
			 }else {
			 	priceDateList.setPdFull("可报名");
			 }
			 SimpleDateFormat week = new SimpleDateFormat("EEEE");
			 SimpleDateFormat ruler1 = new SimpleDateFormat("yyyy-MM");
			 SimpleDateFormat ruler2 = new SimpleDateFormat("yyyy");
			 SimpleDateFormat ruler3 = new SimpleDateFormat("dd");
			 String yearM = ruler1.format(pdDate);
			 String year = ruler2.format(pdDate);
			 String weekOne = week.format(pdDate);
			 String day = ruler3.format(pdDate);
			 dateDetail.setDate(yearM)
					 .setYear(year)
					 .setDay(day)
					 .setWeek(weekOne);

			 priceDateList.setProId(priceDate.getProId())
					 .setPdEnrollment(priceDate.getPdEnrollment())
					 .setPdMaxMan(priceDate.getPdMaxMan())
					 .setPdPrice(priceDate.getPdPrice())
					 .setDateDetail(dateDetail);
			 priceDateLists.add(priceDateList);
		 });
		 return Result.OK(priceDateLists);
	 }

    /**
    * 导出excel
    *
    * @param request
    * @param priceDate
    */
    @RequiresPermissions("core:price_date:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, PriceDate priceDate) {
        return super.exportXls(request, priceDate, PriceDate.class, "每天的产品价格表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("core:price_date:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, PriceDate.class);
    }

}
