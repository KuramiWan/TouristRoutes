package org.jeecg.modules.comment.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.comment.dto.ProductCommentsDTO;
import org.jeecg.modules.comment.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.comment.vo.ProductCommentsVO;

/**
 * @Description: 产品评论表
 * @Author: jeecg-boot
 * @Date:   2023-07-01
 * @Version: V1.0
 */
public interface ICommentService extends IService<Comment> {
    ProductCommentsVO getProductComments(ProductCommentsDTO productCommentsDTO, Wrapper<Comment> queryWrapper);
}
