package org.jeecg.modules.product.entity;

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
 * @Description: 旅行社
 * @Author: jeecg-boot
 * @Date:   2023-09-19
 * @Version: V1.0
 */
@Data
@TableName("travel_agency")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="travel_agency对象", description="旅行社")
public class TravelAgency implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**旅行社id*/
	@TableId(type = IdType.ASSIGN_ID)
	@ApiModelProperty(value = "旅行社id")
	private java.lang.String id;
	/**旅行社名称*/
	@Excel(name = "旅行社名称", width = 15)
	@ApiModelProperty(value = "旅行社名称")
	private java.lang.String organization;
	/**负责人*/
	@Excel(name = "负责人", width = 15)
	@ApiModelProperty(value = "负责人")
	private java.lang.String name;
	/**负责人电话*/
	@Excel(name = "负责人电话", width = 15)
	@ApiModelProperty(value = "负责人电话")
	private java.lang.String phone;
	/**旅行社地址*/
	@Excel(name = "旅行社地址", width = 15)
	@ApiModelProperty(value = "旅行社地址")
	private java.lang.String address;
	/**客服电话*/
	@Excel(name = "客服电话", width = 15)
	@ApiModelProperty(value = "客服电话")
	private java.lang.String mobile;
}
