package org.jeecg.modules.comment.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jeecg.modules.comment.entity.Comment;
import org.jeecg.modules.common.Pages;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="productCommentsDTO对象", description="请求参数")
public class ProductCommentsDTO extends Pages {
    @ApiModelProperty(value = "产品id")
    private String productId;
    @ApiModelProperty(value = "查询条件")
    private Comment comment;
}
