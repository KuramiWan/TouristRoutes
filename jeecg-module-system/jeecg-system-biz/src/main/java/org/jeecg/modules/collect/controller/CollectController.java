package org.jeecg.modules.collect.controller;

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
import org.jeecg.modules.collect.entity.Collect;
import org.jeecg.modules.collect.service.ICollectService;

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
 * @Description: 收藏表
 * @Author: jeecg-boot
 * @Date:   2023-08-02
 * @Version: V1.0
 */
@Api(tags="收藏表")
@RestController
@RequestMapping("/collect/collect")
@Slf4j
public class CollectController extends JeecgController<Collect, ICollectService> {
	@Autowired
	private ICollectService collectService;
	
	/**
	 * 分页列表查询
	 *
	 * @param collect
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "收藏表-分页列表查询")
	@ApiOperation(value="收藏表-分页列表查询", notes="收藏表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<Collect>> queryPageList(Collect collect,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<Collect> queryWrapper = QueryGenerator.initQueryWrapper(collect, req.getParameterMap());
		Page<Collect> page = new Page<Collect>(pageNo, pageSize);
		IPage<Collect> pageList = collectService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param collect
	 * @return
	 */
	@AutoLog(value = "收藏表-添加")
	@ApiOperation(value="收藏表-添加", notes="收藏表-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody Collect collect) {
		collectService.save(collect);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param collect
	 * @return
	 */
	@AutoLog(value = "收藏表-编辑")
	@ApiOperation(value="收藏表-编辑", notes="收藏表-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody Collect collect) {
		collectService.updateById(collect);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "收藏表-通过id删除")
	@ApiOperation(value="收藏表-通过id删除", notes="收藏表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		collectService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "收藏表-批量删除")
	@ApiOperation(value="收藏表-批量删除", notes="收藏表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.collectService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "收藏表-通过id查询")
	@ApiOperation(value="收藏表-通过id查询", notes="收藏表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<Collect> queryById(@RequestParam(name="id",required=true) String id) {
		Collect collect = collectService.getById(id);
		if(collect==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(collect);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param collect
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Collect collect) {
        return super.exportXls(request, collect, Collect.class, "收藏表");
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
        return super.importExcel(request, response, Collect.class);
    }

}
