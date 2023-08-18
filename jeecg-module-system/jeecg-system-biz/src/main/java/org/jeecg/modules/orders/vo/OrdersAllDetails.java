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
import org.jeecg.modules.user.traveler.entity.Traveler;
import org.jeecg.modules.user.userinfo.entity.WxClientUserinfo;
import org.jeecg.modules.utils.json.CommonStringTypeHandler;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 后台"订单管理"查询返回对象
 * @author QiaoQi
 * @version 1.0
 */

@Data
public class OrdersAllDetails {

    /**
     * 订单id（未付款和已付款）
     */
    private String id;

    /**
     * 创建日期
     */
    private Date createTime;

    /**
     * 微信支付订单号
     */
    private String transactionId;

    /**
     * 产品信息
     */
    private Product product;

    /**
     * 出发日期
     */
    private Date dateStarted;

    /**
     * 结束日期
     */
    private Date dateClosed;

    /**
     * 行程套餐信息（orders_fee）
     */
    private JourneyPackage journeypackage;

    /**
     * 导游信息（tourist_guide）
     */
    private TouristGuide touristGuide;

    /**
     * 用户信息（wx_client_userinfo）
     */
    private WxClientUserinfo userinfo;

    /**
     * 联系人姓名
     */
    private String contactName;

    /**
     * 联系人手机号
     */
    private String contactPhone;

    /**
     * 出行人信息（traveler）
     */
    private List<Traveler> travellers;

    /**
     * 成人个数
     */
    private Integer adultCount;

    /**
     * 儿童个数
     */
    private Integer childrenCount;

    /**
     * 实付金额
     */
    private Double paidMoney;

    /**
     * 付款方式
     */
    private String paidMethod;

    /**
     * 优惠卷抵扣金额
     */
    private Double coupon;

    /**
     * 保险信息（orders_fee）
     */
    private List<Insure> insures;

    /**
     * 订单备注
     */
    private String note;

    /**
     * 游侠币抵扣金额
     */
    private Double youxiabi;

    /**
     * 订单状态
     */
    private Integer status;
}
