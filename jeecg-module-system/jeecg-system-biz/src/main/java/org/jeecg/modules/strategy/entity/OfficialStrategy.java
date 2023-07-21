package org.jeecg.modules.strategy.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import java.util.List;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.jeecg.modules.utils.json.CommonIntegerTypeHandler;
import org.jeecg.modules.utils.json.CommonStringTypeHandler;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 官方攻略
 * @Author: jeecg-boot
 * @Date:   2023-07-21
 * @Version: V1.0
 */
@Data
@TableName("official_strategy")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="official_strategy对象", description="官方攻略")
public class OfficialStrategy implements Serializable {
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
	/**攻略标题*/
	@Excel(name = "攻略标题", width = 15)
    @ApiModelProperty(value = "攻略标题")
    private String title;
	/**攻略图片*/
	@Excel(name = "攻略图片", width = 15)
    @ApiModelProperty(value = "攻略图片")
    private String img;
	/**攻略浏览量*/
	@Excel(name = "攻略浏览量", width = 15)
    @ApiModelProperty(value = "攻略浏览量")
    private Integer views;
	/**攻略标签*/
	@Excel(name = "攻略标签", width = 15)
    @ApiModelProperty(value = "攻略标签")
    private String tag;
	/**地点标题*/
	@Excel(name = "地点标题", width = 15)
    @ApiModelProperty(value = "地点标题")
    @TableField(typeHandler = CommonStringTypeHandler.class)
    private List<String> locationTitle;
	/**地点介绍*/
	@Excel(name = "地点介绍", width = 15)
    @ApiModelProperty(value = "地点介绍")
    @TableField(typeHandler = CommonStringTypeHandler.class)
    private List<String> locationDesc;
	/**地点推荐游玩小时数*/
	@Excel(name = "地点推荐游玩小时数", width = 15)
    @ApiModelProperty(value = "地点推荐游玩小时数")
    @TableField(typeHandler = CommonIntegerTypeHandler.class)
    private List<Integer> locationHour;
}
