package org.jeecg.modules.comment.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="commentBO对象", description="评论内容")
public class CommentsBO {
    // 评论的内容
    @ApiModelProperty(value = "评论的内容")
    private String content;
    // 评论的图片
    @ApiModelProperty(value = "评论的图片")
    private String image;
    // 评论的日期
    @ApiModelProperty(value = "评论的日期")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date commentTime;
}
