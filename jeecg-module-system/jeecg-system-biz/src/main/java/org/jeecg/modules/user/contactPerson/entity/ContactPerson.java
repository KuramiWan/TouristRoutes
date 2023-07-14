package org.jeecg.modules.user.contactPerson.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;

/**
 * 联系人(ContactPerson)表实体类
 *
 * @author makejava
 * @since 2023-07-13 15:25:37
 */
@SuppressWarnings("serial")
public class ContactPerson extends Model<ContactPerson> {
    //ID
    private Long id;
    //真名
    private String realName;
    //电话
    private String phone;
    //订单id
    private String orderId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

}

