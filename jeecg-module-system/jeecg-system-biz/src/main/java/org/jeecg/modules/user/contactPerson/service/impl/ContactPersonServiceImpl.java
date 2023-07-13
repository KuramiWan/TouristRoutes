package org.jeecg.modules.user.contactPerson.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.user.contactPerson.dao.ContactPersonDao;
import org.jeecg.modules.user.contactPerson.entity.ContactPerson;
import org.jeecg.modules.user.contactPerson.service.ContactPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 联系人(ContactPerson)表服务实现类
 *
 * @author makejava
 * @since 2023-07-13 15:25:37
 */
@Service("contactPersonService")
public class ContactPersonServiceImpl extends ServiceImpl<ContactPersonDao, ContactPerson> implements ContactPersonService {

    @Autowired
    private ContactPersonDao contactPersonDao;

    public Result selectAll(Page<ContactPerson> page, ContactPerson contactPerson){
        if (contactPerson != null){
            return Result.ok(contactPersonDao.selectPage(page, new QueryWrapper<>(contactPerson)));
        }
        return Result.error("参数为空");
    }

    public Result selectAll(Page<ContactPerson> page){
        return Result.ok(contactPersonDao.selectPage(page, new LambdaQueryWrapper<ContactPerson>()));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    public Result selectOne(Long id) {
        return Result.ok(contactPersonDao.selectById(id));
    }

    /**
     * 新增数据
     *
     * @param contactPerson 实体对象
     * @return 新增结果
     */
    @PostMapping("add")
    public Result insert(ContactPerson contactPerson) {
        return Result.ok(contactPersonDao.insert(contactPerson));
    }

    /**
     * 修改数据
     *
     * @param contactPerson 实体对象
     * @return 修改结果
     */
    @PostMapping("edit")
    public Result update(ContactPerson contactPerson) {
        return Result.ok(contactPersonDao.updateById(contactPerson));
    }

    /**
     * 删除数据
     *
     * @param id 主键结合
     * @return 删除结果
     */
    @PostMapping("delete")
    public Result delete(Long id) {
        return Result.ok(contactPersonDao.deleteById(id));
    }


}

