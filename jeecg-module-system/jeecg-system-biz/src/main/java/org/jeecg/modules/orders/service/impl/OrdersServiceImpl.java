package org.jeecg.modules.orders.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.orders.entity.Orders;
import org.jeecg.modules.orders.mapper.OrdersMapper;
import org.jeecg.modules.orders.service.IOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 订单表
 * @Author: jeecg-boot
 * @Date: 2023-06-19
 * @Version: V1.0
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {
    private final RedisUtil redisUtil;
    private final OrdersMapper ordersMapper;
    @Autowired
    public OrdersServiceImpl(RedisUtil redisUtil,OrdersMapper ordersMapper){
        this.redisUtil=redisUtil;
        this.ordersMapper=ordersMapper;
    }

    @Override
    public Integer productSales(String productId) {
        OrdersServiceImpl ordersServiceImpl = new OrdersServiceImpl(redisUtil,ordersMapper);
        Integer orderCountFromDatabase=0;
        // 先读取redis
        Integer orderCount = ordersServiceImpl.getOrderCountFromCache(productId);
        // 如果key不存在
        if (orderCount==null) {
            // 从数据库中读取
            orderCountFromDatabase= getOrderCountFromDatabase(productId);
            if (orderCountFromDatabase==0) return 0;
            // 之后写入redis
            setOrderCountToCache(productId,orderCountFromDatabase);
            // 重新在redis里面拿
            orderCount=orderCountFromDatabase;
        }
        return orderCount;
    }
    public Integer getOrderCountFromCache(String productId) {
        String key = "orderCount:" + productId;
        Integer count ;
        // 如果有key
        if (redisUtil.hasKey(key)){
            count = (Integer) redisUtil.get(key);
//            System.out.println(count+"wwwwwwwwwwwwwwwwwwwwwwwwwww");
            return count;
        }else {
            return null;
        }
    }
    public Integer getOrderCountFromDatabase(String productId) {
        LambdaQueryWrapper<Orders> ordersLambdaQueryWrapper = new LambdaQueryWrapper<Orders>().eq(Orders::getProductId,productId).eq(Orders::getPayStatus,1);
//        System.out.println("ddddddddddddddddddddddddddddddddddd");
        return ordersMapper.selectCount(ordersLambdaQueryWrapper).intValue();
    }
    public void setOrderCountToCache(String productId, Integer orderCount) {
        String key = "orderCount:" + productId;
        redisUtil.set(key,orderCount,60000);
//        System.out.println(redisUtil.get(key)+"ccccccccccccccccccccccccccccccccccc");
    }

    // 定时业务，每隔2分钟执行一次
    @Scheduled(fixedRate = 120000)
    public void refreshOrderCountCache() {
        // 找出所有的订单，通过id分类好
        List<Orders> orders = ordersMapper.selectList(new LambdaQueryWrapper<Orders>().eq(Orders::getPayStatus, 1));
        List<String> allProductId = orders.stream().map(Orders::getProductId).collect(Collectors.toList());
        List<String> productIds = orders.stream().map(Orders::getProductId).distinct().collect(Collectors.toList());
        productIds.forEach(
                productId->{
                    Integer frequency = Collections.frequency(allProductId, productId);
                    // 更新redis
                    // System.out.println(productId+"有"+frequency);
                    redisUtil.del(productId);
                    setOrderCountToCache(productId,frequency);
                });
    }


}
