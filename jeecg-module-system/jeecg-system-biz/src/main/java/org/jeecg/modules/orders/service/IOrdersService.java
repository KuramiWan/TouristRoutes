package org.jeecg.modules.orders.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.orders.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.orders.resp.OrdersResp;

/**
 * @Description: 订单表
 * @Author: jeecg-boot
 * @Date:   2023-06-19
 * @Version: V1.0
 */
public interface IOrdersService extends IService<Orders> {

}
