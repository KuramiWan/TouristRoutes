package org.jeecg.modules.tourist.client.controller;

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

import org.jeecg.modules.tourist.client.entity.Escort;
import org.jeecg.modules.tourist.client.service.IEscortService;
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
 * @Description: 伴游人的基本信息
 * @Author: jeecg-boot
 * @Date:   2023-06-18
 * @Version: V1.0
 */
@Api(tags="伴游人的基本信息")
@RestController
@RequestMapping("/client/escort")
@Slf4j
public class EscortController extends JeecgController<Escort, IEscortService> {
	@Autowired
	private IEscortService escortService;
	
	/**
	 * 分页列表查询
	 *
	 * @param escort
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "伴游人的基本信息-分页列表查询")
	@ApiOperation(value="伴游人的基本信息-分页列表查询", notes="伴游人的基本信息-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<Escort>> queryPageList(Escort escort,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<Escort> queryWrapper = QueryGenerator.initQueryWrapper(escort, req.getParameterMap());
		Page<Escort> page = new Page<Escort>(pageNo, pageSize);
		IPage<Escort> pageList = escortService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param escort
	 * @return
	 */
	@AutoLog(value = "伴游人的基本信息-添加")
	@ApiOperation(value="伴游人的基本信息-添加", notes="伴游人的基本信息-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody Escort escort) {
		escortService.save(escort);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param escort
	 * @return
	 */
	@AutoLog(value = "伴游人的基本信息-编辑")
	@ApiOperation(value="伴游人的基本信息-编辑", notes="伴游人的基本信息-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody Escort escort) {
		escortService.updateById(escort);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "伴游人的基本信息-通过id删除")
	@ApiOperation(value="伴游人的基本信息-通过id删除", notes="伴游人的基本信息-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		escortService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "伴游人的基本信息-批量删除")
	@ApiOperation(value="伴游人的基本信息-批量删除", notes="伴游人的基本信息-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.escortService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "伴游人的基本信息-通过id查询")
	@ApiOperation(value="伴游人的基本信息-通过id查询", notes="伴游人的基本信息-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<Escort> queryById(@RequestParam(name="id",required=true) String id) {
		Escort escort = escortService.getById(id);
		if(escort==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(escort);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param escort
    */
    @RequiresPermissions("escort:escort:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Escort escort) {
        return super.exportXls(request, escort, Escort.class, "伴游人的基本信息");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("escort:escort:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, Escort.class);
    }

}
