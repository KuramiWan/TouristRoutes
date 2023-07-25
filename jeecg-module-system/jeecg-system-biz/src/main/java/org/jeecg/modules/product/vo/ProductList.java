package org.jeecg.modules.product.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author 14015
 * @version 1.0
 * @data 2023/7/22 19:36
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class ProductList {
    private String id;
    private String origin;
    private Integer proMan;
    private String proPageTitle;
    private Integer sellNumber;
    private Double proEvaluate;
    private Integer spots;
    private String proPageImg;
}
