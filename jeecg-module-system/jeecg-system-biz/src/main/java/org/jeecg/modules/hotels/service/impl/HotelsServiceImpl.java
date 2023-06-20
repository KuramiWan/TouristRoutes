package org.jeecg.modules.hotels.service.impl;


import org.jeecg.modules.hotels.entity.Hotels;
import org.jeecg.modules.hotels.mapper.HotelsMapper;
import org.jeecg.modules.hotels.service.IHotelsService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 酒店管理表
 * @Author: jeecg-boot
 * @Date:   2023-06-20
 * @Version: V1.0
 */
@Service
public class HotelsServiceImpl extends ServiceImpl<HotelsMapper, Hotels> implements IHotelsService {

}
