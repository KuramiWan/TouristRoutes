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
 * @Description: 产品表
 * @Author: jeecg-boot
 * @Date:   2023-07-14
 * @Version: V1.0
 */
@Data
@TableName("product")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="product对象", description="产品表")
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**产品标题*/
	@Excel(name = "产品标题", width = 15)
    @ApiModelProperty(value = "产品标题")
    private java.lang.String proTitle;
    /**产品封面*/
    @Excel(name = "产品封面", width = 15)
    @ApiModelProperty(value = "产品封面")
    private java.lang.String proPageImg;
	/**产品估价*/
	@Excel(name = "产品估价", width = 15)
    @ApiModelProperty(value = "产品估价")
    private java.lang.Double proEvaluate;
	/**产品介绍*/
	@Excel(name = "产品介绍", width = 15)
    @ApiModelProperty(value = "产品介绍")
    private java.lang.String proIntroduction;
    /**成团人数*/
    @Excel(name = "成团人数", width = 15)
    @ApiModelProperty(value = "成团人数")
    private java.lang.Integer proMan;
    /**封面标题*/
    @Excel(name = "封面标题", width = 15)
    @ApiModelProperty(value = "封面标题")
    private java.lang.String proPageTitle;
    /**起始点*/
    @Excel(name = "起始点", width = 15)
    @ApiModelProperty(value = "起始点")
    private java.lang.String origin;
    /**产品时长*/
    @Excel(name = "产品时长", width = 15)
    @ApiModelProperty(value = "产品时长")
    private java.lang.String proDate;
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
