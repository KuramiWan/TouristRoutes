package org.jeecg.modules.user.contactPerson.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.user.contactPerson.entity.ContactPerson;

/**
 * 联系人(ContactPerson)表服务接口
 *
 * @author makejava
 * @since 2023-07-13 15:25:37
 */
public interface ContactPersonService extends IService<ContactPerson> {
    public Result selectAll(Page<ContactPerson> page, ContactPerson contactPerson);

    public Result selectAll(Page<ContactPerson> page);

    public Result selectOne(Long id);

    public Result insert(ContactPerson contactPerson);

    public Result update(ContactPerson contactPerson);

    public Result delete(Long id);
}

