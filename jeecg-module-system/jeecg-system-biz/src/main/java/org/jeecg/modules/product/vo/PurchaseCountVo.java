package org.jeecg.modules.product.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class PurchaseCountVo {
    private List<String> avatars;
    private Long count;
}
