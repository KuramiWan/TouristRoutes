package org.jeecg.modules.strategy.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lowagie.text.PageSize;
import org.jeecg.modules.strategy.entity.FriendStrategy;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.strategy.vo.FriendStrategyVo;

/**
 * @Description: 游友攻略
 * @Author: jeecg-boot
 * @Date:   2023-07-22
 * @Version: V1.0
 */
public interface IFriendStrategyService extends IService<FriendStrategy> {
    Page<FriendStrategyVo> queryFriendStrategyInfo(String id, Integer pageNo, Integer PageSize);
}
