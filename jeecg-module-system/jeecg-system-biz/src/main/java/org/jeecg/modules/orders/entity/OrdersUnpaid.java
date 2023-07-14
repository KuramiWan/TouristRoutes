package org.jeecg.modules.orders.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 未付款的订单表
 * @Author: jeecg-boot
 * @Date:   2023-07-13
 * @Version: V1.0
 */
@Data
@TableName("orders_unpaid")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="orders_unpaid对象", description="未付款的订单表")
public class OrdersUnpaid implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
	/**产品id*/
	@Excel(name = "产品id", width = 15)
    @ApiModelProperty(value = "产品id")
    private String productId;
	/**出发日期*/
	@Excel(name = "出发日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "出发日期")
    private Date dateStarted;
	/**结束日期*/
	@Excel(name = "结束日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "结束日期")
    private Date dateClosed;
	/**套餐id*/
	@Excel(name = "套餐id", width = 15)
    @ApiModelProperty(value = "套餐id")
    private String plansId;
	/**用户id*/
	@Excel(name = "用户id", width = 15)
    @ApiModelProperty(value = "用户id")
    private String userId;
	/**联系人id*/
	@Excel(name = "联系人id", width = 15)
    @ApiModelProperty(value = "联系人id")
    private String contactId;
	/**出行人id(数组)*/
	@Excel(name = "出行人id(数组)", width = 15)
    @ApiModelProperty(value = "出行人id(数组)")
    private String travellerId;
	/**成人个数*/
	@Excel(name = "成人个数", width = 15)
    @ApiModelProperty(value = "成人个数")
    private Integer adultCount;
	/**儿童个数*/
	@Excel(name = "儿童个数", width = 15)
    @ApiModelProperty(value = "儿童个数")
    private Integer childrenCount;
	/**待付款金额*/
	@Excel(name = "待付款金额", width = 15)
    @ApiModelProperty(value = "待付款金额")
    private Double payingMoney;
	/**优惠卷抵扣金额*/
	@Excel(name = "优惠卷抵扣金额", width = 15)
    @ApiModelProperty(value = "优惠卷抵扣金额")
    private Double coupon;
	/**保险id*/
	@Excel(name = "保险id", width = 15)
    @ApiModelProperty(value = "保险id")
    private String insureId;
	/**订单备注*/
	@Excel(name = "订单备注", width = 15)
    @ApiModelProperty(value = "订单备注")
    private String note;
	/**游侠币抵扣金额*/
	@Excel(name = "游侠币抵扣金额", width = 15)
    @ApiModelProperty(value = "游侠币抵扣金额")
    private Double youxiabi;
}
