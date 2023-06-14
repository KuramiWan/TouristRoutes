package org.jeecg.modules.product.task.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.jeecg.modules.product.day.entity.JourneyDay;
import org.jeecg.modules.product.task.entity.JourneyTask;
import org.jeecg.modules.product.task.mapper.JourneyTaskMapper;
import org.jeecg.modules.product.task.service.IJourneyTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 旅行任务表
 * @Author: jeecg-boot
 * @Date:   2023-06-14
 * @Version: V1.0
 */
@Service
public class JourneyTaskServiceImpl extends ServiceImpl<JourneyTaskMapper, JourneyTask> implements IJourneyTaskService {
    @Autowired
    private JourneyTaskMapper journeyTaskMapper;

    @Override
    public List<JourneyTask> getTaskLists(String id) {
        LambdaQueryWrapper<JourneyTask> journeyDayLambdaQueryWrapper = new LambdaQueryWrapper<>();
        journeyDayLambdaQueryWrapper.eq(JourneyTask::getJourneyDayId,id);
        List<JourneyTask> journeyTasks = journeyTaskMapper.selectList(journeyDayLambdaQueryWrapper);
        return journeyTasks;
    }
}
