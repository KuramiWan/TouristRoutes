package org.jeecg.modules.product.tags.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.jeecg.modules.product.tags.mapper.TagsMapper;
import org.jeecg.modules.product.tags.pojo.ProductTag;
import org.jeecg.modules.product.tags.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagsMapper tagsMapper;
    @Override
    public List<String> getTagsByProductId(String id) {
        LambdaQueryWrapper<ProductTag> tagLambdaQueryWrapper = new LambdaQueryWrapper<>();
        tagLambdaQueryWrapper.eq(ProductTag::getProductId,id);
        List<ProductTag> productTagList = tagsMapper.selectList(tagLambdaQueryWrapper);
        return productTagList.stream().map(ProductTag::getTag).collect(Collectors.toList());
    }

    @Override
    public boolean addTags(List<String> tags,String id) {
        ArrayList<ProductTag> productTagArrayList = new ArrayList<>();
        boolean insert = true;
        for (String tag : tags) {
            ProductTag productTag = new ProductTag();
            productTag.setId(id);
            productTag.setTag(tag);
            insert = tagsMapper.insert(productTag) == 1 && insert;
        }
        return insert;
    }

    @Override
    @Transactional
    public boolean editTagsByProductId(List<String> tags, String id) {
        boolean b = deleteTagsByProductId(id);
        if (b){
            b = addTags(tags, id);
        }
        return b;
    }

    @Override
    public boolean deleteTagsByProductId(String id) {
        LambdaQueryWrapper<ProductTag> productTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
        productTagLambdaQueryWrapper.eq(ProductTag::getProductId,id);
        int delete = tagsMapper.delete(productTagLambdaQueryWrapper);
        return delete == 1;
    }
}
