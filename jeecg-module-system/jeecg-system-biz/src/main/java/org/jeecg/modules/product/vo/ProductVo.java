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
public class ProductVo {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
    /**
     * 产品标题
     */
    @Excel(name = "产品标题", width = 15)
    @ApiModelProperty(value = "产品标题")
    private java.lang.String proTitle;
    /**
     * 产品封面
     */
    @Excel(name = "产品封面", width = 15)
    @ApiModelProperty(value = "产品封面")
    private java.lang.String proPageImg;
    /**
     * 产品海报
     */
    @Excel(name = "产品海报", width = 15)
    @ApiModelProperty(value = "产品海报")
    private java.lang.String posters;
    /**
     * 产品估价
     */
    @Excel(name = "产品估价", width = 15)
    @ApiModelProperty(value = "产品估价")
    private java.lang.Double proEvaluate;
    /**
     * 产品介绍
     */
    @Excel(name = "产品介绍", width = 15)
    @ApiModelProperty(value = "产品介绍")
    private java.lang.String proIntroduction;
    /**
     * 成团人数
     */
    @Excel(name = "成团人数", width = 15)
    @ApiModelProperty(value = "成团人数")
    private java.lang.Integer proMan;
    /**
     * 封面标题
     */
    @Excel(name = "封面标题", width = 15)
    @ApiModelProperty(value = "封面标题")
    private java.lang.String proPageTitle;
    /**
     * 起始点
     */
    @Excel(name = "起始点", width = 15)
    @ApiModelProperty(value = "起始点")
    private java.lang.String origin;
    /**
     * 产品时长
     */
    @Excel(name = "产品时长", width = 15)
    @ApiModelProperty(value = "产品时长")
    private java.lang.Integer proDate;
    /**
     * 产品售卖数量
     */
    @Excel(name = "产品售卖数量", width = 15)
    @ApiModelProperty(value = "产品售卖数量")
    private int soldNumber;
    /**
     * 位于地点
     */
    @Excel(name = "位于地点", width = 15)
    @ApiModelProperty(value = "位于地点")
    private String local;
    /**
     * 详细地点(小标题)
     */
    @Excel(name = "详细地点(小标题)", width = 15)
    @ApiModelProperty(value = "详细地点(小标题)")
    private String localDetail;
    /**
     * 推荐指数
     */
    @Excel(name = "推荐指数", width = 15)
    @ApiModelProperty(value = "推荐指数")
    private int recNum;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
    /**
     * 创建日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
    /**
     * 更新日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
    //日程列表
    private List<ScheduleProVo> schedules;
    private List<PriceDate> price_date;
    private List<BatchPackage> batch_package;
    private List<JourneyPackage> journey;
    /**
     * 旅行社id
     */
    @Excel(name = "旅行社id", width = 15)
    @ApiModelProperty(value = "旅行社id")
    private java.lang.String TravelId;
}
