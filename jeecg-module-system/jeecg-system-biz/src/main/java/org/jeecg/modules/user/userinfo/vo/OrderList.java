package org.jeecg.modules.user.userinfo.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecg.modules.orders.entity.OrdersUnpaid;

import java.util.Date;
import java.util.Map;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class OrderList {
    private Map<String,String> product;
    private String orderId;
    private Date dateStarted;
    private Double money;
    private Map<String,Integer> status;
    private OrdersUnpaid ordersUnpaid;
}
