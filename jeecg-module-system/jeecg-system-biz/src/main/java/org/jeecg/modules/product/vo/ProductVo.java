package org.jeecg.modules.product.vo;

import lombok.Data;
import org.jeecg.modules.product.entity.BatchPackage;
import org.jeecg.modules.product.entity.JourneyPackage;
import org.jeecg.modules.product.entity.PriceDate;
import org.jeecg.modules.product.entity.Schedule;

import java.util.List;

@Data
public class ProductVo {
    private String id;
    private String pro_title;
    private Double pro_evaluate;
    private String pro_introduction;
    private String pro_date;
    private String pro_page_img;
    private String posters;
    private String pro_man;
    private String pro_page_title;
    private String origin;
    private String sold_number;
    private String local;
    private String local_detail;
    private String rec_num;
    private List<Schedule> schedules;
    private List<PriceDate> price_date;
    private List<BatchPackage> batch_package;
    private List<JourneyPackage> journey;
}
