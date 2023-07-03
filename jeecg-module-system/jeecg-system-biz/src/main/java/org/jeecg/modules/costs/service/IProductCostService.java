package org.jeecg.modules.costs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.costs.entity.ProductCost;

import java.util.List;

/**
 * @Description: 产品费用说明
 * @Author: jeecg-boot
 * @Date:   2023-07-02
 * @Version: V1.0
 */
public interface IProductCostService extends IService<ProductCost> {
    //根据productId查询
    public ProductCost queryByProductId(String productId);
    //根据productId删除
    public Boolean deleteByProductId(String productId);
}
