package org.jeecg.modules.product.tags.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

@Data
public class ProductTag {
    /**id*/
    @Excel(name = "id", width = 15)
    @ApiModelProperty(value = "id")
    private String id;
    /**海报*/
    @Excel(name = "海报", width = 15)
    @ApiModelProperty(value = "海报")
    private String tag;
    /**海报*/
    @Excel(name = "海报", width = 15)
    @ApiModelProperty(value = "海报")
    private String productId;

}
