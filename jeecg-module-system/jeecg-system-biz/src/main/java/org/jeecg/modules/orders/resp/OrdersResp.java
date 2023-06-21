package org.jeecg.modules.orders.resp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecg.modules.product.entity.Product;
import org.jeecg.modules.tourist.client.entity.Tourist;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;

/**
 * @author QiaoQi
 * @version 1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ordersResp对象", description="订单表(详细信息)")
public class OrdersResp implements Serializable{

    // 将orders的product_id映射成产品
    @Excel(name = "描述", width = 15)
    @ApiModelProperty(value = "描述")
    private java.lang.String productDec;
    /**出发地*/
    @Excel(name = "出发地", width = 15)
    @ApiModelProperty(value = "出发地")
    private java.lang.String departure;
    /**海报*/
    @Excel(name = "海报", width = 15)
    @ApiModelProperty(value = "海报")
    private java.lang.String img;

    // 将orders的user_id映射成产品
    /**游客真实名称*/
    @Excel(name = "游客真实名称", width = 15)
    @ApiModelProperty(value = "游客真实名称")
    private java.lang.String touristName;
    /**游客昵称*/
    @Excel(name = "游客昵称", width = 15)
    @ApiModelProperty(value = "游客昵称")
    private java.lang.String touristNickname;
    /**游客年龄*/
    @Excel(name = "游客年龄", width = 15)
    @ApiModelProperty(value = "游客年龄")
    private java.lang.Integer touristAge;
    /**游客性别*/
    @Excel(name = "游客性别", width = 15, dicCode = "gender")
    @Dict(dicCode = "gender")
    @ApiModelProperty(value = "游客性别")
    private java.lang.Integer touristGender;
    /**游客手机号*/
    @Excel(name = "游客手机号", width = 15)
    @ApiModelProperty(value = "游客手机号")
    private java.lang.String touristPhoneNumber;
    /**游客邮箱*/
    @Excel(name = "游客邮箱", width = 15)
    @ApiModelProperty(value = "游客邮箱")
    private java.lang.String touristEmail;
    /**游客国籍*/
    @Excel(name = "游客国籍", width = 15)
    @ApiModelProperty(value = "游客国籍")
    private java.lang.String touristNationality;
    /**游客偏好*/
    @Excel(name = "游客偏好", width = 15)
    @ApiModelProperty(value = "游客偏好")
    private java.lang.String touristPreference;
    /**游客健康状态*/
    @Excel(name = "游客健康状态", width = 15)
    @ApiModelProperty(value = "游客健康状态")
    private java.lang.String touristHealthCondition;
    /**游客平台内货币*/
    @Excel(name = "游客平台内货币", width = 15)
    @ApiModelProperty(value = "游客平台内货币")
    private java.math.BigDecimal utouristPlatformCurrency;
    /**游客的紧急联系人的手机号码*/
    @Excel(name = "游客的紧急联系人的手机号码", width = 15)
    @ApiModelProperty(value = "游客的紧急联系人的手机号码")
    private java.lang.String touristEmergencyContact;


    /**主键*/
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
    /**产品id*/
    @Excel(name = "产品id", width = 15)
    @ApiModelProperty(value = "产品id")
    private java.lang.String productId;
    /**客户id*/
    @Excel(name = "客户id", width = 15)
    @ApiModelProperty(value = "客户id")
    private java.lang.String userId;
    /**订单状态*/
    @Excel(name = "订单状态", width = 15, dicCode = "order_status")
    @Dict(dicCode = "order_status")
    @ApiModelProperty(value = "订单状态")
    private java.lang.Integer orderStatus;
    /**订单金额*/
    @Excel(name = "订单金额", width = 15)
    @ApiModelProperty(value = "订单金额")
    private java.lang.Double orderMoney;
    /**支付方式*/
    @Excel(name = "支付方式", width = 15, dicCode = "pay_method")
    @Dict(dicCode = "pay_method")
    @ApiModelProperty(value = "支付方式")
    private java.lang.Integer payMethod;
    /**支付状态*/
    @Excel(name = "支付状态", width = 15, dicCode = "pay_status")
    @Dict(dicCode = "pay_method")
    @ApiModelProperty(value = "支付状态")
    private java.lang.Integer payStatus;
    /**支付时间*/
    @Excel(name = "支付时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "支付时间")
    private java.util.Date payDate;
    /**支付金额*/
    @Excel(name = "支付金额", width = 15)
    @ApiModelProperty(value = "支付金额")
    private java.lang.Double payMoney;
    /**订单备注*/
    @Excel(name = "订单备注", width = 15)
    @ApiModelProperty(value = "订单备注")
    private java.lang.String note;
    /**创建日期*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
    /**更新日期*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
    /**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
    /**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
    /**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
}
