package org.jeecg.modules.guide.controller;

import java.util.*;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.common.util.oss.OssBootUtil;
import org.jeecg.modules.guide.dto.UserAndLeader;
import org.jeecg.modules.guide.entity.TouristGuide;
import org.jeecg.modules.guide.entity.UserLeaderLike;
import org.jeecg.modules.guide.mapper.TouristGuideMapper;
import org.jeecg.modules.guide.service.ITouristGuideService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.guide.service.IUserLeaderLikeService;
import org.jeecg.modules.guide.vo.WaterFallGuide;
import org.jeecg.modules.product.entity.Comment;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;
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
 * @Description: 导游表
 * @Author: jeecg-boot
 * @Date: 2023-07-13
 * @Version: V1.0
 */
@Api(tags = "导游表")
@RestController
@RequestMapping("/guide/touristGuide")
@Slf4j
public class TouristGuideController extends JeecgController<TouristGuide, ITouristGuideService> {
    @Autowired
    private ITouristGuideService touristGuideService;
    @Autowired
    private IUserLeaderLikeService userLeaderLikeService;

    @ApiOperation(value = "导游表-增加点赞数量", notes = "导游表-增加点赞数量")
    @PostMapping(value = "/addLikeNum")
    @Transactional(rollbackFor = Exception.class)
    public Result<Integer> addLikeNum(@RequestBody UserAndLeader userAndLeader) throws Exception {
        TouristGuide touristGuide = touristGuideService.getById(userAndLeader.getGuideId());
        Integer likeNum = touristGuide.getLikeNum();
        touristGuide.setLikeNum(likeNum + 1);
        // 加入到用户点赞导游表中
        UserLeaderLike userLeaderLike =
                new UserLeaderLike()
                        .setLeaderId(userAndLeader.getGuideId())
                        .setUserId(userAndLeader.getUserId());
        LambdaQueryWrapper<UserLeaderLike> queryWrapper =
                new LambdaQueryWrapper<UserLeaderLike>()
                        .eq(UserLeaderLike::getUserId, userAndLeader.getUserId())
                        .eq(UserLeaderLike::getLeaderId, userAndLeader.getGuideId());
        long count = userLeaderLikeService.count(queryWrapper);
        touristGuideService.updateById(touristGuide);
        userLeaderLikeService.save(userLeaderLike);
        if (count >= 1) throw new DuplicateKeyException("点赞失败");
        return Result.OK(touristGuide.getLikeNum());
    }

    @ApiOperation(value = "导游表-减少点赞数量", notes = "导游表-减少点赞数量")
    @PostMapping(value = "/subLikeNum")
    @Transactional(rollbackFor = Exception.class)
    public Result<Integer> subLikeNum(@RequestBody UserAndLeader userAndLeader) throws Exception {
        TouristGuide touristGuide = touristGuideService.getById(userAndLeader.getGuideId());
        Integer likeNum = touristGuide.getLikeNum();
        touristGuide.setLikeNum(likeNum - 1);
        //从点赞表中删除这条记录
        LambdaQueryWrapper<UserLeaderLike> queryWrapper =
                new LambdaQueryWrapper<UserLeaderLike>()
                        .eq(UserLeaderLike::getUserId, userAndLeader.getUserId())
                        .eq(UserLeaderLike::getLeaderId, userAndLeader.getGuideId());
        boolean update = touristGuideService.updateById(touristGuide);
        boolean remove = userLeaderLikeService.remove(queryWrapper);
        if (!(update && remove)) throw new DataRetrievalFailureException("取消失败");
        return Result.OK(touristGuide.getLikeNum());
    }

