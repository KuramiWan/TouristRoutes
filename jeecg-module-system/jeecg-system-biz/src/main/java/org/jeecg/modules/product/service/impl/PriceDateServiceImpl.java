package org.jeecg.modules.product.service.impl;

import org.jeecg.modules.product.entity.PriceDate;
import org.jeecg.modules.product.mapper.PriceDateMapper;
import org.jeecg.modules.product.service.IPriceDateService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 每天的产品价格表
 * @Author: jeecg-boot
 * @Date:   2023-07-14
 * @Version: V1.0
 */
@Service
public class PriceDateServiceImpl extends ServiceImpl<PriceDateMapper, PriceDate> implements IPriceDateService {

}
