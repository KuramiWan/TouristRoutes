package org.jeecg.modules.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.product.entity.*;
import org.jeecg.modules.product.mapper.*;
import org.jeecg.modules.product.service.IProductService;
import org.jeecg.modules.product.vo.ProductVo;
import org.jeecg.modules.product.vo.ScheduleVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @Description: 产品表
 * @Author: jeecg-boot
 * @Date:   2023-07-14
 * @Version: V1.0
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private PriceDateMapper priceDateMapper;
    @Autowired
    private ScheduleMapper scheduleMapper;
    @Autowired
    private JourneyPackageMapper journeyPackageMapper;
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private BatchPackageMapper batchPackageMapper;

    @Override
    @Transactional
    public ProductVo queryById(String id) {
        Product product = productMapper.selectById(id);
        ProductVo target = new ProductVo();
        BeanUtils.copyProperties(product, target);
        LambdaQueryWrapper<Schedule> scheduleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        scheduleLambdaQueryWrapper.eq(Schedule::getProId, id);
        List<Schedule> schedules = scheduleMapper.selectList(scheduleLambdaQueryWrapper);
        List<ScheduleVo> ScheduleVos = schedules.stream().map(schedule -> {
            LambdaQueryWrapper<Task> taskLambdaQueryWrapper = new LambdaQueryWrapper<>();
            taskLambdaQueryWrapper.eq(Task::getSchId, schedule.getId());
            ScheduleVo scheduleVo = new ScheduleVo();
            BeanUtils.copyProperties(schedule, scheduleVo);
            List<Task> tasks = taskMapper.selectList(taskLambdaQueryWrapper);
            scheduleVo.setTasks(tasks);
            return scheduleVo;
        }).collect(Collectors.toList());
        target.setSchedules(schedules);

        LambdaQueryWrapper<PriceDate> priceDateLambdaQueryWrapper = new LambdaQueryWrapper<>();
        priceDateLambdaQueryWrapper.eq(PriceDate::getProId, id);
        List<PriceDate> priceDates = priceDateMapper.selectList(priceDateLambdaQueryWrapper);
        target.setPrice_date(priceDates);

        LambdaQueryWrapper<BatchPackage> batchPackageLambdaQueryWrapper = new LambdaQueryWrapper<>();
        batchPackageLambdaQueryWrapper.eq(BatchPackage::getProId, id);
        List<BatchPackage> batchPackages = batchPackageMapper.selectList(batchPackageLambdaQueryWrapper);
        target.setBatch_package(batchPackages);

        LambdaQueryWrapper<JourneyPackage> journeyPackageLambdaQueryWrapper = new LambdaQueryWrapper<>();
        journeyPackageLambdaQueryWrapper.eq(JourneyPackage::getProId, id);
        List<JourneyPackage> journeyPackages = journeyPackageMapper.selectList(journeyPackageLambdaQueryWrapper);
        target.setJourney(journeyPackages);

        return target;
    }

}
