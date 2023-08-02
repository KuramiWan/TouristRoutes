package org.jeecg.modules.follow.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.user.userinfo.entity.WxClientUserinfo;

import java.util.List;

/**
 * @author 14015
 * @version 1.0
 * @data 2023/8/2 15:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowList {
    private Integer count;
    private List<WxClientUserinfo> FollowList;
}
