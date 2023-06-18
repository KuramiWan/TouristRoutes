package org.jeecg.modules.tourist.client.entity;

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
 * @Description: 伴游人的基本信息
 * @Author: jeecg-boot
 * @Date:   2023-06-18
 * @Version: V1.0
 */
@Data
@TableName("escort")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="escort对象", description="伴游人的基本信息")
public class Escort implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**伴游人真实名称*/
	@Excel(name = "伴游人真实名称", width = 15)
    @ApiModelProperty(value = "伴游人真实名称")
    private java.lang.String escortName;
	/**伴游人昵称*/
	@Excel(name = "伴游人昵称", width = 15)
    @ApiModelProperty(value = "伴游人昵称")
    private java.lang.String escortNickname;
	/**伴游人年龄*/
	@Excel(name = "伴游人年龄", width = 15)
    @ApiModelProperty(value = "伴游人年龄")
    private java.lang.Integer escortAge;
	/**伴游人性别*/
	@Excel(name = "伴游人性别", width = 15, dicCode = "gender")
	@Dict(dicCode = "gender")
    @ApiModelProperty(value = "伴游人性别")
    private java.lang.Integer escortGender;
	/**伴游人手机号*/
	@Excel(name = "伴游人手机号", width = 15)
    @ApiModelProperty(value = "伴游人手机号")
    private java.lang.String escortPhoneNumber;
	/**伴游人邮箱*/
	@Excel(name = "伴游人邮箱", width = 15)
    @ApiModelProperty(value = "伴游人邮箱")
    private java.lang.String escortEmail;
	/**伴游人国籍*/
	@Excel(name = "伴游人国籍", width = 15)
    @ApiModelProperty(value = "伴游人国籍")
    private java.lang.String escortNationality;
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
