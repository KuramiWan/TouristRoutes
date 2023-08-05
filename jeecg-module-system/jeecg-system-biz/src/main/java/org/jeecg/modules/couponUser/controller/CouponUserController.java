package org.jeecg.modules.couponUser.controller;

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
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.coupon.entity.Coupon;
import org.jeecg.modules.coupon.service.ICouponService;
import org.jeecg.modules.couponUser.entity.CouponUser;
import org.jeecg.modules.couponUser.service.ICouponUserService;

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
 * @Description: 用户拥有优惠券表
 * @Author: jeecg-boot
 * @Date:   2023-08-05
 * @Version: V1.0
 */
@Api(tags="用户拥有优惠券表")
@RestController
@RequestMapping("/couponUser/couponUser")
@Slf4j
public class CouponUserController extends JeecgController<CouponUser, ICouponUserService> {
	@Autowired
	private ICouponUserService couponUserService;

	@Autowired
	private ICouponService couponService;
	
	/**
	 * 分页列表查询
	 *
	 * @param couponUser
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "用户拥有优惠券表-分页列表查询")
	@ApiOperation(value="用户拥有优惠券表-分页列表查询", notes="用户拥有优惠券表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<CouponUser>> queryPageList(CouponUser couponUser,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<CouponUser> queryWrapper = QueryGenerator.initQueryWrapper(couponUser, req.getParameterMap());
		Page<CouponUser> page = new Page<CouponUser>(pageNo, pageSize);
		IPage<CouponUser> pageList = couponUserService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param couponUser
	 * @return
	 */
	@AutoLog(value = "用户拥有优惠券表-添加")
	@ApiOperation(value="用户拥有优惠券表-添加", notes="用户拥有优惠券表-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody CouponUser couponUser) {
		couponUserService.save(couponUser);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param couponUser
	 * @return
	 */
	@AutoLog(value = "用户拥有优惠券表-编辑")
	@ApiOperation(value="用户拥有优惠券表-编辑", notes="用户拥有优惠券表-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody CouponUser couponUser) {
		couponUserService.updateById(couponUser);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "用户拥有优惠券表-通过id删除")
	@ApiOperation(value="用户拥有优惠券表-通过id删除", notes="用户拥有优惠券表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		couponUserService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "用户拥有优惠券表-批量删除")
	@ApiOperation(value="用户拥有优惠券表-批量删除", notes="用户拥有优惠券表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.couponUserService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "用户拥有优惠券表-通过id查询")
	@ApiOperation(value="用户拥有优惠券表-通过id查询", notes="用户拥有优惠券表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<CouponUser> queryById(@RequestParam(name="id",required=true) String id) {
		CouponUser couponUser = couponUserService.getById(id);
		if(couponUser==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(couponUser);
	}


	 /**
	  * 通过UserId查询
	  *
	  * @param userId
	  * @return
	  */
	 //@AutoLog(value = "用户拥有优惠券表-通过UserId查询")
	 @ApiOperation(value="用户拥有优惠券表-通过UserId查询", notes="用户拥有优惠券表-通过UserId查询")
	 @GetMapping(value = "/queryListByUserId")
	 public Result<List<Coupon>> queryListByUserId(@RequestParam(name="userId",required=true) String userId) {
		 List<CouponUser> couponUserList = couponUserService.list(new LambdaQueryWrapper<CouponUser>().eq(CouponUser::getUserId, userId));
		 if(couponUserList.size() == 0) {
			 return Result.error("未找到对应数据");
		 }
		 List<Coupon> coupons = new ArrayList<>();
		 for (CouponUser couponUser : couponUserList) {
			 Coupon coupon = new Coupon();
			 coupon = couponService.getById(couponUser.getCouponId());
			 coupons.add(coupon);
		 }
		 return Result.OK(coupons);
	 }

    /**
    * 导出excel
    *
    * @param request
    * @param couponUser
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CouponUser couponUser) {
        return super.exportXls(request, couponUser, CouponUser.class, "用户拥有优惠券表");
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
        return super.importExcel(request, response, CouponUser.class);
    }

}
