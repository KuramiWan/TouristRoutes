package org.jeecg.modules.strategy.vo;

import lombok.Data;

import java.util.List;

@Data
public class PostStrategy {
    private String title;
    private String content;
    private List<String> img;
    private String position;
    private String userid;
}
