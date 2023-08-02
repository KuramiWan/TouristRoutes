package org.jeecg.modules.product.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScheduleProVo {
    private String sch_id;
    private String sch_date;
    private String sch_title;
    private String sch_imgs;
    private String sch_content;
    private String breakfast;
    private String lunch;
    private String dinner;
    private String hotel;
    private String sch_address;
}
