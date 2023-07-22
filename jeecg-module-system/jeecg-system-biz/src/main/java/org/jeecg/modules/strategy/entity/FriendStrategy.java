package org.jeecg.modules.strategy.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import java.util.List;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.jeecg.modules.utils.json.CommonStringTypeHandler;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 游友攻略
 * @Author: jeecg-boot
 * @Date: 2023-07-22
 * @Version: V1.0
 */
@Data
@TableName(value = "friend_strategy",autoResultMap = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "friend_strategy对象", description = "游友攻略")
public class FriendStrategy implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createBy;
    /**
     * 创建日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private String updateBy;
    /**
     * 更新日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
    /**
     * 所属部门
     */
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
    /**
     * 攻略用户id
     */
    @Excel(name = "攻略用户id", width = 15)
    @ApiModelProperty(value = "攻略用户id")
    private String userid;
    /**
     * 攻略标题
     */
    @Excel(name = "攻略标题", width = 15)
    @ApiModelProperty(value = "攻略标题")
    private String title;
    /**
     * 攻略内容
     */
    @Excel(name = "攻略内容", width = 15)
    @ApiModelProperty(value = "攻略内容")
    private String content;
    /**
     * 攻略图片
     */
    @Excel(name = "攻略图片", width = 15)
    @ApiModelProperty(value = "攻略图片")
    @TableField(typeHandler = CommonStringTypeHandler.class)
    private List<String> img;
    /**
     * 转发数
     */
    @Excel(name = "转发数", width = 15)
    @ApiModelProperty(value = "转发数")
    private Integer forwardCount;
    /**
     * 点赞数
     */
    @Excel(name = "点赞数", width = 15)
    @ApiModelProperty(value = "点赞数")
    private Integer likeCount;
    /**
     * 发布位置
     */
    @Excel(name = "发布位置", width = 15)
    @ApiModelProperty(value = "发布位置")
    private String position;
}
