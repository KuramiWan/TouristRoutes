package org.jeecg.modules.collect.vo.OtherStrategys;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jeecg.modules.strategy.comment.vo.OfficialStrategyCommentVo;
import org.jeecg.modules.strategy.entity.OfficialStrategy;

/**
 * @author 14015
 * @version 1.0
 * @data 2023/8/4 22:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class OffStrategyVo {
    private String author;
    private OfficialStrategy officialStrategy;
    private IPage<OfficialStrategyCommentVo> officialStrategyCommentVo;
}
