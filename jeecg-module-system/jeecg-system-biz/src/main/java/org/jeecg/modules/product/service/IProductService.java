package org.jeecg.modules.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.product.entity.Product;
import org.jeecg.modules.product.vo.ProductVo;

import java.io.Serializable;

/**
 * @Description: 产品表
 * @Author: jeecg-boot
 * @Date:   2023-07-14
 * @Version: V1.0
 */
public interface IProductService extends IService<Product> {
    ProductVo queryById(String id);
}
