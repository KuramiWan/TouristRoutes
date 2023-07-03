package org.jeecg.modules.product.date.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.jeecg.modules.product.date.mapper.ProductDateMapper;
import org.jeecg.modules.product.date.pojo.ProductDate;
import org.jeecg.modules.product.date.service.ProductDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductDateServiceImpl implements ProductDateService {
    @Autowired
    private ProductDateMapper dateMapper;
    @Override
    public List<String> getDateByProductId(String id) {
        LambdaQueryWrapper<ProductDate> JourneyDateLambdaQueryWrapper = new LambdaQueryWrapper<>();
        JourneyDateLambdaQueryWrapper.eq(ProductDate::getProductId,id);
        List<ProductDate> tagList = dateMapper.selectList(JourneyDateLambdaQueryWrapper);
        return tagList.stream().map(ProductDate::getDate).collect(Collectors.toList());
    }

    @Override
    public boolean addProductDate(List<String> dates,String id) {
        ArrayList<org.jeecg.modules.product.date.pojo.ProductDate> ProductDateArrayList = new ArrayList<>();
        boolean insert = true;
        for (String date : dates) {
            ProductDate productDate = new ProductDate();
            productDate.setId(id);
            productDate.setDate(date);
            insert = dateMapper.insert(productDate) == 1 && insert;
        }
        return insert;
    }

    @Override
    @Transactional
    public boolean editDatesByProductId(List<String> dates, String id) {
        boolean b = deleteDatesByProductId(id);
        if (b){
            b = addProductDate(dates, id);
        }
        return b;
    }

    @Override
    public boolean deleteDatesByProductId(String id) {
        LambdaQueryWrapper<ProductDate> productDateLambdaQueryWrapper = new LambdaQueryWrapper<>();
        productDateLambdaQueryWrapper.eq(ProductDate::getProductId,id);
        int delete = dateMapper.delete(productDateLambdaQueryWrapper);
        return delete == 1;
    }
}
