package org.jeecg.modules.orders.service.impl;

import org.jeecg.modules.orders.entity.OrdersPaid;
import org.jeecg.modules.orders.mapper.OrdersPaidMapper;
import org.jeecg.modules.orders.service.IOrdersPaidService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 已付款的订单表
 * @Author: jeecg-boot
 * @Date:   2023-07-13
 * @Version: V1.0
 */
@Service
public class OrdersPaidServiceImpl extends ServiceImpl<OrdersPaidMapper, OrdersPaid> implements IOrdersPaidService {

}
