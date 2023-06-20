package org.jeecg.modules.product.task.service;

import org.jeecg.modules.product.task.entity.JourneyTask;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.persistence.Id;
import java.util.List;

/**
 * @Description: 旅行任务表
 * @Author: jeecg-boot
 * @Date:   2023-06-14
 * @Version: V1.0
 */
public interface IJourneyTaskService extends IService<JourneyTask> {

    List<JourneyTask> getTaskLists(String id);

    boolean saveTask(List<JourneyTask> tasks);

    boolean editTask(List<JourneyTask> tasks);

    List<JourneyTask> getTaskByJourneyId(String id);
}
