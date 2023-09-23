package org.jeecg.modules.product.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.modules.product.entity.*;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

@Data
public class TravelAgencyVo {
	
	/**
	 * 主键
	 */
	@TableId(type = IdType.ASSIGN_ID)
	@ApiModelProperty(value = "主键")
	private java.lang.String id;
	/**
	 * 旅行社名称
	 */
	@Excel(name = "旅行社名称", width = 15)
	@ApiModelProperty(value = "旅行社名称")
	private java.lang.String organization;
	/**
	 * 负责人
	 */
	@Excel(name = "负责人", width = 15)
	@ApiModelProperty(value = "负责人")
	private java.lang.String name;
	/**
	 * 负责人电话
	 */
	@Excel(name = "负责人电话", width = 15)
	@ApiModelProperty(value = "负责人电话")
	private java.lang.String phone;
	/**
	 * 旅行社地址
	 */
	@Excel(name = "旅行社地址", width = 100)
	@ApiModelProperty(value = "旅行社地址")
	private java.lang.String address;
	/**
	 * 客服电话
	 */
	@Excel(name = "客服电话", width = 15)
	@ApiModelProperty(value = "客服电话")
	private java.lang.String mobile;
}
