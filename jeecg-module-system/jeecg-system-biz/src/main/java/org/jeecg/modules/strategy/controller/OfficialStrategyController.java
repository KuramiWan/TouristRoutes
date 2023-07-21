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
import org.jeecg.modules.strategy.entity.OfficialStrategy;
import org.jeecg.modules.strategy.service.IOfficialStrategyService;

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
 * @Description: 官方攻略
 * @Author: jeecg-boot
 * @Date:   2023-07-21
 * @Version: V1.0
 */
@Api(tags="官方攻略")
@RestController
@RequestMapping("/strategy/officialStrategy")
@Slf4j
public class OfficialStrategyController extends JeecgController<OfficialStrategy, IOfficialStrategyService> {
	@Autowired
	private IOfficialStrategyService officialStrategyService;
	
	/**
	 * 分页列表查询
	 *
	 * @param officialStrategy
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "官方攻略-分页列表查询")
	@ApiOperation(value="官方攻略-分页列表查询", notes="官方攻略-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<OfficialStrategy>> queryPageList(OfficialStrategy officialStrategy,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<OfficialStrategy> queryWrapper = QueryGenerator.initQueryWrapper(officialStrategy, req.getParameterMap());
		Page<OfficialStrategy> page = new Page<OfficialStrategy>(pageNo, pageSize);
		IPage<OfficialStrategy> pageList = officialStrategyService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param officialStrategy
	 * @return
	 */
	@AutoLog(value = "官方攻略-添加")
	@ApiOperation(value="官方攻略-添加", notes="官方攻略-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody OfficialStrategy officialStrategy) {
		officialStrategyService.save(officialStrategy);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param officialStrategy
	 * @return
	 */
	@AutoLog(value = "官方攻略-编辑")
	@ApiOperation(value="官方攻略-编辑", notes="官方攻略-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody OfficialStrategy officialStrategy) {
		officialStrategyService.updateById(officialStrategy);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "官方攻略-通过id删除")
	@ApiOperation(value="官方攻略-通过id删除", notes="官方攻略-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		officialStrategyService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "官方攻略-批量删除")
	@ApiOperation(value="官方攻略-批量删除", notes="官方攻略-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.officialStrategyService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "官方攻略-通过id查询")
	@ApiOperation(value="官方攻略-通过id查询", notes="官方攻略-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<OfficialStrategy> queryById(@RequestParam(name="id",required=true) String id) {
		OfficialStrategy officialStrategy = officialStrategyService.getById(id);
		if(officialStrategy==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(officialStrategy);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param officialStrategy
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OfficialStrategy officialStrategy) {
        return super.exportXls(request, officialStrategy, OfficialStrategy.class, "官方攻略");
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
        return super.importExcel(request, response, OfficialStrategy.class);
    }

}
