package org.jeecg.modules.product.tags.service;

import java.util.List;

public interface TagService {
    public List<String> getTagsByProductId(String id);

    public boolean addTags(List<String> tags, String id);

    public boolean editTagsByProductId(List<String> tags, String id);

    public boolean deleteTagsByProductId(String id);
}
