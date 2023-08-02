package org.jeecg.modules.product.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.product.entity.Task;

import java.util.List;

/**
 * @author QiaoQi
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleVo {
    private List<Task> tasks;
}
