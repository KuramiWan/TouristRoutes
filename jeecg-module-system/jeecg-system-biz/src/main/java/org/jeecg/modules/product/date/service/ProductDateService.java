package org.jeecg.modules.product.date.service;

import java.util.List;

public interface ProductDateService {
    public List<String> getDateByProductId(String id);

    public boolean addProductDate(List<String> dates, String id);

    public boolean editDatesByProductId(List<String> tags, String id);

    public boolean deleteDatesByProductId(String id);
}
