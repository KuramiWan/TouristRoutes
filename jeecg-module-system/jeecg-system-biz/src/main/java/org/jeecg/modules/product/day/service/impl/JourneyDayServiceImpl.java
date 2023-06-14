package org.jeecg.modules.product.day.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.jeecg.modules.product.day.bo.JourneyDayBo;
import org.jeecg.modules.product.day.entity.JourneyDay;
import org.jeecg.modules.product.day.mapper.JourneyDayMapper;
import org.jeecg.modules.product.day.service.IJourneyDayService;
import org.jeecg.modules.product.task.entity.JourneyTask;
import org.jeecg.modules.product.task.service.IJourneyTaskService;
import org.jeecg.modules.product.task.service.impl.JourneyTaskServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

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
}
