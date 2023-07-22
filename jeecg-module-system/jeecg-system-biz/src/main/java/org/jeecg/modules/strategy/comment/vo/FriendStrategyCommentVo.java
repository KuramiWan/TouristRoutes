package org.jeecg.modules.strategy.comment.vo;

import lombok.Data;
import org.jeecg.modules.strategy.comment.entity.FriendStrategyComment;

/**
 * @author QiaoQi
 * @version 1.0
 */
@Data
public class FriendStrategyCommentVo extends FriendStrategyComment {

    private String username;

    private String avatar;
}
