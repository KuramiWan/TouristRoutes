package org.jeecg.modules.servicecomments.entity;

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
 * @Description: 导游服务评论表
 * @Author: jeecg-boot
 * @Date:   2023-07-13
 * @Version: V1.0
 */
@Data
@TableName("service_comments")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="service_comments对象", description="导游服务评论表")
public class ServiceComments implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**服务评价*/
	@Excel(name = "服务评价", width = 15)
    @ApiModelProperty(value = "服务评价")
    private String comments;
	/**导游id*/
	@Excel(name = "导游id", width = 15)
    @ApiModelProperty(value = "导游id")
    private String guideId;
    /**评论用户id*/
    @Excel(name = "评论用户id", width = 15)
    @ApiModelProperty(value = "评论用户id")
    private String userId;
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
