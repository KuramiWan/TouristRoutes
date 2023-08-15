package org.jeecg.modules.user.traveler.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.product.entity.PriceDate;
import org.jeecg.modules.user.traveler.entity.Traveler;
import org.jeecg.modules.user.traveler.service.TravelerService;
import org.jeecg.modules.user.userinfo.entity.WxClientUserinfo;
import org.jeecg.modules.user.userinfo.service.IWxClientUserinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 出行人(Traveler)表控制层
 *
 * @author makejava
 * @since 2023-07-13 15:27:04
 */
@RestController
@RequestMapping("traveler")
@Slf4j
@Api
public class TravelerController {
    /**
     * 服务对象
     */
    @Autowired
    private TravelerService travelerService;

    @Autowired
    private IWxClientUserinfoService wxClientUserinfoService;

    /**
     * 分页查询所有数据
     *
     * @param page     分页对象
     * @param traveler 查询实体
     * @return 所有数据
     */
    @GetMapping("getList")
    public Result selectAll(Page<Traveler> page, Traveler traveler) {
        return Result.ok(this.travelerService.page(page, new QueryWrapper<>(traveler)));
    }

    /**
     *   查询
     *
     * @param
     * @return
     */
    @AutoLog(value = "出行人-查询")
    @ApiOperation(value="出行人-查询", notes="出行人-查询")
    @GetMapping(value = "/queryById")
    public Result<Traveler> queryById(@RequestParam(name="id",required = true) String id) {
        Traveler traveler = travelerService.getOne(new LambdaQueryWrapper<Traveler>().eq(Traveler::getId, id));
        return Result.OK(traveler);
    }

    /**
     *   根据Ids查询
     *
     * @param
     * @return
     */
    @AutoLog(value = "出行人-根据Ids查询")
    @ApiOperation(value="出行人-根据Ids查询", notes="根据Ids查询-查询")
    @GetMapping(value = "/queryListById")
    public Result<List<Traveler>> queryListById(@RequestParam(name="ids",required = true) List<String> ids) {
        List<Traveler> travelers = new ArrayList<>();
        for (String id : ids) {
            Traveler traveler = travelerService.getById(id);
            travelers.add(traveler);
        }

        return Result.OK(travelers);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("queryById/{id}")
    public Result selectOne(@PathVariable Serializable id) {
        return Result.ok(this.travelerService.getById(id));
    }

    /**
     * 通过用户id查询该用户的出行人信息
     */
    @GetMapping("selectListByUserId")
    public Result<List<Traveler>> selectListByUserId(HttpServletRequest req) {
        //根据openid查出用户id
        String openid = req.getHeader("openid");
        LambdaQueryWrapper<WxClientUserinfo> wxClientUserinfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        wxClientUserinfoLambdaQueryWrapper.eq(WxClientUserinfo::getOpenid, openid);
        WxClientUserinfo userinfo = wxClientUserinfoService.getOne(wxClientUserinfoLambdaQueryWrapper);
        String userId = userinfo.getId();

        LambdaQueryWrapper<Traveler> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Traveler::getUserId, userId);
        List<Traveler> list = travelerService.list(wrapper);

        return Result.ok(list);
    }

    /**
     * 新增数据
     *
     * @param traveler 实体对象
     * @return 新增结果
     */
    @PostMapping("add")
    public Result insert(@RequestBody Traveler traveler) {
        return Result.ok(this.travelerService.save(traveler));
    }

    /**
     * 某个用户新增出行人信息
     *
     * @param traveler 实体对象
     * @return 新增结果
     */
    @PostMapping("addTravel")
    public Result addTravel(@RequestBody Traveler traveler, HttpServletRequest req) {
        //根据openid查出用户id
        String openid = req.getHeader("openid");
        LambdaQueryWrapper<WxClientUserinfo> wxClientUserinfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        wxClientUserinfoLambdaQueryWrapper.eq(WxClientUserinfo::getOpenid, openid);
        WxClientUserinfo userinfo = wxClientUserinfoService.getOne(wxClientUserinfoLambdaQueryWrapper);
        traveler.setUserId(userinfo.getId());
        return Result.ok(this.travelerService.save(traveler));
    }

    /**
     * 修改数据
     *
     * @param traveler 实体对象
     * @return 修改结果
     */
    @PostMapping("edit")
    public Result update(@RequestBody Traveler traveler) {
        return Result.ok(this.travelerService.updateById(traveler));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @PostMapping("delete")
    public Result delete(@RequestParam("idList") List<Long> idList) {
        return Result.ok(this.travelerService.removeByIds(idList));
    }
}

