package org.jeecg.modules.orders.service.impl;

import org.jeecg.modules.orders.entity.Orders;
import org.jeecg.modules.orders.mapper.OrdersMapper;
import org.jeecg.modules.orders.service.IOrdersService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 订单表
 * @Author: jeecg-boot
 * @Date:   2023-06-19
 * @Version: V1.0
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {

}
