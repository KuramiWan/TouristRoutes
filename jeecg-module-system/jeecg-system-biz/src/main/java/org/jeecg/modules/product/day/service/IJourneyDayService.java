package org.jeecg.modules.product.day.service;

import org.jeecg.modules.product.day.bo.JourneyDayBo;
import org.jeecg.modules.product.day.entity.JourneyDay;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 旅游日程表
 * @Author: jeecg-boot
 * @Date:   2023-06-14
 * @Version: V1.0
 */
public interface IJourneyDayService extends IService<JourneyDay> {
    List<JourneyDayBo> getDayList(String id);
}
