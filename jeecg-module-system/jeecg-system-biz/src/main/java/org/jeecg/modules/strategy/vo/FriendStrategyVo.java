package org.jeecg.modules.strategy.vo;

import lombok.Data;
import org.jeecg.modules.strategy.comment.entity.FriendStrategyComment;
import org.jeecg.modules.strategy.comment.vo.FriendStrategyCommentVo;
import org.jeecg.modules.strategy.entity.FriendStrategy;

import java.util.List;

/**
 * @author QiaoQi
 * @version 1.0
 */
@Data
public class FriendStrategyVo extends FriendStrategy {

    private String isFollow;

    private String username;

    private String avatar;

    private List<FriendStrategyCommentVo> friendStrategyComments;

}
