package org.jeecg.modules.product.controller;

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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.product.entity.Tag;
import org.jeecg.modules.product.service.ITagService;
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
 * @Description: 产品标签表
 * @Author: jeecg-boot
 * @Date:   2023-07-14
 * @Version: V1.0
 */
@Api(tags="产品标签表")
@RestController
@RequestMapping("/core/tag")
@Slf4j
public class TagController extends JeecgController<Tag, ITagService> {
	@Autowired
	private ITagService tagService;
	
	/**
	 * 分页列表查询
	 *
	 * @param tag
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "产品标签表-分页列表查询")
	@ApiOperation(value="产品标签表-分页列表查询", notes="产品标签表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<Tag>> queryPageList(Tag tag,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<Tag> queryWrapper = QueryGenerator.initQueryWrapper(tag, req.getParameterMap());
		Page<Tag> page = new Page<Tag>(pageNo, pageSize);
		IPage<Tag> pageList = tagService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param tag
	 * @return
	 */
	@AutoLog(value = "产品标签表-添加")
	@ApiOperation(value="产品标签表-添加", notes="产品标签表-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody Tag tag) {
		tagService.save(tag);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param tag
	 * @return
	 */
	@AutoLog(value = "产品标签表-编辑")
	@ApiOperation(value="产品标签表-编辑", notes="产品标签表-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody Tag tag) {
		tagService.updateById(tag);
		return Result.OK("编辑成功!");
	}

	 /**
	  *  保存列表通过productId
	  *
	  * @param productId
	  * @return
	  */
	 @AutoLog(value = "产品标签表-保存列表通过productId")
	 @ApiOperation(value="产品标签表-保存列表通过productId", notes="产品标签表-保存列表通过productId")
	 @RequestMapping(value = "/update", method = {RequestMethod.POST,RequestMethod.POST})
	 public Result<String> update(@RequestBody Map<String, Object> map) {
		 String proId = (String) map.get("proId");
		 List<String> valueList = (List<String>) map.get("value");
		 tagService.remove(new LambdaQueryWrapper<Tag>().eq(Tag::getProId,proId));
		 if(valueList.size() > 0){
			 valueList.forEach(i -> {
				 Tag tag = new Tag();
				 tag.setProId(proId).setTagContent(i);
				 tagService.save(tag);
					 });
			 return Result.OK("修改成功！");
		 }
		 return Result.error("编辑失败!");
	 }

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "产品标签表-通过id删除")
	@ApiOperation(value="产品标签表-通过id删除", notes="产品标签表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		tagService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "产品标签表-批量删除")
	@ApiOperation(value="产品标签表-批量删除", notes="产品标签表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.tagService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "产品标签表-通过id查询")
	@ApiOperation(value="产品标签表-通过id查询", notes="产品标签表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<Tag> queryById(@RequestParam(name="id",required=true) String id) {
		Tag tag = tagService.getById(id);
		if(tag==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(tag);
	}

	 /**
	  * 通过产品id查询
	  *
	  * @param productId
	  * @return
	  */
	 //@AutoLog(value = "产品标签表-通过id查询")
	 @ApiOperation(value="产品标签表-通过产品id查询", notes="产品标签表-通过产品id查询")
	 @GetMapping(value = "/queryByProId")
	 public Result<List<Tag>> queryByProId(@RequestParam(name="productId",required=true) String productId) {
		 LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
		 return Result.OK(tagService.list(queryWrapper.eq(Tag::getProId, productId)));
	 }

    /**
    * 导出excel
    *
    * @param request
    * @param tag
    */
    @RequiresPermissions("core:tag:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Tag tag) {
        return super.exportXls(request, tag, Tag.class, "产品标签表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("core:tag:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, Tag.class);
    }

}
