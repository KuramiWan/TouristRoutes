package org.jeecg.modules.servicecomments.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class CommentUser {
    private String id;
    private String username;
    private String avatar;
    private String comments;
}
