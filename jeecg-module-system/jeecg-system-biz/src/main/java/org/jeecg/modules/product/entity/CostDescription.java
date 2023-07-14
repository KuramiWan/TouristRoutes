package org.jeecg.modules.product.entity;

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
 * @Description: 费用说明
 * @Author: jeecg-boot
 * @Date:   2023-07-14
 * @Version: V1.0
 */
@Data
@TableName("cost_description")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="cost_description对象", description="费用说明")
public class CostDescription implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**对应产品id*/
	@Excel(name = "对应产品id", width = 15)
    @ApiModelProperty(value = "对应产品id")
    private java.lang.String proId;
	/**前提摘要*/
	@Excel(name = "前提摘要", width = 15)
    @ApiModelProperty(value = "前提摘要")
    private java.lang.String cdDigest;
	/**景点门票*/
	@Excel(name = "景点门票", width = 15)
    @ApiModelProperty(value = "景点门票")
    private java.lang.String cdTicket;
	/**当地交通*/
	@Excel(name = "当地交通", width = 15)
    @ApiModelProperty(value = "当地交通")
    private java.lang.String cdTraffic;
	/**餐饮*/
	@Excel(name = "餐饮", width = 15)
    @ApiModelProperty(value = "餐饮")
    private java.lang.String cdFood;
	/**司导服务*/
	@Excel(name = "司导服务", width = 15)
    @ApiModelProperty(value = "司导服务")
    private java.lang.String cdGuide;
	/**其他*/
	@Excel(name = "其他", width = 15)
    @ApiModelProperty(value = "其他")
    private java.lang.String cdOther;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
}
