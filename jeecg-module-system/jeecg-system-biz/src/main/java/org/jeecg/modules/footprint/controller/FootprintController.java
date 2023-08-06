package org.jeecg.modules.footprint.controller;

import java.text.SimpleDateFormat;
import java.util.*;
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
import org.jeecg.modules.footprint.entity.Footprint;
import org.jeecg.modules.footprint.service.IFootprintService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.footprint.vo.FootPrintVo;
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
 * @Description: 足迹表
 * @Author: jeecg-boot
 * @Date:   2023-08-06
 * @Version: V1.0
 */
@Api(tags="足迹表")
@RestController
@RequestMapping("/footprint/footprint")
@Slf4j
public class FootprintController extends JeecgController<Footprint, IFootprintService> {
	@Autowired
	private IFootprintService footprintService;

	 @Autowired
	 private IWxClientUserinfoService wxClientUserinfoService;

	
	/**
	 * 分页列表查询
	 *
	 * @param footprint
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "足迹表-分页列表查询")
	@ApiOperation(value="足迹表-分页列表查询", notes="足迹表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<Footprint>> queryPageList(Footprint footprint,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<Footprint> queryWrapper = QueryGenerator.initQueryWrapper(footprint, req.getParameterMap());
		Page<Footprint> page = new Page<Footprint>(pageNo, pageSize);
		IPage<Footprint> pageList = footprintService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param footprint
	 * @return
	 */
	@AutoLog(value = "足迹表-添加")
	@ApiOperation(value="足迹表-添加", notes="足迹表-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody Footprint footprint) {
		footprintService.save(footprint);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param footprint
	 * @return
	 */
	@AutoLog(value = "足迹表-编辑")
	@ApiOperation(value="足迹表-编辑", notes="足迹表-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody Footprint footprint) {
		footprintService.updateById(footprint);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "足迹表-通过id删除")
	@ApiOperation(value="足迹表-通过id删除", notes="足迹表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		footprintService.removeById(id);
		return Result.OK("删除成功!");
	}

	 /**
	  *   通过userId查询所有足迹
	  *
	  * @param userId
	  * @return
	  */
	 @AutoLog(value = "足迹表-通过userId查询所有足迹")
	 @ApiOperation(value="足迹表-通过userId查询所有足迹", notes="足迹表-通过userId查询所有足迹")
	 @GetMapping(value = "/queryListByUserId")
	 public Result<List<FootPrintVo>> queryListByUserId(@RequestParam(name="userId",required=true) String userId) {
		 List<Footprint> list = footprintService.list(new LambdaQueryWrapper<Footprint>().eq(Footprint::getUserId, userId));
		 List<FootPrintVo> FootPrintList = new ArrayList<>();
		 if(list.size() == 0) {
			 return Result.error("未找到对应数据");
		 }
		 for (Footprint footprint : list) {
			 FootPrintVo footPrintVo = new FootPrintVo();
			 footPrintVo.setFootprint(footprint);
			 WxClientUserinfo user = wxClientUserinfoService.getById(footprint.getUserId());
			 footPrintVo.setAvatar(user.getAvatar());
			 footPrintVo.setName(user.getUsername());
			 Date dateWirte = footprint.getDateWirte();
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd&HH:mm");
			 String dateStr = sdf.format(dateWirte);
			 String[] items = dateStr.split("&");
			 String[] split = items[0].split("-");
			 String monthDay = split[1] + "."+ split[2];
			 String hourSec = items[1];
			 footPrintVo.setHourSec(hourSec);
			 footPrintVo.setMonthDay(monthDay);
			 FootPrintList.add(footPrintVo);
		 }


		 return Result.OK(FootPrintList);
	 }
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "足迹表-批量删除")
	@ApiOperation(value="足迹表-批量删除", notes="足迹表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.footprintService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "足迹表-通过id查询")
	@ApiOperation(value="足迹表-通过id查询", notes="足迹表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<Footprint> queryById(@RequestParam(name="id",required=true) String id) {
		Footprint footprint = footprintService.getById(id);
		if(footprint==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(footprint);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param footprint
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Footprint footprint) {
        return super.exportXls(request, footprint, Footprint.class, "足迹表");
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
        return super.importExcel(request, response, Footprint.class);
    }

}
