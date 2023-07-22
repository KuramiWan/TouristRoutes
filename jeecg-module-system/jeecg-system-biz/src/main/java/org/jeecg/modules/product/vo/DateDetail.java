package org.jeecg.modules.product.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author 14015
 * @version 1.0
 * @data 2023/7/22 14:30
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class DateDetail {
    private String date;
    private String week;
    private String day;
    private String year;
}
