package org.jeecg.modules.contact.controller;

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
import org.jeecg.modules.contact.entity.Contact;
import org.jeecg.modules.contact.service.IContactService;

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
 * @Description: 联系我们表
 * @Author: jeecg-boot
 * @Date:   2023-08-12
 * @Version: V1.0
 */
@Api(tags="联系我们表")
@RestController
@RequestMapping("/contact/contact")
@Slf4j
public class ContactController extends JeecgController<Contact, IContactService> {
	@Autowired
	private IContactService contactService;
	
	/**
	 * 分页列表查询
	 *
	 * @param contact
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "联系我们表-分页列表查询")
	@ApiOperation(value="联系我们表-分页列表查询", notes="联系我们表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<Contact>> queryPageList(Contact contact,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<Contact> queryWrapper = QueryGenerator.initQueryWrapper(contact, req.getParameterMap());
		Page<Contact> page = new Page<Contact>(pageNo, pageSize);
		IPage<Contact> pageList = contactService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param contact
	 * @return
	 */
	@AutoLog(value = "联系我们表-添加")
	@ApiOperation(value="联系我们表-添加", notes="联系我们表-添加")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody Contact contact) {
		contactService.save(contact);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param contact
	 * @return
	 */
	@AutoLog(value = "联系我们表-编辑")
	@ApiOperation(value="联系我们表-编辑", notes="联系我们表-编辑")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody Contact contact) {
		contactService.updateById(contact);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "联系我们表-通过id删除")
	@ApiOperation(value="联系我们表-通过id删除", notes="联系我们表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		contactService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "联系我们表-批量删除")
	@ApiOperation(value="联系我们表-批量删除", notes="联系我们表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.contactService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "联系我们表-通过id查询")
	@ApiOperation(value="联系我们表-通过id查询", notes="联系我们表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<Contact> queryById() {
		Contact contact = contactService.getById("1690232414497939457");
		if(contact==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(contact);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param contact
    */
	@RequestMapping(value = "/exportExcel")
    public ModelAndView exportXls(HttpServletRequest request, Contact contact) {
        return super.exportXls(request, contact, Contact.class, "联系我们表");
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
        return super.importExcel(request, response, Contact.class);
    }

}
