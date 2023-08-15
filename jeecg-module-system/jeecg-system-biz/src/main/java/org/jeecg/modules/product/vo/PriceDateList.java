package org.jeecg.modules.product.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author 14015
 * @version 1.0
 * @data 2023/7/22 14:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class PriceDateList {
    /**
     * 对应产品id
     */
    private String proId;
    /**
     * 产品日期id
     */
    private String dateId;
    /**
     * 产品日期
     */
    private DateDetail dateDetail;
    /**
     * 当天日期对应的价格
     */
    private Double pdPrice;
    /**
     * 当天报名人数
     */
    private Integer pdEnrollment;
    /**
     * 当天是否人满
     */
    private String pdFull;
    /**
     * 当天最多可报名人数
     */
    private Integer pdMaxMan;

    private DateDetail2 dateDetail2;
}
