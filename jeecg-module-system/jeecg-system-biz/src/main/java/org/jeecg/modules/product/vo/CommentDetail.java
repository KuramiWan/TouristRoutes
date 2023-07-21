package org.jeecg.modules.product.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @author 14015
 * @version 1.0
 * @data 2023/7/21 19:17
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class CommentDetail {
    private String id;
    private String username;
    private String avatar;
    private String comments;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private java.util.Date Date;
    private List<String> comImg;
}
