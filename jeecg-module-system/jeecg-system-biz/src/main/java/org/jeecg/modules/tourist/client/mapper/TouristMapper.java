package org.jeecg.modules.tourist.client.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.tourist.client.entity.Tourist;

/**
 * @Description: 游客信息表
 * @Author: jeecg-boot
 * @Date:   2023-06-18
 * @Version: V1.0
 */
@Mapper
public interface TouristMapper extends BaseMapper<Tourist> {

}
