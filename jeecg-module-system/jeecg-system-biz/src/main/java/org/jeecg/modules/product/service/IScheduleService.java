package org.jeecg.modules.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.product.entity.Schedule;
import org.jeecg.modules.product.vo.ScheduleProVo;

/**
 * @Description: 产品日程
 * @Author: jeecg-boot
 * @Date:   2023-07-14
 * @Version: V1.0
 */
public interface IScheduleService extends IService<Schedule> {

    public boolean addScheduleAndTask(ScheduleProVo scheduleProVo);

    public boolean editScheduleAndTask(ScheduleProVo scheduleProVo);

    public boolean deleteScheduleAndTask(ScheduleProVo scheduleProVo);

    public String addScheduleByProId(String proId);
}
