package org.jeecg.modules.product.service.impl;

import org.jeecg.modules.product.entity.Comment;
import org.jeecg.modules.product.mapper.CommentMapper;
import org.jeecg.modules.product.service.ICommentService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 产品评论
 * @Author: jeecg-boot
 * @Date:   2023-07-14
 * @Version: V1.0
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

}
