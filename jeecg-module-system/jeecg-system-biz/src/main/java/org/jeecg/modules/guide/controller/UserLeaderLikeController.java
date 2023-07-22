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
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.guide.entity.UserLeaderLike;
import org.jeecg.modules.guide.service.IUserLeaderLikeService;
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
 * @Description: 用户点赞导游
 * @Author: jeecg-boot
 * @Date:   2023-07-22
 * @Version: V1.0
 */
@Api(tags="用户点赞导游")
@RestController
@RequestMapping("/core/userLeaderLike")
@Slf4j
public class UserLeaderLikeController extends JeecgController<UserLeaderLike, IUserLeaderLikeService> {
	@Autowired
	private IUserLeaderLikeService userLeaderLikeService;

	/**
	 * 分页列表查询
	 *
	 * @param userLeaderLike
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "用户点赞导游-分页列表查询")
	@ApiOperation(value="用户点赞导游-分页列表查询", notes="用户点赞导游-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<UserLeaderLike>> queryPageList(UserLeaderLike userLeaderLike,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<UserLeaderLike> queryWrapper = QueryGenerator.initQueryWrapper(userLeaderLike, req.getParameterMap());
		Page<UserLeaderLike> page = new Page<UserLeaderLike>(pageNo, pageSize);
		IPage<UserLeaderLike> pageList = userLeaderLikeService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param userLeaderLike
	 * @return
	 */
	@AutoLog(value = "用户点赞导游-添加")
	@ApiOperation(value="用户点赞导游-添加", notes="用户点赞导游-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody UserLeaderLike userLeaderLike) {
		userLeaderLikeService.save(userLeaderLike);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param userLeaderLike
	 * @return
	 */
	@AutoLog(value = "用户点赞导游-编辑")
	@ApiOperation(value="用户点赞导游-编辑", notes="用户点赞导游-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody UserLeaderLike userLeaderLike) {
		userLeaderLikeService.updateById(userLeaderLike);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "用户点赞导游-通过id删除")
	@ApiOperation(value="用户点赞导游-通过id删除", notes="用户点赞导游-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		userLeaderLikeService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "用户点赞导游-批量删除")
	@ApiOperation(value="用户点赞导游-批量删除", notes="用户点赞导游-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.userLeaderLikeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "用户点赞导游-通过id查询")
	@ApiOperation(value="用户点赞导游-通过id查询", notes="用户点赞导游-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<UserLeaderLike> queryById(@RequestParam(name="id",required=true) String id) {
		UserLeaderLike userLeaderLike = userLeaderLikeService.getById(id);
		if(userLeaderLike==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(userLeaderLike);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param userLeaderLike
    */
    @RequiresPermissions("core:user_leader_like:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, UserLeaderLike userLeaderLike) {
        return super.exportXls(request, userLeaderLike, UserLeaderLike.class, "用户点赞导游");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("core:user_leader_like:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, UserLeaderLike.class);
    }

}
