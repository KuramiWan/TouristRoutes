package org.jeecg.modules.product.service.impl;

import org.jeecg.modules.product.entity.Tag;
import org.jeecg.modules.product.mapper.TagMapper;
import org.jeecg.modules.product.service.ITagService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 产品标签表
 * @Author: jeecg-boot
 * @Date:   2023-07-14
 * @Version: V1.0
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

}
