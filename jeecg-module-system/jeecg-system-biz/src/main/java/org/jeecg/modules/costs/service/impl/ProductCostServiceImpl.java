package org.jeecg.modules.costs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.jeecg.modules.costs.entity.ProductCost;
import org.jeecg.modules.costs.mapper.ProductCostMapper;
import org.jeecg.modules.costs.service.IProductCostService;
import org.jeecg.modules.product.product.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 产品费用说明
 * @Author: jeecg-boot
 * @Date:   2023-07-02
 * @Version: V1.0
 */
@Service
public class ProductCostServiceImpl extends ServiceImpl<ProductCostMapper, ProductCost> implements IProductCostService {
    @Autowired
        private ProductCostMapper productCostMapper;

    @Override
    public ProductCost queryByProductId(String productId) {
        return productCostMapper.selectOne(new LambdaQueryWrapper<ProductCost>().eq(ProductCost::getProductId, productId));
    }

    @Override
    public Boolean deleteByProductId(String productId) {
        ProductCost productCost = productCostMapper.selectOne(new LambdaQueryWrapper<ProductCost>().eq(ProductCost::getProductId, productId));
        if(productCost == null){
            return false;
        }
        productCostMapper.delete(new LambdaQueryWrapper<ProductCost>().eq(ProductCost::getProductId, productId));
        return true;
    }


}
