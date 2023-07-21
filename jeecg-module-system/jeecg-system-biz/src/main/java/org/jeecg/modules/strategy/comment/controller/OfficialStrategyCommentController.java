package org.jeecg.modules.strategy.comment.controller;

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
import org.jeecg.modules.strategy.comment.entity.OfficialStrategyComment;
import org.jeecg.modules.strategy.comment.service.IOfficialStrategyCommentService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.strategy.comment.vo.OfficialStrategyCommentVo;
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
 * @Description: 官方攻略评论表
 * @Author: jeecg-boot
 * @Date: 2023-07-21
 * @Version: V1.0
 */
@Api(tags = "官方攻略评论表")
@RestController
@RequestMapping("/comment/officialStrategyComment")
@Slf4j
public class OfficialStrategyCommentController extends JeecgController<OfficialStrategyComment, IOfficialStrategyCommentService> {
    @Autowired
    private IOfficialStrategyCommentService officialStrategyCommentService;

    /**
     * 分页列表查询
     *
     * @param officialStrategyComment
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "官方攻略评论表-分页列表查询")
    @ApiOperation(value="官方攻略评论表-分页列表查询", notes="官方攻略评论表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<OfficialStrategyComment>> queryPageList(OfficialStrategyComment officialStrategyComment,
    							   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
    							   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
    							   HttpServletRequest req) {
    	QueryWrapper<OfficialStrategyComment> queryWrapper = QueryGenerator.initQueryWrapper(officialStrategyComment, req.getParameterMap());
    	Page<OfficialStrategyComment> page = new Page<OfficialStrategyComment>(pageNo, pageSize);
    	IPage<OfficialStrategyComment> pageList = officialStrategyCommentService.page(page, queryWrapper);
    	return Result.OK(pageList);
    }

    /**
     * 分页列表查询
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    //@AutoLog(value = "官方攻略评论表-分页列表查询")
    @ApiOperation(value = "官方攻略评论表-分页列表查询", notes = "官方攻略评论表-分页列表查询")
    @GetMapping(value = "/queryByOfficialId")
    public Result<IPage<OfficialStrategyCommentVo>> queryByOfficialId(
            @RequestParam(name = "officialId") String officialId,
            @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Page<OfficialStrategyCommentVo> commentVoPage = officialStrategyCommentService.queryListPage(officialId, pageNo, pageSize);
        return Result.OK(commentVoPage);
    }

    /**
     * 添加
     *
     * @param officialStrategyComment
     * @return
     */
    @AutoLog(value = "官方攻略评论表-添加")
    @ApiOperation(value = "官方攻略评论表-添加", notes = "官方攻略评论表-添加")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody OfficialStrategyComment officialStrategyComment) {
        officialStrategyCommentService.save(officialStrategyComment);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param officialStrategyComment
     * @return
     */
    @AutoLog(value = "官方攻略评论表-编辑")
    @ApiOperation(value = "官方攻略评论表-编辑", notes = "官方攻略评论表-编辑")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody OfficialStrategyComment officialStrategyComment) {
        officialStrategyCommentService.updateById(officialStrategyComment);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "官方攻略评论表-通过id删除")
    @ApiOperation(value = "官方攻略评论表-通过id删除", notes = "官方攻略评论表-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        officialStrategyCommentService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "官方攻略评论表-批量删除")
    @ApiOperation(value = "官方攻略评论表-批量删除", notes = "官方攻略评论表-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.officialStrategyCommentService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "官方攻略评论表-通过id查询")
    @ApiOperation(value = "官方攻略评论表-通过id查询", notes = "官方攻略评论表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<OfficialStrategyComment> queryById(@RequestParam(name = "id", required = true) String id) {
        OfficialStrategyComment officialStrategyComment = officialStrategyCommentService.getById(id);
        if (officialStrategyComment == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(officialStrategyComment);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param officialStrategyComment
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OfficialStrategyComment officialStrategyComment) {
        return super.exportXls(request, officialStrategyComment, OfficialStrategyComment.class, "官方攻略评论表");
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
        return super.importExcel(request, response, OfficialStrategyComment.class);
    }

}
