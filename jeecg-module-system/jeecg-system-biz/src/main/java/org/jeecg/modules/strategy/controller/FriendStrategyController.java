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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.guide.service.ITouristGuideService;
import org.jeecg.modules.strategy.entity.FriendId;
import org.jeecg.modules.strategy.entity.FriendStrategy;
<<<<<<< HEAD
import org.jeecg.modules.strategy.entity.OfficialGuide;
import org.jeecg.modules.strategy.service.IFriendIdService;
=======
import org.jeecg.modules.strategy.entity.OfficialStrategy;
>>>>>>> 9de864e0ba8b22771c6f3e18648f72bf044c5ba8
import org.jeecg.modules.strategy.service.IFriendStrategyService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.strategy.vo.FriendStrategyVo;
import org.jeecg.modules.strategy.vo.Guide;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.BeanUtils;
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
 * @Description: 游友攻略
 * @Author: jeecg-boot
 * @Date: 2023-07-22
 * @Version: V1.0
 */
@Api(tags = "游友攻略")
@RestController
@RequestMapping("/strategy/friendStrategy")
@Slf4j
public class FriendStrategyController extends JeecgController<FriendStrategy, IFriendStrategyService> {
    @Autowired
    private IFriendStrategyService friendStrategyService;
    @Autowired
    private IFriendIdService friendIdService;
    @Autowired
    private ITouristGuideService touristGuideService;

    /**
     * 分页列表查询
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    //@AutoLog(value = "游友攻略-分页列表查询")
    @ApiOperation(value = "游友攻略-分页列表查询", notes = "游友攻略-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<FriendStrategyVo>> queryPageList(
            @RequestParam(name = "id", defaultValue = "") String id,
            @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        Page<FriendStrategyVo> strategyVoPage = friendStrategyService.queryFriendStrategyInfo(id, pageNo, pageSize);
        return Result.OK(strategyVoPage);
    }


    /**
     * 通过title查询攻略id
     *@param friendStrategy
     * @param req
     * @return
     */
    //@AutoLog(value = "通过title查询攻略id")
    @ApiOperation(value = "通过title查询攻略id", notes = "通过title查询攻略id")
    @GetMapping(value = "/newList")
    public Result<IPage<FriendStrategy>> queryPageList(FriendStrategy friendStrategy,HttpServletRequest req)
    {
        QueryWrapper<FriendStrategy> queryWrapper = QueryGenerator.initQueryWrapper(friendStrategy, req.getParameterMap());
        if(req.getParameterValues("searchKey") != null){
            queryWrapper.like("title",req.getParameterValues("searchKey")[0]);
            queryWrapper.select("id");
        }
        Page<FriendStrategy> page = new Page<FriendStrategy>();
        IPage<FriendStrategy> pageList = friendStrategyService.page(page, queryWrapper);
        return Result.OK(pageList);
    }


    /**
     * 添加
     *
     * @param friendStrategy
     * @return
     */
    @AutoLog(value = "游友攻略-添加")
    @ApiOperation(value = "游友攻略-添加", notes = "游友攻略-添加")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody FriendStrategy friendStrategy) {
        friendStrategyService.save(friendStrategy);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param friendStrategy
     * @return
     */
    @AutoLog(value = "游友攻略-编辑")
    @ApiOperation(value = "游友攻略-编辑", notes = "游友攻略-编辑")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody FriendStrategy friendStrategy) {
        friendStrategyService.updateById(friendStrategy);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "游友攻略-通过id删除")
    @ApiOperation(value = "游友攻略-通过id删除", notes = "游友攻略-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        friendStrategyService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "游友攻略-批量删除")
    @ApiOperation(value = "游友攻略-批量删除", notes = "游友攻略-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.friendStrategyService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "游友攻略-通过id查询")
    @ApiOperation(value = "游友攻略-通过id查询", notes = "游友攻略-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<FriendStrategy> queryById(@RequestParam(name = "id", required = true) String id) {
        FriendStrategy friendStrategy = friendStrategyService.getById(id);
        if (friendStrategy == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(friendStrategy);
    }

    /**
     * 通过userId查询获赞数
     *
     * @param userId
     * @return
     */
    //@AutoLog(value = "游友攻略-通过id查询")
    @ApiOperation(value = "游友攻略-通过userId查询获赞数", notes = "游友攻略-通过userId查询获赞数")
    @GetMapping(value = "/queryLikeByUserId")
    public Result<Integer> queryLikeById(@RequestParam(name = "userId", required = true) String userId) {
        List<FriendStrategy> friendStrategy = friendStrategyService.list(new LambdaQueryWrapper<FriendStrategy>().eq(FriendStrategy::getUserid, userId));
        Integer num = 0;
        for (FriendStrategy strategy : friendStrategy) {
            num = num + strategy.getLikeCount();

        }
        if (num == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(num);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param friendStrategy
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, FriendStrategy friendStrategy) {
        return super.exportXls(request, friendStrategy, FriendStrategy.class, "游友攻略");
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
        return super.importExcel(request, response, FriendStrategy.class);
    }

    /**
     * 查询游友攻略的导游列表
     */
    @ApiOperation(value = "官方攻略-查询游友攻略的导游列表", notes = "官方攻略-查询游友攻略的导游列表")
    @GetMapping(value = "/selectGuides")
    public Result<List<Guide>> selectGuides(String id) {
        List<String> guideIds = friendIdService.list(new LambdaQueryWrapper<FriendId>().eq(FriendId::getFriendId, id)).stream().map(FriendId::getGuideId).collect(Collectors.toList());
        List<Guide> guideList = touristGuideService.listByIds(guideIds).stream().map(touristGuide -> {Guide guide = new Guide();BeanUtils.copyProperties(touristGuide, guide);return guide;}).collect(Collectors.toList());
        return !guideList.isEmpty() ? Result.ok(guideList) : Result.error("未找到对应数据");
    }
}
