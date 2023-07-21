package org.jeecg.modules.guide.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import java.util.List;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
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
 * @Description: 导游表
 * @Author: jeecg-boot
 * @Date:   2023-07-13
 * @Version: V1.0
 */
@Data
@TableName(value="tourist_guide", autoResultMap = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="tourist_guide对象", description="导游表")
public class TouristGuide implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**导游名字*/
	@Excel(name = "导游名字", width = 15)
    @ApiModelProperty(value = "导游名字")
    private String name;
	/**年龄*/
	@Excel(name = "年龄", width = 15)
    @ApiModelProperty(value = "年龄")
    private String age;
	/**性别*/
	@Excel(name = "性别", width = 15)
    @ApiModelProperty(value = "性别")
    private String sex;
	/**祖籍*/
	@Excel(name = "祖籍", width = 15)
    @ApiModelProperty(value = "祖籍")
    private String descent;
	/**从业年限*/
	@Excel(name = "从业年限", width = 15)
    @ApiModelProperty(value = "从业年限")
    private String employmentTime;
    /**瀑布摘要*/
    @Excel(name = "瀑布摘要", width = 15)
    @ApiModelProperty(value = "瀑布摘要")
    private String summary;
	/**擅长景点*/
	@Excel(name = "擅长景点", width = 15)
    @TableField(typeHandler = JacksonTypeHandler.class)
    @ApiModelProperty(value = "擅长景点")
    private List<String> greatSpots;
	/**个人介绍*/
	@Excel(name = "个人介绍", width = 15)
    @ApiModelProperty(value = "个人介绍")
    private String introduction;
	/**从业资质*/
	@Excel(name = "从业资质", width = 15)
    @ApiModelProperty(value = "从业资质")
    private String qualifications;
	/**导游头像*/
	@Excel(name = "导游头像", width = 15)
    @ApiModelProperty(value = "导游头像")
    private String avatar;
    /**封面*/
    @Excel(name = "封面", width = 15)
    @ApiModelProperty(value = "封面")
    private String pageImg;
    /**点赞数量*/
    @Excel(name = "点赞数量", width = 15)
    @ApiModelProperty(value = "点赞数量")
    private Integer likeNum;
    /**头衔*/
    @Excel(name = "头衔", width = 15)
    @ApiModelProperty(value = "头衔")
    private String honor;
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
}
