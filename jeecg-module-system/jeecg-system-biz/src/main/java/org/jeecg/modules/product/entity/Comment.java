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
 * @Description: 产品评论
 * @Author: jeecg-boot
 * @Date:   2023-07-14
 * @Version: V1.0
 */
@Data
@TableName(value = "comment", autoResultMap = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="comment对象", description="产品评论")
public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**对应产品id*/
	@Excel(name = "对应产品id", width = 15)
    @ApiModelProperty(value = "对应产品id")
    private java.lang.String proId;
	/**评论的用户id*/
	@Excel(name = "评论的用户id", width = 15)
    @ApiModelProperty(value = "评论的用户id")
    private java.lang.String userId;
	/**评论日期*/
	@Excel(name = "评论日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "评论日期")
    private java.util.Date comDate;
	/**评论内容*/
	@Excel(name = "评论内容", width = 15)
    @ApiModelProperty(value = "评论内容")
    private java.lang.String comContent;
	/**评论的图片封面*/
	@Excel(name = "评论的图片封面", width = 15)
    @ApiModelProperty(value = "评论的图片封面")
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> comImg;
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
