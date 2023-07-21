package org.jeecg.modules.strategy.comment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.strategy.comment.entity.OfficialStrategyComment;
import org.jeecg.modules.strategy.comment.mapper.OfficialStrategyCommentMapper;
import org.jeecg.modules.strategy.comment.service.IOfficialStrategyCommentService;
import org.jeecg.modules.strategy.comment.vo.OfficialStrategyCommentVo;
import org.jeecg.modules.user.userinfo.mapper.WxClientUserinfoMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * @Description: 官方攻略评论表
 * @Author: jeecg-boot
 * @Date: 2023-07-21
 * @Version: V1.0
 */
@Service
public class OfficialStrategyCommentServiceImpl extends ServiceImpl<OfficialStrategyCommentMapper, OfficialStrategyComment> implements IOfficialStrategyCommentService {

    @Autowired
    private OfficialStrategyCommentMapper officialStrategyCommentMapper;

    @Autowired
    private WxClientUserinfoMapper wxClientUserinfoMapper;

    @Override
    public Page<OfficialStrategyCommentVo> queryListPage(String officialId, Integer pageNo, Integer pageSize) {
        Page<OfficialStrategyComment> page = new Page<>(pageNo, pageSize);
        Page<OfficialStrategyComment> strategyCommentPage = officialStrategyCommentMapper.selectPage(page, new LambdaQueryWrapper<OfficialStrategyComment>().eq(OfficialStrategyComment::getOfficialId, officialId));
        ArrayList<OfficialStrategyCommentVo> officialStrategyCommentVos = new ArrayList<>();
        strategyCommentPage.getRecords().forEach(e -> {
            String userid = e.getUserid();
            String username = wxClientUserinfoMapper.selectById(userid).getUsername();
            OfficialStrategyCommentVo officialStrategyCommentVo = new OfficialStrategyCommentVo();
            BeanUtils.copyProperties(e, officialStrategyCommentVo);
            officialStrategyCommentVo.setUsername(username);
            officialStrategyCommentVos.add(officialStrategyCommentVo);
        });
        Page<OfficialStrategyCommentVo> strategyCommentVoPage = new Page<>();
        BeanUtils.copyProperties(strategyCommentPage, strategyCommentVoPage);
        strategyCommentVoPage.setRecords(officialStrategyCommentVos);

        return strategyCommentVoPage;
    }
}
