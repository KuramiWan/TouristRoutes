package org.jeecg.modules.user.traveler.relation.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 出行人订单联系id(TravelerOrder)表实体类
 *
 * @author makejava
 * @since 2023-07-13 16:12:06
 */
@SuppressWarnings("serial")
public class TravelerOrder extends Model<TravelerOrder> {
    //ID
    private Long id;
    //真名
    private String orderId;
    //出行人id
    private String travelerId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTravelerId() {
        return travelerId;
    }

    public void setTravelerId(String travelerId) {
        this.travelerId = travelerId;
    }

}

