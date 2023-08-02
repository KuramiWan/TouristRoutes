package org.jeecg.modules.follow.service.impl;

import org.jeecg.modules.follow.entity.Follow;
import org.jeecg.modules.follow.mapper.FollowMapper;
import org.jeecg.modules.follow.service.IFollowService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 关注人表
 * @Author: jeecg-boot
 * @Date:   2023-08-02
 * @Version: V1.0
 */
@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements IFollowService {

}
