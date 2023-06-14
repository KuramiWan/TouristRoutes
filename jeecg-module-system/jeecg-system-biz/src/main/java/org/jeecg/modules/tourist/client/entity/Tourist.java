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
 * @Description: 游客信息表
 * @Author: jeecg-boot
 * @Date:   2023-06-14
 * @Version: V1.0
 */
@Data
@TableName("tourist")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="tourist对象", description="游客信息表")
public class Tourist implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**游客真实名称*/
	@Excel(name = "游客真实名称", width = 15)
    @ApiModelProperty(value = "游客真实名称")
    private String touristName;
	/**游客昵称*/
	@Excel(name = "游客昵称", width = 15)
    @ApiModelProperty(value = "游客昵称")
    private String touristNickname;
	/**游客年龄*/
	@Excel(name = "游客年龄", width = 15)
    @ApiModelProperty(value = "游客年龄")
    private Integer touristAge;
	/**游客性别*/
	@Excel(name = "游客性别", width = 15)
    @ApiModelProperty(value = "游客性别")
    private String touristGender;
	/**游客手机号*/
	@Excel(name = "游客手机号", width = 15)
    @ApiModelProperty(value = "游客手机号")
    private String touristPhoneNumber;
	/**游客邮箱*/
	@Excel(name = "游客邮箱", width = 15)
    @ApiModelProperty(value = "游客邮箱")
    private String touristEmail;
	/**游客国籍*/
	@Excel(name = "游客国籍", width = 15)
    @ApiModelProperty(value = "游客国籍")
    private String touristNationality;
	/**游客偏好*/
	@Excel(name = "游客偏好", width = 15)
    @ApiModelProperty(value = "游客偏好")
    private String touristPreference;
	/**游客健康状态*/
	@Excel(name = "游客健康状态", width = 15)
    @ApiModelProperty(value = "游客健康状态")
    private String touristHealthCondition;
	/**游客平台内货币*/
	@Excel(name = "游客平台内货币", width = 15)
    @ApiModelProperty(value = "游客平台内货币")
    private BigDecimal utouristPlatformCurrency;
	/**游客的紧急联系人的手机号码*/
	@Excel(name = "游客的紧急联系人的手机号码", width = 15)
    @ApiModelProperty(value = "游客的紧急联系人的手机号码")
    private String touristEmergencyContact;
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
