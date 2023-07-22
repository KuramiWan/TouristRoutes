package org.jeecg.modules.guide.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class UserAndLeader {
    private String guideId;
    private String userId;
}
