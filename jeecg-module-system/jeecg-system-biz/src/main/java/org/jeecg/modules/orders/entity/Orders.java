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
 * @Description: 订单表
 * @Author: jeecg-boot
 * @Date:   2023-06-19
 * @Version: V1.0
 */
@Data
@TableName("orders")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="orders对象", description="订单表")
public class Orders implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
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
	@Dict(dicCode = "pay_status")
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
