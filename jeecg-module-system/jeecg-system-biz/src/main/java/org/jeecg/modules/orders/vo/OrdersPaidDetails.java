package org.jeecg.modules.orders.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.modules.Insure.entity.Insure;
import org.jeecg.modules.guide.entity.TouristGuide;
import org.jeecg.modules.product.entity.JourneyPackage;
import org.jeecg.modules.product.entity.Product;
import org.jeecg.modules.productguide.entity.ProductGuide;
import org.jeecg.modules.user.traveler.entity.Traveler;
import org.jeecg.modules.user.userinfo.entity.WxClientUserinfo;
import org.jeecg.modules.utils.json.CommonStringTypeHandler;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @author QiaoQi
 * @version 1.0
 */
@Data
public class OrdersPaidDetails {
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
     * 微信支付订单号
     */
    @Excel(name = "微信支付订单号", width = 15)
    @ApiModelProperty(value = "微信支付订单号")
    private Product transactionId;
    /**
     * 产品
     */
    @Excel(name = "产品", width = 15)
    @ApiModelProperty(value = "产品")
    private Product product;
    /**
     * 出发日期
     */
    @Excel(name = "出发日期", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "出发日期")
    private Date dateStarted;
    /**
     * 结束日期
     */
    @Excel(name = "结束日期", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "结束日期")
    private Date dateClosed;
    /**
     * 行程套餐
     */
    @Excel(name = "行程套餐", width = 15)
    @ApiModelProperty(value = "行程套餐")
    private JourneyPackage journeyPackage;
    /**
     * 选择导游
     */
    @Excel(name = "选择导游", width = 15)
    @ApiModelProperty(value = "选择导游")
    private TouristGuide touristGuide;
    /**
     * 用户
     */
    @Excel(name = "用户", width = 15)
    @ApiModelProperty(value = "用户")
    private WxClientUserinfo userinfo;
    /**
     * 联系人姓名
     */
    @Excel(name = "联系人姓名", width = 15)
    @ApiModelProperty(value = "联系人姓名")
    private String contactName;
    /**
     * 联系人手机号
     */
    @Excel(name = "联系人手机号", width = 15)
    @ApiModelProperty(value = "联系人手机号")
    private String contactPhone;
    /**
     * 出行人
     */
    @Excel(name = "出行人", width = 15)
    @ApiModelProperty(value = "出行人")
    @TableField(typeHandler = CommonStringTypeHandler.class)
    private List<Traveler> travelers;
    /**
     * 成人个数
     */
    @Excel(name = "成人个数", width = 15)
    @ApiModelProperty(value = "成人个数")
    private Integer adultCount;
    /**
     * 儿童个数
     */
    @Excel(name = "儿童个数", width = 15)
    @ApiModelProperty(value = "儿童个数")
    private Integer childrenCount;
    /**
     * 实付金额
     */
    @Excel(name = "实付金额", width = 15)
    @ApiModelProperty(value = "实付金额")
    private Double paidMoney;
    /**
     * 付款方式
     */
    @Excel(name = "付款方式", width = 15)
    @ApiModelProperty(value = "付款方式")
    private String paidMethod;
    /**
     * 优惠卷抵扣金额
     */
    @Excel(name = "优惠卷抵扣金额", width = 15)
    @ApiModelProperty(value = "优惠卷抵扣金额")
    private Double coupon;
    /**
     * 保险
     */
    @Excel(name = "保险", width = 15)
    @ApiModelProperty(value = "保险")
    @TableField(typeHandler = CommonStringTypeHandler.class)
    private List<Insure> insures;
    /**
     * 订单备注
     */
    @Excel(name = "订单备注", width = 15)
    @ApiModelProperty(value = "订单备注")
    private String note;
    /**
     * 游侠币抵扣金额
     */
    @Excel(name = "游侠币抵扣金额", width = 15)
    @ApiModelProperty(value = "游侠币抵扣金额")
    private Double youxiabi;
    /**
     * 订单状态
     */
    @Excel(name = "订单状态", width = 15)
    @ApiModelProperty(value = "订单状态")
    private Integer status;
    /**
     * 是否申请退款
     */
    @Excel(name = "是否申请退款", width = 15)
    @ApiModelProperty(value = "是否申请退款")
    private Integer toRefund;
    /**
     * 是否退款
     */
    @Excel(name = "是否退款", width = 15)
    @ApiModelProperty(value = "是否退款")
    private Integer isRefund;
}
