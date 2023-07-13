package org.jeecg.modules.orders.service.impl;

import org.jeecg.modules.orders.entity.OrdersUnpaid;
import org.jeecg.modules.orders.mapper.OrdersUnpaidMapper;
import org.jeecg.modules.orders.service.IOrdersUnpaidService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 未付款的订单表
 * @Author: jeecg-boot
 * @Date:   2023-07-13
 * @Version: V1.0
 */
@Service
public class OrdersUnpaidServiceImpl extends ServiceImpl<OrdersUnpaidMapper, OrdersUnpaid> implements IOrdersUnpaidService {

}
