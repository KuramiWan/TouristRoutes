package org.jeecg.modules.follow.controller;

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
import org.jeecg.modules.follow.entity.Follow;
import org.jeecg.modules.follow.service.IFollowService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.follow.vo.FollowList;
import org.jeecg.modules.user.userinfo.entity.WxClientUserinfo;
import org.jeecg.modules.user.userinfo.service.IWxClientUserinfoService;
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
 * @Description: 关注人表
 * @Author: jeecg-boot
 * @Date:   2023-08-02
 * @Version: V1.0
 */
@Api(tags="关注人表")
@RestController
@RequestMapping("/follow/follow")
@Slf4j
public class FollowController extends JeecgController<Follow, IFollowService> {
	@Autowired
	private IFollowService followService;

	@Autowired
	private IWxClientUserinfoService wxClientUserinfoService;
	
	/**
	 * 分页列表查询
	 *
	 * @param follow
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "关注人表-分页列表查询")
	@ApiOperation(value="关注人表-分页列表查询", notes="关注人表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<Follow>> queryPageList(Follow follow,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<Follow> queryWrapper = QueryGenerator.initQueryWrapper(follow, req.getParameterMap());
		Page<Follow> page = new Page<Follow>(pageNo, pageSize);
		IPage<Follow> pageList = followService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	 @ApiOperation(value="关注人表-关注或者取关", notes="关注人表-关注或者取关")
	 @RequestMapping(value = "/addOrSub")
	 public boolean addOrSub(@RequestParam(value = "userId",required = true) String userId, @RequestParam(value = "followId",required = true) String followId,@RequestParam(value = "flag",required = true) Integer flag){
		return flag==1?followService.save(new Follow().setFollowId(followId).setUserId(userId)):followService.remove(new LambdaQueryWrapper<Follow>().eq(Follow::getFollowId,followId).eq(Follow::getUserId,userId));
	 }
	
	/**
	 *   添加
	 *
	 * @param follow
	 * @return
	 */
	@AutoLog(value = "关注人表-添加")
	@ApiOperation(value="关注人表-添加", notes="关注人表-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody Follow follow) {
		followService.save(follow);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param follow
	 * @return
	 */
	@AutoLog(value = "关注人表-编辑")
	@ApiOperation(value="关注人表-编辑", notes="关注人表-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody Follow follow) {
		followService.updateById(follow);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "关注人表-通过id删除")
	@ApiOperation(value="关注人表-通过id删除", notes="关注人表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		followService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "关注人表-批量删除")
	@ApiOperation(value="关注人表-批量删除", notes="关注人表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.followService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "关注人表-通过id查询")
	@ApiOperation(value="关注人表-通过id查询", notes="关注人表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<Follow> queryById(@RequestParam(name="id",required=true) String id) {
		Follow follow = followService.getById(id);
		if(follow==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(follow);
	}

	 /**
	  * 通过userId查询
	  *
	  * @param userId
	  * @return
	  */
	 //@AutoLog(value = "关注人表-通过userId查询")
	 @ApiOperation(value="关注人表-通过userId查询", notes="关注人表-通过userId查询")
	 @GetMapping(value = "/queryListByUserId")
	 public Result<FollowList> queryListByUserId(@RequestParam(name="userId",required=true) String userId) {
		 List<Follow> follows = followService.list(new LambdaQueryWrapper<Follow>().eq(Follow::getUserId, userId));
		 int count = follows.size();
		 FollowList followList = new FollowList();
		 followList.setCount(count);
		 List<WxClientUserinfo> wxClientUserinfos = new ArrayList<WxClientUserinfo>();
		 for (Follow follow : follows) {
			 WxClientUserinfo user = wxClientUserinfoService.getById(follow.getFollowId());
			 wxClientUserinfos.add(user);
		 }
		 followList.setFollowList(wxClientUserinfos);
		 return Result.OK(followList);
	 }

    /**
    * 导出excel
    *
    * @param request
    * @param follow
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Follow follow) {
        return super.exportXls(request, follow, Follow.class, "关注人表");
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
        return super.importExcel(request, response, Follow.class);
    }

}
