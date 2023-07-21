package org.jeecg.modules.strategy.comment.mapper;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.strategy.comment.entity.OfficialStrategyComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.strategy.comment.vo.OfficialStrategyCommentVo;

/**
 * @Description: 官方攻略评论表
 * @Author: jeecg-boot
 * @Date:   2023-07-21
 * @Version: V1.0
 */
public interface OfficialStrategyCommentMapper extends BaseMapper<OfficialStrategyComment> {
}
