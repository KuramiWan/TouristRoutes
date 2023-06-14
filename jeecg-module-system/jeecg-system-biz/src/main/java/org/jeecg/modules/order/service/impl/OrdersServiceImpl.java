package org.jeecg.modules.order.service.impl;

import org.jeecg.modules.order.entity.Orders;
import org.jeecg.modules.order.mapper.OrdersMapper;
import org.jeecg.modules.order.service.IOrdersService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 订单表
 * @Author: jeecg-boot
 * @Date:   2023-06-14
 * @Version: V1.0
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {

}
