package org.jeecg.modules.footprint.vo;

import lombok.Data;
import org.jeecg.modules.footprint.entity.Footprint;

/**
 * @author 14015
 * @version 1.0
 * @data 2023/8/6 14:36
 */

@Data
public class FootPrintVo {
    private String monthDay;
    private String hourSec;
    private Footprint footprint;
    private String name;
    private String avatar;
}
