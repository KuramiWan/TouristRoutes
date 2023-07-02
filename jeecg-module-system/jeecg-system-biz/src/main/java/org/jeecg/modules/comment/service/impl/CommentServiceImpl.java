package org.jeecg.modules.comment.service.impl;

import org.jeecg.modules.comment.entity.Comment;
import org.jeecg.modules.comment.mapper.CommentMapper;
import org.jeecg.modules.comment.service.ICommentService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 产品评论表
 * @Author: jeecg-boot
 * @Date:   2023-07-01
 * @Version: V1.0
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

}
