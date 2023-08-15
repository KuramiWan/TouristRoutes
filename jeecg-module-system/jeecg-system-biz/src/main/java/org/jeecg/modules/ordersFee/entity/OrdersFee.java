package org.jeecg.modules.ordersFee.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import java.util.List;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
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
 * @Description: 订单的费用明细
 * @Author: jeecg-boot
 * @Date: 2023-08-15
 * @Version: V1.0
 */
@Data
@TableName(value = "orders_fee", autoResultMap = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "orders_fee对象", description = "订单的费用明细")
public class OrdersFee implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createBy;
    /**
     * 创建日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private String updateBy;
    /**
     * 更新日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
    /**
     * 所属部门
     */
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
    /**
     * 订单id
     */
    @Excel(name = "订单id", width = 15)
    @ApiModelProperty(value = "订单id")
    private String ordersPaidId;
    /**
     * 保险名称
     */
    @Excel(name = "保险名称", width = 15)
    @ApiModelProperty(value = "保险名称")
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> insureName;
    /**
     * 保险费用
     */
    @Excel(name = "保险费用", width = 15)
    @ApiModelProperty(value = "保险费用")
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> insureFee;
    /**
     * 套餐名字
     */
    @Excel(name = "套餐名字", width = 15)
    @ApiModelProperty(value = "套餐名字")
    private String packageName;
    /**
     * 套餐费用(成人)
     */
    @Excel(name = "套餐费用(成人)", width = 15)
    @ApiModelProperty(value = "套餐费用(成人)")
    private Double packageFeeAdult;
    /**
     * 套餐费用(儿童)
     */
    @Excel(name = "套餐费用(儿童)", width = 15)
    @ApiModelProperty(value = "套餐费用(儿童)")
    private Double packageFeeChild;
    /**
     * 优惠卷
     */
    @Excel(name = "优惠卷", width = 15)
    @ApiModelProperty(value = "优惠卷")
    private Double cuponFee;
}
