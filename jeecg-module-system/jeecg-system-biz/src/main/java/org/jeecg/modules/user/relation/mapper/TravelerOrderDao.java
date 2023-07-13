package org.jeecg.modules.user.relation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.user.relation.entity.TravelerOrder;

/**
 * 出行人订单联系id(TravelerOrder)表数据库访问层
 *
 * @author makejava
 * @since 2023-07-13 16:12:06
 */

@Mapper
public interface TravelerOrderDao extends BaseMapper<TravelerOrder> {

}

