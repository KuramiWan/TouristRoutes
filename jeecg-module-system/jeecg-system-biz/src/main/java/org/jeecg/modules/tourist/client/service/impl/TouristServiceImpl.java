package org.jeecg.modules.tourist.client.service.impl;


import org.jeecg.modules.tourist.client.entity.Tourist;
import org.jeecg.modules.tourist.client.mapper.TouristMapper;
import org.jeecg.modules.tourist.client.service.ITouristService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 游客信息表
 * @Author: jeecg-boot
 * @Date:   2023-06-18
 * @Version: V1.0
 */
@Service
public class TouristServiceImpl extends ServiceImpl<TouristMapper, Tourist> implements ITouristService {

}
