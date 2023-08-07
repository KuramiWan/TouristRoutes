package org.jeecg.modules.strategy.vo;

import lombok.Data;

import java.util.List;

@Data
public class Guide {
    private String id;
    private String name;
    private List<String> greatSpots;
    private String avatar;
    private String honor;
}
