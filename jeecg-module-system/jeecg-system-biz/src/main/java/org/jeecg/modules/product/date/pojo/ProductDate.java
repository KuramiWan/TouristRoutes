package org.jeecg.modules.product.date.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

@Data
public class ProductDate {
    /**id*/
    @Excel(name = "id", width = 15)
    @ApiModelProperty(value = "id")
    private String id;
    /**海报*/
    @Excel(name = "日期", width = 15)
    @ApiModelProperty(value = "日期")
    private String date;
    /**海报*/
    @Excel(name = "产品id", width = 15)
    @ApiModelProperty(value = "产品id")
    private String productId;

}
