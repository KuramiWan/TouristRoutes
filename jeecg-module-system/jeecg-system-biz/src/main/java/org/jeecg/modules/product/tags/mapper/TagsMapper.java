package org.jeecg.modules.product.tags.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.jeecg.modules.product.tags.pojo.ProductTag;

@Mapper
public interface TagsMapper extends BaseMapper<ProductTag> {
}
