package org.jeecg.modules.couponUser.service.impl;

import org.jeecg.modules.couponUser.entity.CouponUser;
import org.jeecg.modules.couponUser.mapper.CouponUserMapper;
import org.jeecg.modules.couponUser.service.ICouponUserService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 用户拥有优惠券表
 * @Author: jeecg-boot
 * @Date:   2023-08-05
 * @Version: V1.0
 */
@Service
public class CouponUserServiceImpl extends ServiceImpl<CouponUserMapper, CouponUser> implements ICouponUserService {

}
