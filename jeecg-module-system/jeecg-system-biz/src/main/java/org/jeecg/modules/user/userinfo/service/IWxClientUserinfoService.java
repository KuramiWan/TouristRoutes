package org.jeecg.modules.user.userinfo.service;

import org.jeecg.modules.user.userinfo.entity.WxClientUserinfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.user.userinfo.vo.WxClientUserinfoVo;

/**
 * @Description: 微信客户端用户信息表
 * @Author: jeecg-boot
 * @Date:   2023-07-17
 * @Version: V1.0
 */
public interface IWxClientUserinfoService extends IService<WxClientUserinfo> {
    WxClientUserinfoVo login(String openid, String username, String sessionKey);
}
