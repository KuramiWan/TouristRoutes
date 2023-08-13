package org.jeecg.modules.indexPopularcity.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.indexPopularcity.entity.IndexPopularcity;
import org.jeecg.modules.indexPopularcity.service.IIndexPopularcityService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

 /**
 * @Description: 首页热门城市攻略配置
 * @Author: jeecg-boot
 * @Date:   2023-08-13
 * @Version: V1.0
 */
@Api(tags="首页热门城市攻略配置")
@RestController
@RequestMapping("/indexPopularcity/indexPopularcity")
@Slf4j
public class IndexPopularcityController extends JeecgController<IndexPopularcity, IIndexPopularcityService> {
	@Autowired
	private IIndexPopularcityService indexPopularcityService;
	
	/**
	 * 分页列表查询
	 *
	 * @param indexPopularcity
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "首页热门城市攻略配置-分页列表查询")
	@ApiOperation(value="首页热门城市攻略配置-分页列表查询", notes="首页热门城市攻略配置-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<IndexPopularcity>> queryPageList(IndexPopularcity indexPopularcity,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<IndexPopularcity> queryWrapper = QueryGenerator.initQueryWrapper(indexPopularcity, req.getParameterMap());
		Page<IndexPopularcity> page = new Page<IndexPopularcity>(pageNo, pageSize);
		IPage<IndexPopularcity> pageList = indexPopularcityService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param indexPopularcity
	 * @return
	 */
	@AutoLog(value = "首页热门城市攻略配置-添加")
	@ApiOperation(value="首页热门城市攻略配置-添加", notes="首页热门城市攻略配置-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody IndexPopularcity indexPopularcity) {
		indexPopularcityService.save(indexPopularcity);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param indexPopularcity
	 * @return
	 */
	@AutoLog(value = "首页热门城市攻略配置-编辑")
	@ApiOperation(value="首页热门城市攻略配置-编辑", notes="首页热门城市攻略配置-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody IndexPopularcity indexPopularcity) {
		indexPopularcityService.updateById(indexPopularcity);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "首页热门城市攻略配置-通过id删除")
	@ApiOperation(value="首页热门城市攻略配置-通过id删除", notes="首页热门城市攻略配置-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		indexPopularcityService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "首页热门城市攻略配置-批量删除")
	@ApiOperation(value="首页热门城市攻略配置-批量删除", notes="首页热门城市攻略配置-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.indexPopularcityService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "首页热门城市攻略配置-通过id查询")
	@ApiOperation(value="首页热门城市攻略配置-通过id查询", notes="首页热门城市攻略配置-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<IndexPopularcity> queryById(@RequestParam(name="id",required=true) String id) {
		IndexPopularcity indexPopularcity = indexPopularcityService.getById(id);
		if(indexPopularcity==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(indexPopularcity);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param indexPopularcity
    */
    @RequiresPermissions("indexPopularcity:index_popularcity:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, IndexPopularcity indexPopularcity) {
        return super.exportXls(request, indexPopularcity, IndexPopularcity.class, "首页热门城市攻略配置");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("indexPopularcity:index_popularcity:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, IndexPopularcity.class);
    }

}
