package org.jeecg.modules.user.contactPerson.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.user.contactPerson.entity.ContactPerson;

/**
 * 联系人(ContactPerson)表数据库访问层
 *
 * @author makejava
 * @since 2023-07-13 15:25:36
 */
@Mapper
public interface ContactPersonDao extends BaseMapper<ContactPerson> {

}

