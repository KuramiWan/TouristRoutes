package org.jeecg.modules.orders.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.orders.entity.OrdersPaid;
import org.jeecg.modules.orders.entity.OrdersUnpaid;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.orders.vo.OrdersAllDetails;
import org.jeecg.modules.orders.vo.OrdersPaidDetails;
import org.jeecg.modules.orders.vo.OrdersUnpaidDetails;
import org.jeecg.modules.user.userinfo.entity.WxClientUserinfo;

import java.util.List;

/**
 * @Description: 未付款的订单表
 * @Author: jeecg-boot
 * @Date: 2023-07-13
 * @Version: V1.0
 */
public interface IOrdersUnpaidService extends IService<OrdersUnpaid> {

    // 查询所有订单
    public IPage<OrdersPaidDetails> getOrderAllPaid(Page<OrdersPaid> page, WxClientUserinfo userinfo);

    // 查询待付款订单（未支付 status=0）
    public IPage<OrdersUnpaidDetails> getOrderUnpaid(Page<OrdersUnpaid> page, WxClientUserinfo userinfo);

    // 查询待确认（已支付 status=1）
    public IPage<OrdersPaidDetails> getOrderPaidToConfirm(Page<OrdersPaid> page, WxClientUserinfo userinfo);

    // 查询待出行（已支付 status=2）
    public IPage<OrdersPaidDetails> getOrderPaidToTravel(Page<OrdersPaid> page, WxClientUserinfo userinfo);

    // 查询待评价（已支付 status=3）
    public IPage<OrdersPaidDetails> getOrderPaidToComment(Page<OrdersPaid> page, WxClientUserinfo userinfo);

    // 查询已结束（已支付 status=4）
    public IPage<OrdersPaidDetails> getOrderPaidToOver(Page<OrdersPaid> page, WxClientUserinfo userinfo);

    // 后台查询orders_unpaid订单(status=0)
    public IPage<OrdersUnpaidDetails> getOrderAllUnpaid(Page<OrdersUnpaid> page);

    // 后台查询orders_paid订单
    public IPage<OrdersUnpaidDetails> getAllOrderPaid(Page<OrdersUnpaid> page);


    // 后台查询所有订单信息(未付款 status = 0)
    public IPage<OrdersAllDetails> getOrdersAllUnPaidDetails(Page<OrdersAllDetails> page);

    // 后台查询所有订单信息(已付款 所有的)
    public IPage<OrdersAllDetails> getOrdersAllPaidDetails(Page<OrdersAllDetails> page);

    // 后台未付款订单根据订单id搜索
    public IPage<OrdersAllDetails> getUnpaidOrdersBySearch(Page<OrdersAllDetails> page,String keyword);

    // 后台已付款订单根据订单id搜索
    public IPage<OrdersAllDetails> getPaidOrdersBySearch(Page<OrdersAllDetails> page,String keyword);
}
