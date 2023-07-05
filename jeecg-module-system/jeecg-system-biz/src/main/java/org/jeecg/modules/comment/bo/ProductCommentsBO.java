package org.jeecg.modules.comment.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="productCommentsBO对象", description="游客信息内容")
public class ProductCommentsBO {
    // 游客昵称
    @ApiModelProperty(value = "游客昵称")
    private String touristNickname;
    // 游客头像
    @ApiModelProperty(value = "游客头像")
    private String touristAvatar;
    // 评论内容对象
    @ApiModelProperty(value = "评论内容对象")
    private CommentsBO commentsBO;
}
