package org.jeecg.modules.servicecomments.service.impl;

import org.jeecg.modules.servicecomments.entity.ServiceComments;
import org.jeecg.modules.servicecomments.mapper.ServiceCommentsMapper;
import org.jeecg.modules.servicecomments.service.IServiceCommentsService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 导游服务评论表
 * @Author: jeecg-boot
 * @Date:   2023-07-13
 * @Version: V1.0
 */
@Service
public class ServiceCommentsServiceImpl extends ServiceImpl<ServiceCommentsMapper, ServiceComments> implements IServiceCommentsService {

}
