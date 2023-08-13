package org.jeecg.modules.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.Insure.entity.Insure;
import org.jeecg.modules.Insure.mapper.InsureMapper;
import org.jeecg.modules.product.entity.*;
import org.jeecg.modules.product.mapper.*;
import org.jeecg.modules.product.service.IProductService;
import org.jeecg.modules.product.service.IScheduleService;
import org.jeecg.modules.product.vo.ProductVo;
import org.jeecg.modules.product.vo.ScheduleProVo;
import org.jeecg.modules.product.vo.ScheduleVo;
import org.jeecg.modules.productguide.entity.ProductGuide;
import org.jeecg.modules.productguide.mapper.ProductGuideMapper;
import org.jeecg.modules.purchaseNotes.entity.PurchaseNotes;
import org.jeecg.modules.purchaseNotes.mapper.PurchaseNotesMapper;
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
    @Autowired
    private ProductGuideMapper productGuideMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private PurchaseNotesMapper purchaseNotesMapper;
    @Autowired
    private InsureMapper insureMapper;
    @Autowired
    private CostDescriptionMapper costDescriptionMapper;






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
        if(id != null && id != "") productMapper.deleteById(id);

        //查到所有的Schedule并删除
        QueryWrapper<Schedule> queryWrapperSchedule = new QueryWrapper<>();
        queryWrapperSchedule.eq("pro_id",id);
        queryWrapperSchedule.select("id");
        List<Schedule> scheduleList = scheduleMapper.selectList(queryWrapperSchedule);
        if(scheduleList != null && scheduleList.size()>0)scheduleMapper.deleteBatchIds(scheduleList);

        //查到所有的Schedule并删除
        QueryWrapper<Task> queryWrapperTask = new QueryWrapper<>();
        queryWrapperTask.eq("pro_id",id);
        queryWrapperTask.select("id");
        List<Task> taskList = taskMapper.selectList(queryWrapperTask);
        if(taskList != null && taskList.size()>0)taskMapper.deleteBatchIds(taskList);


        //查到所有的PriceDate并删除
        QueryWrapper<PriceDate> queryWrapperPriceDate = new QueryWrapper<>();
        queryWrapperPriceDate.eq("pro_id",id);
        queryWrapperPriceDate.select("id");
        List<PriceDate> priceDateList = priceDateMapper.selectList(queryWrapperPriceDate);
        if(priceDateList != null && priceDateList.size()>0)priceDateMapper.deleteBatchIds(priceDateList);


        //查到所有的JourneyPackage并删除
        QueryWrapper<JourneyPackage> queryWrapperJourneyPackage = new QueryWrapper<>();
        queryWrapperJourneyPackage.eq("pro_id",id);
        queryWrapperJourneyPackage.select("id");
        List<JourneyPackage> journeyPackageList = journeyPackageMapper.selectList(queryWrapperJourneyPackage);
        if(journeyPackageList != null && journeyPackageList.size()>0)journeyPackageMapper.deleteBatchIds(journeyPackageList);


        //查到所有的BatchPackageMapper并删除
        QueryWrapper<BatchPackage> queryWrapperBatchPackage = new QueryWrapper<>();
        queryWrapperBatchPackage.eq("pro_id",id);
        queryWrapperBatchPackage.select("id");
        List<BatchPackage> batchPackageList = batchPackageMapper.selectList(queryWrapperBatchPackage);
        if(batchPackageList != null && batchPackageList.size()>0)batchPackageMapper.deleteBatchIds(batchPackageList);

        //查到所有导游并删除
        QueryWrapper<ProductGuide> queryGuides = new QueryWrapper<>();
        queryGuides.eq("product_id",id);
        queryGuides.select("id");
        List<ProductGuide> productGuides = productGuideMapper.selectList(queryGuides);
        if(productGuides != null && productGuides.size()>0)productGuideMapper.deleteBatchIds(productGuides);
        //查到所有标签并删除
        QueryWrapper<Tag> queryTags = new QueryWrapper<>();
        queryTags.eq("pro_id",id);
        queryTags.select("id");
        List<Tag> tags = tagMapper.selectList(queryTags);
        if(tags != null && tags.size()>0)tagMapper.deleteBatchIds(tags);
        //删除购买说明
        QueryWrapper<PurchaseNotes> queryPurchaseNotes = new QueryWrapper<>();
        queryPurchaseNotes.eq("pro_id",id);
        queryPurchaseNotes.select("id");
        List<PurchaseNotes> purchaseNotes = purchaseNotesMapper.selectList(queryPurchaseNotes);
        if(purchaseNotes != null && purchaseNotes.size()>0)purchaseNotesMapper.deleteBatchIds(purchaseNotes);
        //删除费用说明
        QueryWrapper<CostDescription> queryCostDescription = new QueryWrapper<>();
        queryCostDescription.eq("pro_id",id);
        queryCostDescription.select("id");
        List<CostDescription> costDescriptions = costDescriptionMapper.selectList(queryCostDescription);
        if(costDescriptions != null && costDescriptions.size()>0)costDescriptionMapper.deleteBatchIds(costDescriptions);
        //删除保险
        QueryWrapper<Insure> queryInsure = new QueryWrapper<>();
        queryInsure.eq("proid",id);
        queryInsure.select("id");
        List<Insure> insures = insureMapper.selectList(queryInsure);
        if(insures != null && insures.size()>0)insureMapper.deleteBatchIds(insures);
        //删除每日价格
        QueryWrapper<PriceDate> queryPriceDate = new QueryWrapper<>();
        queryPriceDate.eq("pro_id",id);
        queryPriceDate.select("id");
        List<PriceDate> priceDates = priceDateMapper.selectList(queryPriceDate);
        if(priceDates != null && priceDates.size()>0)priceDateMapper.deleteBatchIds(priceDates);
        return true;
    }

}
