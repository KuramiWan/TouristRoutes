package org.jeecg.modules.comment.entity;

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
 * @Description: 产品评论表
 * @Author: jeecg-boot
 * @Date:   2023-07-01
 * @Version: V1.0
 */
@Data
@TableName("comment")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="comment对象", description="产品评论表")
public class Comment implements Serializable {
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
	/**用户id*/
	@Excel(name = "用户id", width = 15)
    @ApiModelProperty(value = "用户id")
    private String userId;
	/**评论内容*/
	@Excel(name = "评论内容", width = 15)
    @ApiModelProperty(value = "评论内容")
    private String content;
	/**评论图片*/
	@Excel(name = "评论图片", width = 15)
    @ApiModelProperty(value = "评论图片")
    private String image;
	/**评论时间*/
	@Excel(name = "评论时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "评论时间")
    private Date commentTime;
}
