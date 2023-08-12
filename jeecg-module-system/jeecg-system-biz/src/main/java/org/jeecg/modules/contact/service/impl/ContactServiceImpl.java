package org.jeecg.modules.contact.service.impl;

import org.jeecg.modules.contact.entity.Contact;
import org.jeecg.modules.contact.mapper.ContactMapper;
import org.jeecg.modules.contact.service.IContactService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 联系我们表
 * @Author: jeecg-boot
 * @Date:   2023-08-12
 * @Version: V1.0
 */
@Service
public class ContactServiceImpl extends ServiceImpl<ContactMapper, Contact> implements IContactService {

}
