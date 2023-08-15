package org.jeecg.modules.product.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author 14015
 * @version 1.0
 * @data 2023/8/15 14:37
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class DateDetail2 {
    private String date;
    private String week;
    private String day;
    private String year;
}
