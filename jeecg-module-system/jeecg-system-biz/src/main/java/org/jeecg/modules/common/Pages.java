package org.jeecg.modules.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Value;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="pages对象", description="分页请求参数")
public class Pages {
    @ApiModelProperty(value = "页内数量")
    private Integer pageSize;
    @ApiModelProperty(value = "页码")
    private Integer pageNo;
}
