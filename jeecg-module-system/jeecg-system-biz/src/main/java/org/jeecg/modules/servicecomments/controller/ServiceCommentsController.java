package org.jeecg.modules.servicecomments.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.servicecomments.entity.ServiceComments;
import org.jeecg.modules.servicecomments.service.IServiceCommentsService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.servicecomments.vo.CommentUser;
import org.jeecg.modules.user.userinfo.entity.WxClientUserinfo;
import org.jeecg.modules.user.userinfo.service.IWxClientUserinfoService;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

 /**
 * @Description: 导游服务评论表
 * @Author: jeecg-boot
 * @Date:   2023-07-13
 * @Version: V1.0
 */
@Api(tags="导游服务评论表")
@RestController
@RequestMapping("/servicecomments/serviceComments")
@Slf4j
public class ServiceCommentsController extends JeecgController<ServiceComments, IServiceCommentsService> {
	@Autowired
	private IServiceCommentsService serviceCommentsService;
	@Autowired
	private IWxClientUserinfoService wxClientUserinfoService;
	
	/**
	 * 分页列表查询
	 *
	 * @param serviceComments
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "导游服务评论表-分页列表查询")
	@ApiOperation(value="导游服务评论表-分页列表查询", notes="导游服务评论表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<ServiceComments>> queryPageList(ServiceComments serviceComments,
								   @RequestParam(name = "guideId",required = false,defaultValue="0") String guideId,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		if ("0".equals(guideId)){
			// 普通分页
			QueryWrapper<ServiceComments> queryWrapper = QueryGenerator.initQueryWrapper(serviceComments, req.getParameterMap());
			Page<ServiceComments> page = new Page<ServiceComments>(pageNo, pageSize);
			IPage<ServiceComments> pageList = serviceCommentsService.page(page, queryWrapper);
			return Result.OK(pageList);
		}else {
			// 如果有guideId，那么就展示用户端导游的服务评价
			Page<ServiceComments> page = new Page<ServiceComments>(pageNo, pageSize);
			LambdaQueryWrapper<ServiceComments> queryWrapper =
					new LambdaQueryWrapper<ServiceComments>()
							.eq(ServiceComments::getGuideId,guideId);
			IPage pageList = serviceCommentsService.page(page, queryWrapper);
			List<ServiceComments> records = pageList.getRecords();
			List<CommentUser> commentUsers = new ArrayList<>();
			records.forEach(
					record->{
						CommentUser commentUser = new CommentUser();
						// 找到对应的人名和头像
						WxClientUserinfo wxClientUserinfo = wxClientUserinfoService.getById(record.getUserId());
						commentUser.setId(record.getId())
								.setComments(record.getComments())
								.setUsername(wxClientUserinfo.getUsername())
								.setAvatar(wxClientUserinfo.getAvatar());
						commentUsers.add(commentUser);
			});
			pageList.setRecords(commentUsers);
			return Result.OK(pageList);
		}

	}
	
	/**
	 *   添加
	 *
	 * @param serviceComments
	 * @return
	 */
	@AutoLog(value = "导游服务评论表-添加")
	@ApiOperation(value="导游服务评论表-添加", notes="导游服务评论表-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody ServiceComments serviceComments) {
		serviceCommentsService.save(serviceComments);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param serviceComments
	 * @return
	 */
	@AutoLog(value = "导游服务评论表-编辑")
	@ApiOperation(value="导游服务评论表-编辑", notes="导游服务评论表-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody ServiceComments serviceComments) {
		serviceCommentsService.updateById(serviceComments);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "导游服务评论表-通过id删除")
	@ApiOperation(value="导游服务评论表-通过id删除", notes="导游服务评论表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		serviceCommentsService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "导游服务评论表-批量删除")
	@ApiOperation(value="导游服务评论表-批量删除", notes="导游服务评论表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.serviceCommentsService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "导游服务评论表-通过id查询")
	@ApiOperation(value="导游服务评论表-通过id查询", notes="导游服务评论表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<ServiceComments> queryById(@RequestParam(name="id",required=true) String id) {
		ServiceComments serviceComments = serviceCommentsService.getById(id);
		if(serviceComments==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(serviceComments);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param serviceComments
    */
    @RequiresPermissions("servicecomments:service_comments:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ServiceComments serviceComments) {
        return super.exportXls(request, serviceComments, ServiceComments.class, "导游服务评论表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("servicecomments:service_comments:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, ServiceComments.class);
    }

}
