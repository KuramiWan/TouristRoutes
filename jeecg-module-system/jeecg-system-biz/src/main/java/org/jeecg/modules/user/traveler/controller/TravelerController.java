package org.jeecg.modules.user.traveler.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.user.traveler.entity.Traveler;
import org.jeecg.modules.user.traveler.service.TravelerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
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

