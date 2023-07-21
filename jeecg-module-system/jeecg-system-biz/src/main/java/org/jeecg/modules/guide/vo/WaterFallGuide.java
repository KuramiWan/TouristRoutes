package org.jeecg.modules.guide.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class WaterFallGuide {
    private String id;
    private String name;
    private List<String> greatSpots;
    private String summary;
    private String pageImg;
    private Integer likeNum;
    private String honor;

}
