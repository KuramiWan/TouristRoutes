package org.jeecg.modules.product.entity;


import lombok.Data;

/**
 * @author:一笙
 * @create: 2023-08-05 13:18
 * @Description:
 */

@Data
public class TemporaryUpload {
    String base64Data;
    String productid;
    Integer witch;
}
