package org.jeecg.modules.guide.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class GuideProduct {
    private String proPageImg;
    private String proPageTitle;
    private String id;
    private Double proEvaluate;
}
