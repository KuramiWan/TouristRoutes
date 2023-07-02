package org.jeecg.modules.costs.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 产品费用说明
 * @Author: jeecg-boot
 * @Date:   2023-07-02
 * @Version: V1.0
 */
@Data
@TableName("product_cost")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="product_cost对象", description="产品费用说明")
public class ProductCost implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**景点门票*/
	@Excel(name = "景点门票", width = 15)
    @ApiModelProperty(value = "景点门票")
    private String attractionTickets;
	/**当地交通*/
	@Excel(name = "当地交通", width = 15)
    @ApiModelProperty(value = "当地交通")
    private String localTransportation;
	/**餐饮*/
	@Excel(name = "餐饮", width = 15)
    @ApiModelProperty(value = "餐饮")
    private String restaurant;
	/**司导服务*/
	@Excel(name = "司导服务", width = 15)
    @ApiModelProperty(value = "司导服务")
    private String supervisorService;
	/**其他*/
	@Excel(name = "其他", width = 15)
    @ApiModelProperty(value = "其他")
    private String other;
	/**关联product_id*/
	@Excel(name = "关联product_id", width = 15)
    @ApiModelProperty(value = "关联product_id")
    private String productId;
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
}
