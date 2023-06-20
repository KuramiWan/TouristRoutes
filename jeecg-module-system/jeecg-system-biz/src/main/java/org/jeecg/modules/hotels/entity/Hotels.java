package org.jeecg.modules.hotels.entity;

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
 * @Description: 酒店管理表
 * @Author: jeecg-boot
 * @Date:   2023-06-20
 * @Version: V1.0
 */
@Data
@TableName("hotels")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="hotels对象", description="酒店管理表")
public class Hotels implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**酒店名称*/
	@Excel(name = "酒店名称", width = 15)
    @ApiModelProperty(value = "酒店名称")
    private java.lang.String hotelName;
	/**酒店地址*/
	@Excel(name = "酒店地址", width = 15)
    @ApiModelProperty(value = "酒店地址")
    private java.lang.String hotelAddress;
	/**酒店电话*/
	@Excel(name = "酒店电话", width = 15)
    @ApiModelProperty(value = "酒店电话")
    private java.lang.String hotelPhone;
	/**酒店邮箱*/
	@Excel(name = "酒店邮箱", width = 15)
    @ApiModelProperty(value = "酒店邮箱")
    private java.lang.String hotelEmail;
	/**酒店星级*/
	@Excel(name = "酒店星级", width = 15, dicCode = "hotel_rating")
	@Dict(dicCode = "hotel_rating")
    @ApiModelProperty(value = "酒店星级")
    private java.lang.String hotelRating;
	/**酒店描述*/
	@Excel(name = "酒店描述", width = 15)
    @ApiModelProperty(value = "酒店描述")
    private java.lang.String hotelDescription;
	/**酒店图片*/
	@Excel(name = "酒店图片", width = 15)
    @ApiModelProperty(value = "酒店图片")
    private java.lang.String hotelImage;
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
