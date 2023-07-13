package org.jeecg.modules.user.traveler.relation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.user.traveler.relation.dao.TravelerOrderDao;
import org.jeecg.modules.user.traveler.relation.entity.TravelerOrder;
import org.jeecg.modules.user.traveler.relation.service.TravelerOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 出行人订单联系id(TravelerOrder)表服务实现类
 *
 * @author makejava
 * @since 2023-07-13 16:12:06
 */
@Service("travelerOrderService")
public class TravelerOrderServiceImpl extends ServiceImpl<TravelerOrderDao, TravelerOrder> implements TravelerOrderService {
    @Autowired
    private TravelerOrderDao travelerOrderDao;

    public List<TravelerOrder> getList(TravelerOrder travelerOrder){
        if (travelerOrder == null){
            return new ArrayList<TravelerOrder>();
        }
        String travelerId = travelerOrder.getTravelerId();
        String orderId = travelerOrder.getOrderId();
        LambdaQueryWrapper<TravelerOrder> travelerOrderLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (travelerId != null){
            travelerOrderLambdaQueryWrapper.eq(TravelerOrder::getTravelerId,travelerId);
        }
        if (orderId != null){
            travelerOrderLambdaQueryWrapper.eq(TravelerOrder::getOrderId,orderId);
        }
        return travelerOrderDao.selectList(travelerOrderLambdaQueryWrapper);
    }
    public int edit(TravelerOrder travelerOrder){
        return travelerOrderDao.updateById(travelerOrder);
    }

    public int delete(TravelerOrder travelerOrder){
        return travelerOrderDao.deleteById(travelerOrder);
    }

    public int add(TravelerOrder travelerOrder){
        return travelerOrderDao.insert(travelerOrder);
    }

}

