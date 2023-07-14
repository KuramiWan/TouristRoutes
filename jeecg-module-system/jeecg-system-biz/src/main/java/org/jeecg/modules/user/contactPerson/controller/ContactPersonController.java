package org.jeecg.modules.user.contactPerson.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.user.contactPerson.entity.ContactPerson;
import org.jeecg.modules.user.contactPerson.service.ContactPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

/**
 * 联系人(ContactPerson)表控制层
 *
 * @author makejava
 * @since 2023-07-13 15:25:36
 */
@RestController
@RequestMapping("contactPerson")
@Slf4j
@Api(tags = "联系人")
public class ContactPersonController extends JeecgController<ContactPerson, ContactPersonService> {
    /**
     * 服务对象
     */
    @Autowired
    private ContactPersonService contactPersonService;

    /**
     * 分页查询所有数据
     *
     * @param page          分页对象
     * @param contactPerson 查询实体
     * @return 所有数据
     */
    @GetMapping("getList")
    public Result selectAll(Page<ContactPerson> page, ContactPerson contactPerson) {
        return Result.ok(this.contactPersonService.page(page,
                new LambdaQueryWrapper<ContactPerson>().eq(ContactPerson::getOrderId,contactPerson.getId())));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("queryById/{id}")
    public Result selectOne(@PathVariable Serializable id) {
        return Result.ok(this.contactPersonService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param contactPerson 实体对象
     * @return 新增结果
     */
    @PostMapping("add")
    public Result insert(@RequestBody ContactPerson contactPerson) {
        return Result.ok(this.contactPersonService.save(contactPerson));
    }

    /**
     * 修改数据
     *
     * @param contactPerson 实体对象
     * @return 修改结果
     */
    @PostMapping("edit")
    public Result update(@RequestBody ContactPerson contactPerson) {
        return Result.ok(this.contactPersonService.updateById(contactPerson));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @PostMapping("delete")
    public Result delete(@RequestParam("idList") List<Long> idList) {
        return Result.ok(this.contactPersonService.removeByIds(idList));
    }
}