    /**
     * 分页列表查询
     *
     * @param touristGuide
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "导游表-分页列表查询")
    @ApiOperation(value = "导游表-分页列表查询", notes = "导游表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<TouristGuide>> getPageList(TouristGuide touristGuide,
                                                   @RequestParam(name = "userId", required = false) String userId,
                                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                   HttpServletRequest req) {
        Page<TouristGuide> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<TouristGuide> queryWrapper =
                new LambdaQueryWrapper<TouristGuide>().orderByDesc(TouristGuide::getLikeNum);
        IPage pageList = touristGuideService.page(page, queryWrapper);
        List<TouristGuide> records = pageList.getRecords();
        ArrayList<WaterFallGuide> waterFallGuides = new ArrayList<>();
        records.forEach(
                record -> {
                    WaterFallGuide waterFallGuide = new WaterFallGuide();
                    BeanUtils.copyProperties(record, waterFallGuide);
                    LambdaQueryWrapper<UserLeaderLike> starQueryWrapper =
                            new LambdaQueryWrapper<UserLeaderLike>()
                                    .eq(UserLeaderLike::getUserId, userId)
                                    .eq(UserLeaderLike::getLeaderId, record.getId());
                    long count = userLeaderLikeService.count(starQueryWrapper);
                    waterFallGuide.setValue(count);
                    waterFallGuides.add(waterFallGuide);
                });
        pageList.setRecords(waterFallGuides);
        return Result.OK(pageList);
    }


    /**
     * 分页列表查询
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    //@AutoLog(value = "导游表-分页列表查询")
    @ApiOperation(value = "导游表-分页列表查询", notes = "导游表-分页列表查询")
    @GetMapping(value = "/listAll")
    public Result<IPage<TouristGuide>> getPageListAll(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Page<TouristGuide> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<TouristGuide> queryWrapper =
                new LambdaQueryWrapper<TouristGuide>().orderByDesc(TouristGuide::getCreateTime);
        IPage<TouristGuide> pageList = touristGuideService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 分页列表根据姓名模糊查询
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    //@AutoLog(value = "导游表-分页列表根据姓名模糊查询")
    @ApiOperation(value = "导游表-分页列表根据姓名模糊查询", notes = "导游表-分页列表根据姓名模糊查询")
    @GetMapping(value = "/listByName")
    public Result<IPage<TouristGuide>> listByName(@RequestParam(name = "name", defaultValue = "1") String name, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Page<TouristGuide> page = new Page<>(pageNo, pageSize);
        LambdaQueryWrapper<TouristGuide> queryWrapper =
                new LambdaQueryWrapper<TouristGuide>().like(TouristGuide::getName, name).orderByDesc(TouristGuide::getCreateTime);
        IPage<TouristGuide> pageList = touristGuideService.page(page, queryWrapper);
        return Result.OK(pageList);
    }


    /**
     * 添加
     *
     * @param touristGuide
     * @return
     */
    @AutoLog(value = "导游表-添加")
    @ApiOperation(value = "导游表-添加", notes = "导游表-添加")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody TouristGuide touristGuide) {
        touristGuideService.save(touristGuide);
        return Result.OK("添加成功！");
    }

    /**
     * 添加or编辑
     *
     * @param touristGuide
     * @return
     */
    @AutoLog(value = "导游表-添加or编辑")
    @ApiOperation(value = "导游表-添加or编辑", notes = "导游表-添加or编辑")
    @PostMapping(value = "/addOrEdit")
    public Result<String> addOrEdit(@RequestBody TouristGuide touristGuide) {
        touristGuide.setPageImg(touristGuide.getAvatar());
        //根据传过来的touristGuide中是否有id来区分
        if (StringUtils.isNotEmpty(touristGuide.getId())) {
            touristGuideService.updateById(touristGuide);
            return Result.OK("编辑成功！");
        } else {
            touristGuideService.save(touristGuide);
        }
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param touristGuide
     * @return
     */
    @AutoLog(value = "导游表-编辑")
    @ApiOperation(value = "导游表-编辑", notes = "导游表-编辑")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody TouristGuide touristGuide) {
        touristGuideService.updateById(touristGuide);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "导游表-通过id删除")
    @ApiOperation(value = "导游表-通过id删除", notes = "导游表-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        touristGuideService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "导游表-批量删除")
    @ApiOperation(value = "导游表-批量删除", notes = "导游表-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.touristGuideService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "导游表-通过id查询")
    @ApiOperation(value = "导游表-通过id查询", notes = "导游表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<TouristGuide> queryById(@RequestParam(name = "id", required = true) String id) {
        TouristGuide touristGuide = touristGuideService.getById(id);
        if (touristGuide == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(touristGuide);
    }

    @AutoLog(value = "某个产品某一天的所有任务-上传图床返回url")
    @ApiOperation(value = "某个产品某一天的所有任务-上传图床返回url", notes = "某个产品某一天的所有任务-上传图床返回url")
    @PostMapping(value = "/uploadGuideImg")
    public Result<List<String>> uploadGuideImg(@RequestBody Map<String, String> map) {
        ArrayList<String> list = new ArrayList<>();
        String base64Img = map.get("base64Img");
        //上传图床并更新数据库中的图片字段
        list.add(uploadImg(base64Img));
        return Result.OK(list);
    }

    public String uploadImg(String base64Img) {
        try {
            // 将Base64数据转换为字节数组
            byte[] pageImg = Base64.getDecoder().decode(base64Img);
            String fileDir1 = "suixinyou-wx-client/pages-tourGuide/"; // 文件保存目录，根据实际情况调整
            String fileUrl1 = OssBootUtil.upload(new Date().toString(), pageImg, fileDir1);

            if (fileUrl1 == null) return "上传失败";

            return fileUrl1;

        } catch (Exception e) {
            e.printStackTrace();
            return "上传失败";
        }
    }


    /**
     * 导出excel
     *
     * @param request
     * @param touristGuide
     */
    @RequiresPermissions("guide:tourist_guide:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, TouristGuide touristGuide) {
        return super.exportXls(request, touristGuide, TouristGuide.class, "导游表");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequiresPermissions("guide:tourist_guide:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, TouristGuide.class);
    }

}
