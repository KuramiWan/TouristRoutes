package org.jeecg.modules.datePrice.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author 14015
 * @version 1.0
 * @data 2023/8/3 10:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatePrice {

    private java.lang.String id;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date pdDate;


    private java.lang.Double pdPrice;
}
