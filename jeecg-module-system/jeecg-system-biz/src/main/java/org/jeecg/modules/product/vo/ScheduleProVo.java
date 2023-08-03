package org.jeecg.modules.product.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.product.entity.Task;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleProVo {

    /**主键*/
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
    /**对应产品id*/
    @Excel(name = "对应产品id", width = 15)
    @ApiModelProperty(value = "对应产品id")
    private java.lang.String proId;
    /**对应第几天*/
    @Excel(name = "对应第几天", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "对应第几天")
    private java.util.Date schDate;
    /**当天标题*/
    @Excel(name = "当天标题", width = 15)
    @ApiModelProperty(value = "当天标题")
    private java.lang.String schTitle;
    /**当天精选图片*/
    @Excel(name = "当天精选图片", width = 15)
    @TableField(typeHandler = JacksonTypeHandler.class)
    @ApiModelProperty(value = "当天精选图片")
    private List<String> schImgs;
    /**日程详情*/
    @Excel(name = "日程详情", width = 15)
    @ApiModelProperty(value = "日程详情")
    private java.lang.String schContent;
    /**早餐*/
    @Excel(name = "早餐", width = 15)
    @ApiModelProperty(value = "早餐")
    private java.lang.String breakfast;
    /**午餐*/
    @Excel(name = "午餐", width = 15)
    @ApiModelProperty(value = "午餐")
    private java.lang.String lunch;
    /**晚餐*/
    @Excel(name = "晚餐", width = 15)
    @ApiModelProperty(value = "晚餐")
    private java.lang.String dinner;
    /**日程地点*/
    @Excel(name = "日程地点", width = 15)
    @ApiModelProperty(value = "日程地点")
    private java.lang.String schAddress;
    /**酒店*/
    @Excel(name = "酒店", width = 15)
    @ApiModelProperty(value = "酒店")
    private java.lang.String hotel;
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

    private List<Task> tasks;
}
