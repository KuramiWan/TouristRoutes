package org.jeecg.modules.product.day.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.jeecg.modules.product.day.bo.JourneyDayBo;
import org.jeecg.modules.product.day.entity.JourneyDay;
import org.jeecg.modules.product.day.mapper.JourneyDayMapper;
import org.jeecg.modules.product.day.service.IJourneyDayService;
import org.jeecg.modules.product.task.controller.JourneyTaskController;
import org.jeecg.modules.product.task.entity.JourneyTask;
import org.jeecg.modules.product.task.service.IJourneyTaskService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 旅游日程表
 * @Author: jeecg-boot
 * @Date:   2023-06-14
 * @Version: V1.0
 */
@Service
public class JourneyDayServiceImpl extends ServiceImpl<JourneyDayMapper, JourneyDay> implements IJourneyDayService {
    @Autowired
    private JourneyDayMapper journeyDayMapper;
    @Autowired
    private IJourneyTaskService journeyTaskService;
    @Autowired
    private JourneyTaskController journeyTaskController;

    @Override
    public List<JourneyDayBo> getDayList(String id) {
        LambdaQueryWrapper<JourneyDay> journeyDayLambdaQueryWrapper = new LambdaQueryWrapper<>();
        journeyDayLambdaQueryWrapper.eq(JourneyDay::getProductId,id);
        List<JourneyDay> journeyDays = journeyDayMapper.selectList(journeyDayLambdaQueryWrapper);
        ArrayList<JourneyDayBo> journeyDayBos = new ArrayList<>();
        for (JourneyDay journeyDay : journeyDays) {
            JourneyDayBo journeyDayBo = new JourneyDayBo();
            BeanUtils.copyProperties(journeyDay,journeyDayBo);
            String journeyId = journeyDay.getId();
            List<JourneyTask> journeyTasks = journeyTaskService.getTaskLists(journeyId);
            journeyDayBo.setTasks(journeyTasks);
            journeyDayBos.add(journeyDayBo);
        }
        return journeyDayBos;
    }

    @Override
    public boolean saveDay(List<JourneyDayBo> journeyDaysBo) {
        List<JourneyDay> journeyDays = journeyDaysBo.stream().map(journeyDayBo -> {
                    JourneyDay journeyDay = new JourneyDay();
                    BeanUtils.copyProperties(journeyDayBo, journeyDay);
                    return journeyDay;
                }
        ).collect(Collectors.toList());
        boolean b = true;
        for (JourneyDay journeyDay : journeyDays) {
            int insert = journeyDayMapper.insert(journeyDay);
            b = insert == 1 && b;
        }
        List<JourneyTask> tasks = new ArrayList<>();


        for (JourneyDay journeyDay : journeyDays) {
            journeyDaysBo.forEach(journeyDayBo -> {
                journeyDayBo.setId(journeyDay.getId());
            });
        }

        for (JourneyDayBo journeyDayBo : journeyDaysBo) {
            tasks = journeyDayBo.getTasks().stream()
                    .peek(journeyTask -> journeyTask.setJourneyDayId(journeyDayBo.getId()))
                    .collect(Collectors.toList());
            b = journeyTaskService.saveTask(tasks);
        }
        return b;
    }

    @Override
    public boolean edit(List<JourneyDayBo> journeyDaysBo) {
        boolean isSuccess = true;
        for (JourneyDayBo journeyDayBo : journeyDaysBo) {
            JourneyDay journeyDay = new JourneyDay();
            BeanUtils.copyProperties(journeyDayBo,journeyDay);
            int i = journeyDayMapper.updateById(journeyDay);
            isSuccess = i == 1 && isSuccess;
        }
        if (isSuccess){
            boolean isTaskSuc = true;
            for (JourneyDayBo journeyDayBo : journeyDaysBo) {
                if (!journeyDayBo.getTasks().isEmpty()){
                    isTaskSuc = isTaskSuc && journeyTaskService.editTask(journeyDayBo.getTasks());
                }
            }
            isSuccess = isTaskSuc;
        }
        return isSuccess;
    }

    @Override
    public List<JourneyDayBo> getByProductId(String id) {
        LambdaQueryWrapper<JourneyDay> journeyDayLambdaQueryWrapper = new LambdaQueryWrapper<>();
        journeyDayLambdaQueryWrapper.eq(JourneyDay::getProductId, id);
        List<JourneyDay> journeyDays = journeyDayMapper.selectList(journeyDayLambdaQueryWrapper);
        List<JourneyDayBo> journeyDayBos = new ArrayList<>();
        for (JourneyDay journeyDay : journeyDays) {
            List<JourneyTask> journeyTasks = journeyTaskService.getTaskByJourneyId(journeyDay.getId());
            JourneyDayBo journeyDayBo = new JourneyDayBo();
            BeanUtils.copyProperties(journeyDay,journeyDayBo);
            journeyDayBo.setTasks(journeyTasks);
            journeyDayBos.add(journeyDayBo);
        }
        return journeyDayBos;


    }
}
