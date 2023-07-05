package org.jeecg.modules.comment.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.models.auth.In;
import org.jeecg.modules.comment.bo.CommentsBO;
import org.jeecg.modules.comment.bo.ProductCommentsBO;
import org.jeecg.modules.comment.dto.ProductCommentsDTO;
import org.jeecg.modules.comment.entity.Comment;
import org.jeecg.modules.comment.mapper.CommentMapper;
import org.jeecg.modules.comment.service.ICommentService;
import org.jeecg.modules.comment.vo.ProductCommentsVO;
import org.jeecg.modules.orders.mapper.OrdersMapper;
import org.jeecg.modules.tourist.client.entity.Tourist;
import org.jeecg.modules.tourist.client.mapper.TouristMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 产品评论表
 * @Author: jeecg-boot
 * @Date:   2023-07-01
 * @Version: V1.0
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private TouristMapper touristMapper;
    @Override
    public ProductCommentsVO getProductComments(ProductCommentsDTO productCommentsDTO, Wrapper<Comment> queryWrapper) {
        // 产品总评论总数，并且根据评论日期进行降序
        LambdaQueryWrapper<Comment> commentQueryWrapper = new LambdaQueryWrapper<Comment>()
                .eq(Comment::getProductId,productCommentsDTO.getProductId())
                .orderByDesc(Comment::getCommentTime);
        List<Comment> comments = commentMapper.selectList(commentQueryWrapper);
        Integer total = comments.size();
        // 删选评论（预留）

        // 分页评论，组装CommentsBO对象
        long currentPage = productCommentsDTO.getPageNo().longValue();
        Integer pageSize = productCommentsDTO.getPageSize();
        List<Comment> commentPageList = comments.stream()
                .skip((currentPage - 1) * pageSize)
                .limit(pageSize).collect(Collectors.toList());

        List<ProductCommentsBO> productCommentsBOList = new ArrayList<>();
        commentPageList.forEach(
                comment->{
                    CommentsBO commentsBO = new CommentsBO();
                    BeanUtils.copyProperties(comment,commentsBO);
                    ProductCommentsBO productCommentsBO = new ProductCommentsBO();
                    LambdaQueryWrapper<Tourist> touristQueryWrapper = new LambdaQueryWrapper<Tourist>()
                            .eq(Tourist::getId,comment.getUserId());
                    Tourist tourist = touristMapper.selectOne(touristQueryWrapper);
                    // 组装ProductCommentsBO对象
                    BeanUtils.copyProperties(tourist,productCommentsBO);
                    productCommentsBO.setCommentsBO(commentsBO);
                    // 装入大集合
                    productCommentsBOList.add(productCommentsBO);
                });
        // 返回组装好的ProductCommentsVO对象
        return new ProductCommentsVO(total, productCommentsBOList);
    }
}
