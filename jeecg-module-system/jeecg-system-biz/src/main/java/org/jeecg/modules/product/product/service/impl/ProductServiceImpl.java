package org.jeecg.modules.product.product.service.impl;

import org.jeecg.modules.product.product.entity.Product;
import org.jeecg.modules.product.product.mapper.ProductMapper;
import org.jeecg.modules.product.product.service.IProductService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 旅游产品表
 * @Author: jeecg-boot
 * @Date:   2023-06-14
 * @Version: V1.0
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {
}
