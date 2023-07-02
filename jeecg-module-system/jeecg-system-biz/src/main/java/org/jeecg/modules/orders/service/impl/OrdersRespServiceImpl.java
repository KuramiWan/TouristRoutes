package org.jeecg.modules.orders.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.beanutils.BeanUtils;
import org.jeecg.modules.orders.entity.Orders;
import org.jeecg.modules.orders.mapper.OrdersMapper;
import org.jeecg.modules.orders.resp.OrdersResp;
import org.jeecg.modules.orders.service.IOrdersRespService;
import org.jeecg.modules.orders.service.IOrdersService;
import org.jeecg.modules.product.product.entity.Product;
import org.jeecg.modules.product.product.mapper.ProductMapper;
import org.jeecg.modules.tourist.client.entity.Tourist;
import org.jeecg.modules.tourist.client.mapper.TouristMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author QiaoQi
 * @version 1.0
 */
@Service
public class OrdersRespServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersRespService {
    @Resource
    private ProductMapper productMapper;

    @Resource
    private TouristMapper touristMapper;

    @Resource
    private IOrdersService ordersService;

    @Override
    public List<OrdersResp> getList(Page<Orders> page, QueryWrapper<Orders> wrapper) throws InvocationTargetException, IllegalAccessException {
        List<OrdersResp> ordersRespPage = new ArrayList<>();
        Page<Orders> ordersPage = ordersService.page(page, wrapper);
        List<Orders> records = ordersPage.getRecords();
        for (Orders record : records) {
            OrdersResp ordersResp = new OrdersResp();
            BeanUtils.copyProperties(ordersResp, record);

            String productId = record.getProductId();
            Product product = productMapper.selectById(productId);
            BeanUtils.copyProperties(ordersResp, product);

            String userId = record.getUserId();
            Tourist tourist = touristMapper.selectById(userId);
            BeanUtils.copyProperties(ordersResp, tourist);

            ordersRespPage.add(ordersResp);
        }
        return ordersRespPage;
    }
}
