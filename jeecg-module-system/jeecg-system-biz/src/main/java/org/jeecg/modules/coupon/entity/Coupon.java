package org.jeecg.modules.coupon.entity;

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
 * @Description: 优惠券表
 * @Author: jeecg-boot
 * @Date:   2023-08-05
 * @Version: V1.0
 */
@Data
@TableName("coupon")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="coupon对象", description="优惠券表")
public class Coupon implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**优惠金额*/
	@Excel(name = "优惠金额", width = 15)
    @ApiModelProperty(value = "优惠金额")
    private Double discountAmount;
	/**条件(满足多少金额可以使用)*/
	@Excel(name = "条件(满足多少金额可以使用)", width = 15)
    @ApiModelProperty(value = "条件(满足多少金额可以使用)")
    private Double conditionalAmount;
	/**优惠券名*/
	@Excel(name = "优惠券名", width = 15)
    @ApiModelProperty(value = "优惠券名")
    private String title;
	/**产品可用范围(1是全部 0是部分)*/
	@Excel(name = "产品可用范围(1是全部 0是部分)", width = 15)
    @ApiModelProperty(value = "产品可用范围(1是全部 0是部分)")
    private Integer useRange;
	/**有效期（最后日期）*/
	@Excel(name = "有效期（最后日期）", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "有效期（最后日期）")
    private Date usefulDate;
	/**使用规则*/
	@Excel(name = "使用规则", width = 15)
    @ApiModelProperty(value = "使用规则")
    private String ruleUse;
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
