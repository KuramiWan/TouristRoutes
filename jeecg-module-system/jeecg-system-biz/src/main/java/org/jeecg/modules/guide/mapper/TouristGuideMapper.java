package org.jeecg.modules.guide.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.guide.entity.TouristGuide;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 导游表
 * @Author: jeecg-boot
 * @Date:   2023-07-13
 * @Version: V1.0
 */
@Mapper
public interface TouristGuideMapper extends BaseMapper<TouristGuide> {

}
