package org.jeecg.modules.orders.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.orders.entity.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.orders.resp.OrdersResp;

/**
 * @Description: 订单表
 * @Author: jeecg-boot
 * @Date:   2023-06-19
 * @Version: V1.0
 */
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
}
