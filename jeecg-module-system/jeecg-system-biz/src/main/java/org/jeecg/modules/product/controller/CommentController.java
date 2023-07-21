package org.jeecg.modules.product.controller;

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
import io.swagger.models.auth.In;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.product.entity.Comment;
import org.jeecg.modules.product.mapper.CommentMapper;
import org.jeecg.modules.product.service.ICommentService;
import org.jeecg.modules.product.vo.CommentDetail;
import org.jeecg.modules.user.userinfo.entity.WxClientUserinfo;
import org.jeecg.modules.user.userinfo.mapper.WxClientUserinfoMapper;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.BeanUtils;
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
 * @Description: 产品评论
 * @Author: jeecg-boot
 * @Date:   2023-07-14
 * @Version: V1.0
 */
@Api(tags="产品评论")
@RestController
@RequestMapping("/core/comment")
@Slf4j
public class CommentController extends JeecgController<Comment, ICommentService> {
	@Autowired
	private ICommentService commentService;

	@Autowired
	private WxClientUserinfoMapper wxClientUserinfoMapper;
	
	/**
	 * 分页列表查询
	 *
	 * @param comment
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "产品评论-分页列表查询")
	@ApiOperation(value="产品评论-分页列表查询", notes="产品评论-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<Comment>> queryPageList(@RequestParam(name="proId") String proId,
													  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
													  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
													  HttpServletRequest req) {
//		QueryWrapper<Comment> queryWrapper = QueryGenerator.initQueryWrapper(comment, req.getParameterMap());
		Page<Comment> page = new Page<Comment>(pageNo, pageSize);
		IPage pageList = commentService.page(page, new LambdaQueryWrapper<Comment>().eq(Comment::getProId,proId));
		List<Comment> records = pageList.getRecords();
		if (proId==null || proId =="" || proId == "0"){
			return Result.OK(pageList);
		}
		else {
			ArrayList<CommentDetail> commentDetails = new ArrayList<>();;
			records.forEach(record->{
				CommentDetail commentDetail = new CommentDetail();
				WxClientUserinfo user = wxClientUserinfoMapper.selectById(record.getUserId());
				String fisName = user.getUsername().substring(0, 1);
				commentDetail.setAvatar(user.getAvatar())
						.setComImg(record.getComImg())
						.setComments(record.getComContent())
						.setDate(record.getComDate())
						.setId(record.getId())
						.setUsername(fisName);
				commentDetails.add(commentDetail);
			});
			pageList.setRecords(commentDetails);
			return Result.OK(pageList);
		}



	}
	
	/**
	 *   添加
	 *
	 * @param comment
	 * @return
	 */
	@AutoLog(value = "产品评论-添加")
	@ApiOperation(value="产品评论-添加", notes="产品评论-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody Comment comment) {
		commentService.save(comment);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param comment
	 * @return
	 */
	@AutoLog(value = "产品评论-编辑")
	@ApiOperation(value="产品评论-编辑", notes="产品评论-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody Comment comment) {
		commentService.updateById(comment);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "产品评论-通过id删除")
	@ApiOperation(value="产品评论-通过id删除", notes="产品评论-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		commentService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "产品评论-批量删除")
	@ApiOperation(value="产品评论-批量删除", notes="产品评论-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.commentService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "产品评论-通过id查询")
	@ApiOperation(value="产品评论-通过id查询", notes="产品评论-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<Comment> queryById(@RequestParam(name="id",required=true) String id) {
		Comment comment = commentService.getById(id);
		if(comment==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(comment);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param comment
    */
    @RequiresPermissions("core:comment:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Comment comment) {
        return super.exportXls(request, comment, Comment.class, "产品评论");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("core:comment:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, Comment.class);
    }

}
