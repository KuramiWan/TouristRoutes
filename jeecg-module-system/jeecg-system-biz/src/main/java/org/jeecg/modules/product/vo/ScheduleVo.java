package org.jeecg.modules.product.vo;

import lombok.Data;
import org.jeecg.modules.product.entity.Task;

import java.util.List;

/**
 * @author QiaoQi
 * @version 1.0
 */
@Data
public class ScheduleVo {
    private List<Task> tasks;
}
