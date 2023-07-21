package org.jeecg.modules.user.userinfo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.PasswordUtil;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysUserService;
import org.jeecg.modules.user.userinfo.entity.WxClientUserinfo;
import org.jeecg.modules.user.userinfo.mapper.WxClientUserinfoMapper;
import org.jeecg.modules.user.userinfo.service.IWxClientUserinfoService;
import org.jeecg.modules.user.userinfo.vo.WxClientUserinfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Description: 微信客户端用户信息表
 * @Author: jeecg-boot
 * @Date:   2023-07-17
 * @Version: V1.0
 */
@Service
public class WxClientUserinfoServiceImpl extends ServiceImpl<WxClientUserinfoMapper, WxClientUserinfo> implements IWxClientUserinfoService {
    @Autowired
    private WxClientUserinfoMapper wxClientUserinfoMapper;

    @Autowired
    private ISysUserService sysUserService;

    @Lazy
    @Resource
    private RedisUtil redisUtil;

    @Override
    public WxClientUserinfoVo login(String openId, String username, String avatar, String sessionKey) {
        //根据openid生成一个token发放给用户
        String token = JwtUtil.sign(openId, openId);
        QueryWrapper<WxClientUserinfo> clientUserinfoQueryWrapper = new QueryWrapper<WxClientUserinfo>().eq("openid", openId);
        WxClientUserinfo clientUserinfoServiceOne = wxClientUserinfoMapper.selectOne(clientUserinfoQueryWrapper);
        // 如果是第一次使用随心游,则添加用户
        if (clientUserinfoServiceOne == null) {
            // 先添加到sys_user表中
            SysUser sysUser = new SysUser();
            sysUser.setCreateTime(new Date());//设置创建时间
            String salt = oConvertUtils.randomGen(8);
            sysUser.setSalt(salt);
            String passwordEncode = PasswordUtil.encrypt(openId, openId, salt); // 将用户的openid作为username和password加密
            sysUser.setPassword(passwordEncode);
            sysUser.setStatus(1);
            sysUser.setDelFlag(CommonConstant.DEL_FLAG_0);
            // 用户表字段org_code不能在这里设置他的值
            sysUser.setOrgCode(null);
            sysUser.setUsername(openId);
            sysUserService.save(sysUser);

            // 然后添加到wx_client_user表中
            WxClientUserinfo wxClientUserinfo = new WxClientUserinfo();
            wxClientUserinfo.setOpenid(openId);
            wxClientUserinfo.setAvatar(avatar);
            wxClientUserinfo.setUsername(username);
            wxClientUserinfo.setSessionKey(sessionKey);
            wxClientUserinfoMapper.insert(wxClientUserinfo);

            // 创造vo对象
            WxClientUserinfoVo wxClientUserinfoVo = new WxClientUserinfoVo();
            BeanUtils.copyProperties(wxClientUserinfo,wxClientUserinfoVo);
            wxClientUserinfoVo.setToken(token);

            //redis中添加token
            redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, token);
            return wxClientUserinfoVo;
        } else { // 之前使用过随心游,则更新用户信息
            clientUserinfoServiceOne.setUsername(username);
            clientUserinfoServiceOne.setAvatar(avatar);
            clientUserinfoServiceOne.setSessionKey(sessionKey);
            wxClientUserinfoMapper.updateById(clientUserinfoServiceOne);

            // 创造vo对象
            WxClientUserinfoVo wxClientUserinfoVo = new WxClientUserinfoVo();
            BeanUtils.copyProperties(clientUserinfoServiceOne,wxClientUserinfoVo);
            wxClientUserinfoVo.setToken(token);
            //redis中添加token
            redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, token);

            return wxClientUserinfoVo;
        }
    }
}
