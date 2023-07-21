package org.jeecg.modules.product.entity;

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
 * @Description: 产品日程
 * @Author: jeecg-boot
 * @Date:   2023-07-14
 * @Version: V1.0
 */
@Data
@TableName(value = "schedule", autoResultMap = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="schedule对象", description="产品日程")
public class Schedule implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**对应产品id*/
	@Excel(name = "对应产品id", width = 15)
    @ApiModelProperty(value = "对应产品id")
    private java.lang.String proId;
	/**对应第几天*/
	@Excel(name = "对应第几天", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "对应第几天")
    private java.util.Date schDate;
	/**当天标题*/
	@Excel(name = "当天标题", width = 15)
    @ApiModelProperty(value = "当天标题")
    private java.lang.String schTitle;
	/**当天精选图片*/
	@Excel(name = "当天精选图片", width = 15)
    @TableField(typeHandler = JacksonTypeHandler.class)
    @ApiModelProperty(value = "当天精选图片")
    private List<String> schImgs;
	/**日程详情*/
	@Excel(name = "日程详情", width = 15)
    @ApiModelProperty(value = "日程详情")
    private java.lang.String schContent;
	/**日程地点*/
	@Excel(name = "日程地点", width = 15)
    @ApiModelProperty(value = "日程地点")
    private java.lang.String schAddress;
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
