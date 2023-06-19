package org.jeecg.modules.orders.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.orders.entity.Orders;
import org.jeecg.modules.orders.mapper.OrdersMapper;
import org.jeecg.modules.orders.resp.OrdersResp;
import org.jeecg.modules.orders.service.IOrdersService;
import org.jeecg.modules.product.entity.Product;
import org.jeecg.modules.product.mapper.ProductMapper;
import org.jeecg.modules.tourist.client.entity.Tourist;
import org.jeecg.modules.tourist.client.mapper.TouristMapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 订单表
 * @Author: jeecg-boot
 * @Date: 2023-06-19
 * @Version: V1.0
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {
}
