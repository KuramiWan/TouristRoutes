package org.jeecg.modules.productService.service.impl;

import org.jeecg.modules.productService.entity.ProductService;
import org.jeecg.modules.productService.mapper.ProductServiceMapper;
import org.jeecg.modules.productService.service.IProductServiceService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 客服产品表
 * @Author: jeecg-boot
 * @Date:   2023-08-16
 * @Version: V1.0
 */
@Service
public class ProductServiceServiceImpl extends ServiceImpl<ProductServiceMapper, ProductService> implements IProductServiceService {

}
