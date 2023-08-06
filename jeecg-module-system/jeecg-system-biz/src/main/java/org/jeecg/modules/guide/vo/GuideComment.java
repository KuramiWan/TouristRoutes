package org.jeecg.modules.guide.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class GuideComment {
    private String comments;
    private String avatar;
    private String username;
}
