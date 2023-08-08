package org.jeecg.modules.strategy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.follow.entity.Follow;
import org.jeecg.modules.follow.mapper.FollowMapper;
import org.jeecg.modules.strategy.comment.entity.FriendStrategyComment;
import org.jeecg.modules.strategy.comment.mapper.FriendStrategyCommentMapper;
import org.jeecg.modules.strategy.comment.vo.FriendStrategyCommentVo;
import org.jeecg.modules.strategy.entity.FriendStrategy;
import org.jeecg.modules.strategy.mapper.FriendStrategyMapper;
import org.jeecg.modules.strategy.service.IFriendStrategyService;
import org.jeecg.modules.strategy.vo.FriendStrategyVo;
import org.jeecg.modules.user.userinfo.entity.WxClientUserinfo;
import org.jeecg.modules.user.userinfo.mapper.WxClientUserinfoMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Description: 游友攻略
 * @Author: jeecg-boot
 * @Date: 2023-07-22
 * @Version: V1.0
 */
@Service
public class FriendStrategyServiceImpl extends ServiceImpl<FriendStrategyMapper, FriendStrategy> implements IFriendStrategyService {

    @Autowired
    private WxClientUserinfoMapper wxClientUserinfoMapper;

    @Autowired
    private FriendStrategyMapper friendStrategyMapper;

    @Autowired
    private FriendStrategyCommentMapper friendStrategyCommentMapper;

    @Autowired
    private FollowMapper followMapper;

    @Override
    public Page<FriendStrategyVo> queryFriendStrategyInfo(String myUserid, String id, Integer pageNo, Integer PageSize) {
        Page<FriendStrategy> page = new Page<>(pageNo, PageSize);
        LambdaQueryWrapper<FriendStrategy> wrapper = new LambdaQueryWrapper<FriendStrategy>().like(FriendStrategy::getId, id);
        Page<FriendStrategy> strategyPage = friendStrategyMapper.selectPage(page, wrapper);
        List<FriendStrategy> list = strategyPage.getRecords();
        System.out.println("list:" + list);
        ArrayList<FriendStrategyVo> friendStrategyVos = new ArrayList<>();

        list.forEach(i -> {
            FriendStrategyVo friendStrategyVo = new FriendStrategyVo();
            String friendId = i.getId();
            String wxuserid = i.getUserid();

            // 查询这个人和游友攻略的人是否存在关注关系
            Follow follow = followMapper.selectOne(new LambdaQueryWrapper<Follow>().eq(Follow::getFollowId, wxuserid).eq(Follow::getUserId, myUserid));
            if (follow == null) {
                friendStrategyVo.setIsFollow("关注");
            } else {
                friendStrategyVo.setIsFollow("取消关注");
            }

            WxClientUserinfo wxClientUserinfo = wxClientUserinfoMapper.selectOne(new LambdaQueryWrapper<WxClientUserinfo>().eq(WxClientUserinfo::getId, wxuserid));
            LambdaQueryWrapper<FriendStrategyComment> commentVoLambdaQueryWrapper = new LambdaQueryWrapper<FriendStrategyComment>().eq(FriendStrategyComment::getFriendId, friendId);
            Page<FriendStrategyComment> friendStrategyCommentPage = friendStrategyCommentMapper.selectPage(new Page<>(1, 10), commentVoLambdaQueryWrapper);
            List<FriendStrategyComment> friendStrategyCommentPageRecords = friendStrategyCommentPage.getRecords();
            ArrayList<FriendStrategyCommentVo> friendStrategyCommentVos = new ArrayList<>();
            Page<FriendStrategyCommentVo> friendStrategyCommentVoPage = new Page<>();

            friendStrategyCommentPageRecords.forEach(e -> {
                FriendStrategyCommentVo friendStrategyCommentVo = new FriendStrategyCommentVo();
                String userid = e.getUserid();
                LambdaQueryWrapper<WxClientUserinfo> userinfoLambdaQueryWrapper = new LambdaQueryWrapper<WxClientUserinfo>().eq(WxClientUserinfo::getId, userid);
                WxClientUserinfo userinfo = wxClientUserinfoMapper.selectOne(userinfoLambdaQueryWrapper);
                BeanUtils.copyProperties(e, friendStrategyCommentVo);
                friendStrategyCommentVo.setUsername(userinfo.getUsername());
                friendStrategyCommentVo.setAvatar(userinfo.getAvatar());
                friendStrategyCommentVos.add(friendStrategyCommentVo);
            });

            BeanUtils.copyProperties(i, friendStrategyVo);
            friendStrategyVo.setFriendStrategyComments(friendStrategyCommentVos);
            friendStrategyVo.setUsername(wxClientUserinfo.getUsername());
            friendStrategyVo.setAvatar(wxClientUserinfo.getAvatar());
            friendStrategyVos.add(friendStrategyVo);
        });

        Page<FriendStrategyVo> strategyVoPage = new Page<>();
        BeanUtils.copyProperties(strategyPage, strategyVoPage);

        // 随机打乱list，模拟相关推荐
        Collections.shuffle(friendStrategyVos);

        strategyVoPage.setRecords(friendStrategyVos);

        return strategyVoPage;
    }
}
