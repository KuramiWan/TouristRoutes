package org.jeecg.modules.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.product.entity.*;
import org.jeecg.modules.product.mapper.*;
import org.jeecg.modules.product.service.IProductService;
import org.jeecg.modules.product.service.IScheduleService;
import org.jeecg.modules.product.vo.ProductVo;
import org.jeecg.modules.product.vo.ScheduleProVo;
import org.jeecg.modules.product.vo.ScheduleVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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

    @Autowired

    private IScheduleService iScheduleService;





    @Override
    @Transactional
    public ProductVo queryById(String id) {
        Product product = productMapper.selectById(id);
        return fillProduct(product);
    }

    @Override
    @Transactional
    public Page<ProductVo> getProductList(Page<Product> page) {
        Page<Product> productList = productMapper.selectPage(page, new LambdaQueryWrapper<Product>());
        List<ProductVo> collect = productList.getRecords().stream().map(this::fillProduct).collect(Collectors.toList());
        Page<ProductVo> productVoPage = new Page<>();
        BeanUtils.copyProperties(productList,productVoPage);
        return productVoPage.setRecords(collect);
    }
    @Transactional
    public ProductVo fillProduct(Product product){
        ProductVo target = new ProductVo();
        BeanUtils.copyProperties(product, target);
        LambdaQueryWrapper<Schedule> scheduleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        scheduleLambdaQueryWrapper.eq(Schedule::getProId, product.getId());
        List<Schedule> schedules = scheduleMapper.selectList(scheduleLambdaQueryWrapper);
        List<ScheduleProVo> ScheduleVos = schedules.stream().map(schedule -> {
            LambdaQueryWrapper<Task> taskLambdaQueryWrapper = new LambdaQueryWrapper<>();
            taskLambdaQueryWrapper.eq(Task::getSchId, schedule.getId());
            ScheduleProVo scheduleVo = new ScheduleProVo();
            BeanUtils.copyProperties(schedule, scheduleVo);
            List<Task> tasks = taskMapper.selectList(taskLambdaQueryWrapper);
            scheduleVo.setTasks(tasks);
            return scheduleVo;
        }).collect(Collectors.toList());
        target.setSchedules(ScheduleVos);

        LambdaQueryWrapper<PriceDate> priceDateLambdaQueryWrapper = new LambdaQueryWrapper<>();
        priceDateLambdaQueryWrapper.eq(PriceDate::getProId, product.getId());
        List<PriceDate> priceDates = priceDateMapper.selectList(priceDateLambdaQueryWrapper);
        target.setPrice_date(priceDates);

        LambdaQueryWrapper<BatchPackage> batchPackageLambdaQueryWrapper = new LambdaQueryWrapper<>();
        batchPackageLambdaQueryWrapper.eq(BatchPackage::getProId, product.getId());
        List<BatchPackage> batchPackages = batchPackageMapper.selectList(batchPackageLambdaQueryWrapper);
        target.setBatch_package(batchPackages);

        LambdaQueryWrapper<JourneyPackage> journeyPackageLambdaQueryWrapper = new LambdaQueryWrapper<>();
        journeyPackageLambdaQueryWrapper.eq(JourneyPackage::getProId, product.getId());
        List<JourneyPackage> journeyPackages = journeyPackageMapper.selectList(journeyPackageLambdaQueryWrapper);
        target.setJourney(journeyPackages);
        return target;
    }

    //删除项目及其相关的日程和任务(弃用)
    @Transactional
    @Override
    public boolean deleteProductAndScheduleAndTask(ProductVo productVo){
        //删除项目
        String id = productVo.getId();
        productMapper.deleteById(id);
        if(productVo.getSchedules() != null && productVo.getSchedules().size() > 0){
            //删除日程及任务
            for (ScheduleProVo scheduleProVo:
                    productVo.getSchedules()
                 ) {
                iScheduleService.deleteScheduleAndTask(scheduleProVo);
            }
        }
        return true;
    }

    //删除项目及其相关的日程和任务
    @Transactional
    @Override
    public boolean deleteProductAndScheduleAndTaskById(String id){
        //删除所选项目
        productMapper.deleteById(id);

        //查到所有的Schedule并删除
        QueryWrapper<Schedule> queryWrapperSchedule = new QueryWrapper<>();
        queryWrapperSchedule.eq("pro_id",id);
        queryWrapperSchedule.select("id");
        List<Schedule> scheduleList = scheduleMapper.selectList(queryWrapperSchedule);
        scheduleMapper.deleteBatchIds(scheduleList);

        //查到所有的Schedule并删除
        QueryWrapper<Task> queryWrapperTask = new QueryWrapper<>();
        queryWrapperTask.eq("pro_id",id);
        queryWrapperTask.select("id");
        List<Task> taskList = taskMapper.selectList(queryWrapperTask);
        taskMapper.deleteBatchIds(taskList);

        //查到所有的PriceDate并删除
        QueryWrapper<PriceDate> queryWrapperPriceDate = new QueryWrapper<>();
        queryWrapperPriceDate.eq("pro_id",id);
        queryWrapperPriceDate.select("id");
        List<PriceDate> priceDateList = priceDateMapper.selectList(queryWrapperPriceDate);
        priceDateMapper.deleteBatchIds(priceDateList);

        //查到所有的JourneyPackage并删除
        QueryWrapper<JourneyPackage> queryWrapperJourneyPackage = new QueryWrapper<>();
        queryWrapperJourneyPackage.eq("pro_id",id);
        queryWrapperJourneyPackage.select("id");
        List<JourneyPackage> journeyPackageList = journeyPackageMapper.selectList(queryWrapperJourneyPackage);
        journeyPackageMapper.deleteBatchIds(journeyPackageList);

        //查到所有的BatchPackageMapper并删除
        QueryWrapper<BatchPackage> queryWrapperBatchPackage = new QueryWrapper<>();
        queryWrapperBatchPackage.eq("pro_id",id);
        queryWrapperBatchPackage.select("id");
        List<BatchPackage> batchPackageList = batchPackageMapper.selectList(queryWrapperBatchPackage);
        batchPackageMapper.deleteBatchIds(batchPackageList);

        return true;
    }

}
