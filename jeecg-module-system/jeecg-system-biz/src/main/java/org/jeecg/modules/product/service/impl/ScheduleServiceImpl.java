package org.jeecg.modules.product.service.impl;

import org.jeecg.modules.product.entity.Schedule;
import org.jeecg.modules.product.entity.Task;
import org.jeecg.modules.product.mapper.ScheduleMapper;
import org.jeecg.modules.product.mapper.TaskMapper;
import org.jeecg.modules.product.service.IScheduleService;
import org.jeecg.modules.product.vo.ScheduleProVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 产品日程
 * @Author: jeecg-boot
 * @Date: 2023-07-14
 * @Version: V1.0
 */
@Service
public class ScheduleServiceImpl extends ServiceImpl<ScheduleMapper, Schedule> implements IScheduleService {

    @Autowired
    private ScheduleMapper scheduleMapper;

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public boolean addScheduleAndTask(ScheduleProVo scheduleProVo) {
        // 添加日程
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleProVo, schedule);
        int s = scheduleMapper.insert(schedule);

        andOrEditTask(scheduleProVo);

        return true;
    }

    @Override
    public boolean editScheduleAndTask(ScheduleProVo scheduleProVo) {
        // 修改日程
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleProVo, schedule);
        int s = scheduleMapper.updateById(schedule);

        andOrEditTask(scheduleProVo);

        return true;
    }

    @Override
    public boolean deleteScheduleAndTask(ScheduleProVo scheduleProVo) {
        // 删除日程
        String id = scheduleProVo.getId();
        scheduleMapper.deleteById(id);

        if (scheduleProVo.getTasks() != null && scheduleProVo.getTasks().size() > 0) {
            // 删除任务
            List<String> taskIds = scheduleProVo.getTasks().stream().map(Task::getId).collect(Collectors.toList());
            System.out.println("taskIds====================" + taskIds);
            if (taskIds.size() > 0) {
                taskMapper.deleteBatchIds(taskIds);
            }
        }

        return true;
    }

    @Override
    public String addScheduleByProId(String proId) {
        Schedule schedule = new Schedule();
        schedule.setProId(proId);
        scheduleMapper.insert(schedule);
        return schedule.getId();
    }

    // 辅助函数，判断任务是编辑还是新增
    public void andOrEditTask(ScheduleProVo scheduleProVo) {
        // 遍历每个任务，如果任务有id，就是修改这个任务，没有就是添加任务
        scheduleProVo.getTasks().forEach(t -> {
            if (("").equals(t.getId()) || t.getId() == null) {
                taskMapper.insert(t);
            } else {
                Task task = new Task();
                BeanUtils.copyProperties(t, task);
                taskMapper.updateById(task);
            }
        });
    }
}
