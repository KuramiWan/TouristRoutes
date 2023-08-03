package org.jeecg.modules.product.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.product.entity.Task;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleProVo {
    private String id;
    private String schDate;
    private String schTitle;
    private String schImgs;
    private String schContent;
    private String breakfast;
    private String lunch;
    private String dinner;
    private String hotel;
    private String schAddress;
    private List<Task> tasks;
}
