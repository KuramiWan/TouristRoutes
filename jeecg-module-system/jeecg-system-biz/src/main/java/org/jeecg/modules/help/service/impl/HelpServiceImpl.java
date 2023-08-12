package org.jeecg.modules.help.service.impl;

import org.jeecg.modules.help.entity.Help;
import org.jeecg.modules.help.mapper.HelpMapper;
import org.jeecg.modules.help.service.IHelpService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 帮助中心表
 * @Author: jeecg-boot
 * @Date:   2023-08-12
 * @Version: V1.0
 */
@Service
public class HelpServiceImpl extends ServiceImpl<HelpMapper, Help> implements IHelpService {

}
