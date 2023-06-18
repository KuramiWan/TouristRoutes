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
 * @Description: 旅行社信息表
 * @Author: jeecg-boot
 * @Date:   2023-06-18
 * @Version: V1.0
 */
@Data
@TableName("travel_agency")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="travel_agency对象", description="旅行社信息表")
public class TravelAgency implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**旅行社名称*/
	@Excel(name = "旅行社名称", width = 15)
    @ApiModelProperty(value = "旅行社名称")
    private java.lang.String agencyName;
	/**旅行社联系人*/
	@Excel(name = "旅行社联系人", width = 15)
    @ApiModelProperty(value = "旅行社联系人")
    private java.lang.String agencyContactName;
	/**联系人电话*/
	@Excel(name = "联系人电话", width = 15)
    @ApiModelProperty(value = "联系人电话")
    private java.lang.String contactPhone;
	/**旅行社邮箱*/
	@Excel(name = "旅行社邮箱", width = 15)
    @ApiModelProperty(value = "旅行社邮箱")
    private java.lang.String agencyEmail;
	/**旅行社地址*/
	@Excel(name = "旅行社地址", width = 15)
    @ApiModelProperty(value = "旅行社地址")
    private java.lang.String agencyAddress;
	/**营业执照号码*/
	@Excel(name = "营业执照号码", width = 15)
    @ApiModelProperty(value = "营业执照号码")
    private java.lang.String businessLicenseNumber;
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
