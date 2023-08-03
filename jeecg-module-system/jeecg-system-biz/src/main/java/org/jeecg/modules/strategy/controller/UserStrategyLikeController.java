package org.jeecg.modules.strategy.controller;

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

import org.jeecg.modules.strategy.entity.UserStrategyLike;
import org.jeecg.modules.strategy.service.IUserStrategyLikeService;
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
 * @Description: 旅友攻略点赞表
 * @Author: jeecg-boot
 * @Date:   2023-08-02
 * @Version: V1.0
 */
@Api(tags="旅友攻略点赞表")
@RestController
@RequestMapping("/strategy/userStrategyLike")
@Slf4j
public class UserStrategyLikeController extends JeecgController<UserStrategyLike, IUserStrategyLikeService> {
	@Autowired
	private IUserStrategyLikeService userStrategyLikeService;
	
	/**
	 * 分页列表查询
	 *
	 * @param userStrategyLike
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "旅友攻略点赞表-分页列表查询")
	@ApiOperation(value="旅友攻略点赞表-分页列表查询", notes="旅友攻略点赞表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<UserStrategyLike>> queryPageList(UserStrategyLike userStrategyLike,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<UserStrategyLike> queryWrapper = QueryGenerator.initQueryWrapper(userStrategyLike, req.getParameterMap());
		Page<UserStrategyLike> page = new Page<UserStrategyLike>(pageNo, pageSize);
		IPage<UserStrategyLike> pageList = userStrategyLikeService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param userStrategyLike
	 * @return
	 */
	@AutoLog(value = "旅友攻略点赞表-添加")
	@ApiOperation(value="旅友攻略点赞表-添加", notes="旅友攻略点赞表-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody UserStrategyLike userStrategyLike) {
		userStrategyLikeService.save(userStrategyLike);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param userStrategyLike
	 * @return
	 */
	@AutoLog(value = "旅友攻略点赞表-编辑")
	@ApiOperation(value="旅友攻略点赞表-编辑", notes="旅友攻略点赞表-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody UserStrategyLike userStrategyLike) {
		userStrategyLikeService.updateById(userStrategyLike);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "旅友攻略点赞表-通过id删除")
	@ApiOperation(value="旅友攻略点赞表-通过id删除", notes="旅友攻略点赞表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		userStrategyLikeService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "旅友攻略点赞表-批量删除")
	@ApiOperation(value="旅友攻略点赞表-批量删除", notes="旅友攻略点赞表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.userStrategyLikeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "旅友攻略点赞表-通过id查询")
	@ApiOperation(value="旅友攻略点赞表-通过id查询", notes="旅友攻略点赞表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<UserStrategyLike> queryById(@RequestParam(name="id",required=true) String id) {
		UserStrategyLike userStrategyLike = userStrategyLikeService.getById(id);
		if(userStrategyLike==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(userStrategyLike);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param userStrategyLike
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, UserStrategyLike userStrategyLike) {
        return super.exportXls(request, userStrategyLike, UserStrategyLike.class, "旅友攻略点赞表");
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
        return super.importExcel(request, response, UserStrategyLike.class);
    }

}
