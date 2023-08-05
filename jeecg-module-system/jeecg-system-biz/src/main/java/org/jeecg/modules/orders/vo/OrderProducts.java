package org.jeecg.modules.orders.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.product.entity.Product;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author 14015
 * @version 1.0
 * @data 2023/8/5 13:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProducts {
    private String orderId;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateStarted;
    private Product product;
}
