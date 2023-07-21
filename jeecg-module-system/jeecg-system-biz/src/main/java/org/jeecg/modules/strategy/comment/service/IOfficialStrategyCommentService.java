package org.jeecg.modules.strategy.comment.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.strategy.comment.entity.OfficialStrategyComment;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.strategy.comment.vo.OfficialStrategyCommentVo;

/**
 * @Description: 官方攻略评论表
 * @Author: jeecg-boot
 * @Date:   2023-07-21
 * @Version: V1.0
 */
public interface IOfficialStrategyCommentService extends IService<OfficialStrategyComment> {
    Page<OfficialStrategyCommentVo> queryListPage(String officialId, Integer pageNo, Integer pageSize);
}
