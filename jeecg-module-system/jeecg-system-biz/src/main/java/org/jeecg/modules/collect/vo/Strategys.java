package org.jeecg.modules.collect.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.collect.vo.OtherStrategys.OffStrategyVo;
import org.jeecg.modules.strategy.vo.FriendStrategyVo;

import java.util.List;

/**
 * @author 14015
 * @version 1.0
 * @data 2023/8/4 20:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Strategys {
    private List<IPage<FriendStrategyVo>> friendStrategies;

    private List<OffStrategyVo> officialStrategies;

    private Integer number;
}
