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
 * @Description: 每天的产品价格表
 * @Author: jeecg-boot
 * @Date:   2023-07-14
 * @Version: V1.0
 */
@Data
@TableName("price_date")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="price_date对象", description="每天的产品价格表")
public class PriceDate implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**对应产品id*/
	@Excel(name = "对应产品id", width = 15)
    @ApiModelProperty(value = "对应产品id")
    private java.lang.String proId;
	/**产品日期*/
	@Excel(name = "产品日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "产品日期")
    private java.util.Date pdDate;
	/**当天日期对应的价格*/
	@Excel(name = "当天日期对应的价格", width = 15)
    @ApiModelProperty(value = "当天日期对应的价格")
    private java.lang.Double pdPrice;
	/**当天报名人数*/
	@Excel(name = "当天报名人数", width = 15)
    @ApiModelProperty(value = "当天报名人数")
    private java.lang.Integer pdEnrollment;
	/**当天是否人满*/
	@Excel(name = "当天是否人满", width = 15)
    @ApiModelProperty(value = "当天是否人满")
    private java.lang.Integer pdFull;
    /**当天最多可报名人数*/
    @Excel(name = "当天最多可报名人数", width = 15)
    @ApiModelProperty(value = "当天最多可报名人数")
    private java.lang.Integer pdMaxMan;
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
