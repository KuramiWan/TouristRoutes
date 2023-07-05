package org.jeecg.modules.comment.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jeecg.modules.comment.bo.ProductCommentsBO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="productCommentsVO对象", description="返回的评论视图信息")
public class ProductCommentsVO {
    // 产品总共评论条数
    @ApiModelProperty(value = "产品总共评论条数")
    private Integer total;
    // 产品的评论列表
    @ApiModelProperty(value = "产品的评论列表")
    private List<ProductCommentsBO> productCommentsBOList;

}