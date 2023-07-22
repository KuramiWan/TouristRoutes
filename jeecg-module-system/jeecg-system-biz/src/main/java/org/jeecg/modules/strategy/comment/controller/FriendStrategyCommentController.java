package org.jeecg.modules.strategy.comment.controller;

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
import org.jeecg.modules.strategy.comment.entity.FriendStrategyComment;
import org.jeecg.modules.strategy.comment.service.IFriendStrategyCommentService;

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
 * @Description: 游友攻略评论表
 * @Author: jeecg-boot
 * @Date:   2023-07-22
 * @Version: V1.0
 */
@Api(tags="游友攻略评论表")
@RestController
@RequestMapping("/comment/friendStrategyComment")
@Slf4j
public class FriendStrategyCommentController extends JeecgController<FriendStrategyComment, IFriendStrategyCommentService> {
	@Autowired
	private IFriendStrategyCommentService friendStrategyCommentService;
	
	/**
	 * 分页列表查询
	 *
	 * @param friendStrategyComment
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "游友攻略评论表-分页列表查询")
	@ApiOperation(value="游友攻略评论表-分页列表查询", notes="游友攻略评论表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<FriendStrategyComment>> queryPageList(FriendStrategyComment friendStrategyComment,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<FriendStrategyComment> queryWrapper = QueryGenerator.initQueryWrapper(friendStrategyComment, req.getParameterMap());
		Page<FriendStrategyComment> page = new Page<FriendStrategyComment>(pageNo, pageSize);
		IPage<FriendStrategyComment> pageList = friendStrategyCommentService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param friendStrategyComment
	 * @return
	 */
	@AutoLog(value = "游友攻略评论表-添加")
	@ApiOperation(value="游友攻略评论表-添加", notes="游友攻略评论表-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody FriendStrategyComment friendStrategyComment) {
		friendStrategyCommentService.save(friendStrategyComment);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param friendStrategyComment
	 * @return
	 */
	@AutoLog(value = "游友攻略评论表-编辑")
	@ApiOperation(value="游友攻略评论表-编辑", notes="游友攻略评论表-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody FriendStrategyComment friendStrategyComment) {
		friendStrategyCommentService.updateById(friendStrategyComment);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "游友攻略评论表-通过id删除")
	@ApiOperation(value="游友攻略评论表-通过id删除", notes="游友攻略评论表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		friendStrategyCommentService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "游友攻略评论表-批量删除")
	@ApiOperation(value="游友攻略评论表-批量删除", notes="游友攻略评论表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.friendStrategyCommentService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "游友攻略评论表-通过id查询")
	@ApiOperation(value="游友攻略评论表-通过id查询", notes="游友攻略评论表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<FriendStrategyComment> queryById(@RequestParam(name="id",required=true) String id) {
		FriendStrategyComment friendStrategyComment = friendStrategyCommentService.getById(id);
		if(friendStrategyComment==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(friendStrategyComment);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param friendStrategyComment
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, FriendStrategyComment friendStrategyComment) {
        return super.exportXls(request, friendStrategyComment, FriendStrategyComment.class, "游友攻略评论表");
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
        return super.importExcel(request, response, FriendStrategyComment.class);
    }

}
