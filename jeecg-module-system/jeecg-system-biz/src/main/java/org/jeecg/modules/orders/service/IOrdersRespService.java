package org.jeecg.modules.orders.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.orders.entity.Orders;
import org.jeecg.modules.orders.resp.OrdersResp;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author QiaoQi
 * @version 1.0
 */
public interface IOrdersRespService extends IService<Orders> {
    // 订单查询详细信息
    public List<OrdersResp> getList(Page<Orders> page, QueryWrapper<Orders> wrapper) throws InvocationTargetException, IllegalAccessException;
}
