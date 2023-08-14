package org.jeecg.modules.orders.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.Insure.entity.Insure;
import org.jeecg.modules.Insure.mapper.InsureMapper;
import org.jeecg.modules.orders.entity.OrdersPaid;
import org.jeecg.modules.orders.entity.OrdersUnpaid;
import org.jeecg.modules.orders.mapper.OrdersPaidMapper;
import org.jeecg.modules.orders.mapper.OrdersUnpaidMapper;
import org.jeecg.modules.orders.service.IOrdersUnpaidService;
import org.jeecg.modules.orders.vo.OrdersPaidDetails;
import org.jeecg.modules.orders.vo.OrdersUnpaidDetails;
import org.jeecg.modules.product.entity.JourneyPackage;
import org.jeecg.modules.product.entity.Product;
import org.jeecg.modules.product.mapper.JourneyPackageMapper;
import org.jeecg.modules.product.mapper.ProductMapper;
import org.jeecg.modules.productguide.entity.ProductGuide;
import org.jeecg.modules.productguide.mapper.ProductGuideMapper;
import org.jeecg.modules.user.traveler.entity.Traveler;
import org.jeecg.modules.user.traveler.mapper.TravelerDao;
import org.jeecg.modules.user.userinfo.entity.WxClientUserinfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 未付款的订单表
 * @Author: jeecg-boot
 * @Date: 2023-07-13
 * @Version: V1.0
 */
@Service
public class OrdersUnpaidServiceImpl extends ServiceImpl<OrdersUnpaidMapper, OrdersUnpaid> implements IOrdersUnpaidService {
    @Autowired
    private OrdersUnpaidMapper ordersUnpaidMapper;

    @Autowired
    private OrdersPaidMapper ordersPaidMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private JourneyPackageMapper journeyPackageMapper;

    @Autowired
    private ProductGuideMapper productGuideMapper;

    @Autowired
    private InsureMapper insureMapper;

    @Autowired
    private TravelerDao travelerMapper;

    @Override
    public IPage<OrdersPaidDetails> getOrderAllPaid(Page<OrdersPaid> page, WxClientUserinfo userinfo) {
        // 查询ordersUnpaid表，userid相同的数据
        Page<OrdersPaid> ordersPaidPage = ordersPaidMapper.selectPage(page, new LambdaQueryWrapper<OrdersPaid>().eq(OrdersPaid::getUserId, userinfo.getId()));
        if (ordersPaidPage.getRecords() == null) {
            return null;
        }
        List<OrdersPaid> records = ordersPaidPage.getRecords();
        System.out.println("records==========" + records);
        Page<OrdersPaidDetails> detailsPage = new Page<>();
        BeanUtils.copyProperties(ordersPaidPage, detailsPage);
        ArrayList<OrdersPaidDetails> ordersPaidDetailsArrayList = new ArrayList<>();
        records.forEach(i -> {
            OrdersPaidDetails ordersPaidDetails = new OrdersPaidDetails();
            // 对于每个订单，把订单的用户信息，产品信息，套餐信息，导游信息，出行人信息，保险信息查出来
            BeanUtils.copyProperties(i, ordersPaidDetails);
            ordersPaidDetails.setUserinfo(userinfo);

            // 产品信息
            Product product = productMapper.selectById(i.getProductId());
            ordersPaidDetails.setProduct(product);

            // 套餐信息
            JourneyPackage journeyPackage = journeyPackageMapper.selectById(i.getJourneypackageId());
            ordersPaidDetails.setJourneyPackage(journeyPackage);

            // 导游信息
            ProductGuide productGuide = productGuideMapper.selectById(i.getBatchpackageId());
            ordersPaidDetails.setProductGuide(productGuide);

            // 保险信息
            ArrayList<Insure> insureArrayList = new ArrayList<>();
            List<String> iInsureIds = i.getInsureId();
            if (iInsureIds != null) {
                iInsureIds.forEach(insureId -> {
                    insureArrayList.add(insureMapper.selectById(insureId));
                });
                ordersPaidDetails.setInsures(insureArrayList);
            }

            // 出行人信息
            ArrayList<Traveler> travelerArrayList = new ArrayList<>();
            List<String> travelerIds = i.getTravellerId();
            travelerIds.forEach(travelerId -> {
                travelerArrayList.add(travelerMapper.selectById(travelerId));
            });
            ordersPaidDetails.setTravelers(travelerArrayList);

            ordersPaidDetailsArrayList.add(ordersPaidDetails);
        });

        detailsPage.setRecords(ordersPaidDetailsArrayList);

        return detailsPage;
    }

    @Override
    public IPage<OrdersUnpaidDetails> getOrderUnpaid(Page<OrdersUnpaid> page, WxClientUserinfo userinfo) {
        // 查询ordersUnpaid表，userid相同的，status = 0的数据
        Page<OrdersUnpaid> ordersUnpaidPage = ordersUnpaidMapper.selectPage(page, new LambdaQueryWrapper<OrdersUnpaid>().eq(OrdersUnpaid::getUserId, userinfo.getId()).eq(OrdersUnpaid::getStatus, '0'));
        List<OrdersUnpaid> records = ordersUnpaidPage.getRecords();
        Page<OrdersUnpaidDetails> detailsPage = new Page<>();
        BeanUtils.copyProperties(ordersUnpaidPage, detailsPage);
        ArrayList<OrdersUnpaidDetails> ordersUnpaidDetailsArrayList = new ArrayList<>();
        records.forEach(i -> {
            OrdersUnpaidDetails ordersUnpaidDetails = new OrdersUnpaidDetails();
            // 对于每个订单，把订单的用户信息，产品信息，套餐信息，导游信息，出行人信息，保险信息查出来
            BeanUtils.copyProperties(i, ordersUnpaidDetails);
            ordersUnpaidDetails.setUserinfo(userinfo);

            // 产品信息
            Product product = productMapper.selectById(i.getProductId());
            ordersUnpaidDetails.setProduct(product);

            // 套餐信息
            JourneyPackage journeyPackage = journeyPackageMapper.selectById(i.getJourneypackageId());
            ordersUnpaidDetails.setJourneyPackage(journeyPackage);

            // 导游信息
            ProductGuide productGuide = productGuideMapper.selectById(i.getBatchpackageId());
            ordersUnpaidDetails.setProductGuide(productGuide);

            // 保险信息
            ArrayList<Insure> insureArrayList = new ArrayList<>();
            List<String> iInsureIds = i.getInsureId();
            if (iInsureIds != null) {
                iInsureIds.forEach(insureId -> {
                    insureArrayList.add(insureMapper.selectById(insureId));
                });
                ordersUnpaidDetails.setInsures(insureArrayList);
            }

            // 出行人信息
            ArrayList<Traveler> travelerArrayList = new ArrayList<>();
            List<String> travelerIds = i.getTravelerId();
            travelerIds.forEach(travelerId -> {
                travelerArrayList.add(travelerMapper.selectById(travelerId));
            });
            ordersUnpaidDetails.setTravelers(travelerArrayList);

            ordersUnpaidDetailsArrayList.add(ordersUnpaidDetails);
        });

        detailsPage.setRecords(ordersUnpaidDetailsArrayList);

        return detailsPage;
    }

    @Override
    public IPage<OrdersPaidDetails> getOrderPaidToConfirm(Page<OrdersPaid> page, WxClientUserinfo userinfo) {
        // 查询ordersUnpaid表，userid相同的数据
        Page<OrdersPaid> ordersPaidPage = ordersPaidMapper.selectPage(page, new LambdaQueryWrapper<OrdersPaid>().eq(OrdersPaid::getUserId, userinfo.getId()).eq(OrdersPaid::getStatus, 1));
        if (ordersPaidPage.getRecords() == null) {
            return null;
        }
        List<OrdersPaid> records = ordersPaidPage.getRecords();
        System.out.println("records==========" + records);
        Page<OrdersPaidDetails> detailsPage = new Page<>();
        BeanUtils.copyProperties(ordersPaidPage, detailsPage);
        ArrayList<OrdersPaidDetails> ordersPaidDetailsArrayList = new ArrayList<>();
        records.forEach(i -> {
            OrdersPaidDetails ordersPaidDetails = new OrdersPaidDetails();
            // 对于每个订单，把订单的用户信息，产品信息，套餐信息，导游信息，出行人信息，保险信息查出来
            BeanUtils.copyProperties(i, ordersPaidDetails);
            ordersPaidDetails.setUserinfo(userinfo);

            // 产品信息
            Product product = productMapper.selectById(i.getProductId());
            ordersPaidDetails.setProduct(product);

            // 套餐信息
            JourneyPackage journeyPackage = journeyPackageMapper.selectById(i.getJourneypackageId());
            ordersPaidDetails.setJourneyPackage(journeyPackage);

            // 导游信息
            ProductGuide productGuide = productGuideMapper.selectById(i.getBatchpackageId());
            ordersPaidDetails.setProductGuide(productGuide);

            // 保险信息
            ArrayList<Insure> insureArrayList = new ArrayList<>();
            List<String> iInsureIds = i.getInsureId();
            if (iInsureIds != null) {
                iInsureIds.forEach(insureId -> {
                    insureArrayList.add(insureMapper.selectById(insureId));
                });
                ordersPaidDetails.setInsures(insureArrayList);
            }

            // 出行人信息
            ArrayList<Traveler> travelerArrayList = new ArrayList<>();
            List<String> travelerIds = i.getTravellerId();
            travelerIds.forEach(travelerId -> {
                travelerArrayList.add(travelerMapper.selectById(travelerId));
            });
            ordersPaidDetails.setTravelers(travelerArrayList);

            ordersPaidDetailsArrayList.add(ordersPaidDetails);
        });

        detailsPage.setRecords(ordersPaidDetailsArrayList);

        return detailsPage;
    }

    @Override
    public IPage<OrdersPaidDetails> getOrderPaidToTravel(Page<OrdersPaid> page, WxClientUserinfo userinfo) {
        // 查询ordersUnpaid表，userid相同的数据
        Page<OrdersPaid> ordersPaidPage = ordersPaidMapper.selectPage(page, new LambdaQueryWrapper<OrdersPaid>().eq(OrdersPaid::getUserId, userinfo.getId()).eq(OrdersPaid::getStatus, 2));
        if (ordersPaidPage.getRecords() == null) {
            return null;
        }
        List<OrdersPaid> records = ordersPaidPage.getRecords();
        System.out.println("records==========" + records);
        Page<OrdersPaidDetails> detailsPage = new Page<>();
        BeanUtils.copyProperties(ordersPaidPage, detailsPage);
        ArrayList<OrdersPaidDetails> ordersPaidDetailsArrayList = new ArrayList<>();
        records.forEach(i -> {
            OrdersPaidDetails ordersPaidDetails = new OrdersPaidDetails();
            // 对于每个订单，把订单的用户信息，产品信息，套餐信息，导游信息，出行人信息，保险信息查出来
            BeanUtils.copyProperties(i, ordersPaidDetails);
            ordersPaidDetails.setUserinfo(userinfo);

            // 产品信息
            Product product = productMapper.selectById(i.getProductId());
            ordersPaidDetails.setProduct(product);

            // 套餐信息
            JourneyPackage journeyPackage = journeyPackageMapper.selectById(i.getJourneypackageId());
            ordersPaidDetails.setJourneyPackage(journeyPackage);

            // 导游信息
            ProductGuide productGuide = productGuideMapper.selectById(i.getBatchpackageId());
            ordersPaidDetails.setProductGuide(productGuide);

            // 保险信息
            ArrayList<Insure> insureArrayList = new ArrayList<>();
            List<String> iInsureIds = i.getInsureId();
            if (iInsureIds != null) {
                iInsureIds.forEach(insureId -> {
                    insureArrayList.add(insureMapper.selectById(insureId));
                });
                ordersPaidDetails.setInsures(insureArrayList);
            }

            // 出行人信息
            ArrayList<Traveler> travelerArrayList = new ArrayList<>();
            List<String> travelerIds = i.getTravellerId();
            travelerIds.forEach(travelerId -> {
                travelerArrayList.add(travelerMapper.selectById(travelerId));
            });
            ordersPaidDetails.setTravelers(travelerArrayList);

            ordersPaidDetailsArrayList.add(ordersPaidDetails);
        });

        detailsPage.setRecords(ordersPaidDetailsArrayList);

        return detailsPage;
    }

    @Override
    public IPage<OrdersPaidDetails> getOrderPaidToComment(Page<OrdersPaid> page, WxClientUserinfo userinfo) {
        // 查询ordersUnpaid表，userid相同的数据
        Page<OrdersPaid> ordersPaidPage = ordersPaidMapper.selectPage(page, new LambdaQueryWrapper<OrdersPaid>().eq(OrdersPaid::getUserId, userinfo.getId()).eq(OrdersPaid::getStatus, 3));
        if (ordersPaidPage.getRecords() == null) {
            return null;
        }
        List<OrdersPaid> records = ordersPaidPage.getRecords();
        System.out.println("records==========" + records);
        Page<OrdersPaidDetails> detailsPage = new Page<>();
        BeanUtils.copyProperties(ordersPaidPage, detailsPage);
        ArrayList<OrdersPaidDetails> ordersPaidDetailsArrayList = new ArrayList<>();
        records.forEach(i -> {
            OrdersPaidDetails ordersPaidDetails = new OrdersPaidDetails();
            // 对于每个订单，把订单的用户信息，产品信息，套餐信息，导游信息，出行人信息，保险信息查出来
            BeanUtils.copyProperties(i, ordersPaidDetails);
            ordersPaidDetails.setUserinfo(userinfo);

            // 产品信息
            Product product = productMapper.selectById(i.getProductId());
            ordersPaidDetails.setProduct(product);

            // 套餐信息
            JourneyPackage journeyPackage = journeyPackageMapper.selectById(i.getJourneypackageId());
            ordersPaidDetails.setJourneyPackage(journeyPackage);

            // 导游信息
            ProductGuide productGuide = productGuideMapper.selectById(i.getBatchpackageId());
            ordersPaidDetails.setProductGuide(productGuide);

            // 保险信息
            ArrayList<Insure> insureArrayList = new ArrayList<>();
            List<String> iInsureIds = i.getInsureId();
            if (iInsureIds != null) {
                iInsureIds.forEach(insureId -> {
                    insureArrayList.add(insureMapper.selectById(insureId));
                });
                ordersPaidDetails.setInsures(insureArrayList);
            }

            // 出行人信息
            ArrayList<Traveler> travelerArrayList = new ArrayList<>();
            List<String> travelerIds = i.getTravellerId();
            travelerIds.forEach(travelerId -> {
                travelerArrayList.add(travelerMapper.selectById(travelerId));
            });
            ordersPaidDetails.setTravelers(travelerArrayList);

            ordersPaidDetailsArrayList.add(ordersPaidDetails);
        });

        detailsPage.setRecords(ordersPaidDetailsArrayList);

        return detailsPage;
    }

    @Override
    public IPage<OrdersPaidDetails> getOrderPaidToOver(Page<OrdersPaid> page, WxClientUserinfo userinfo) {
        // 查询ordersUnpaid表，userid相同的数据
        Page<OrdersPaid> ordersPaidPage = ordersPaidMapper.selectPage(page, new LambdaQueryWrapper<OrdersPaid>().eq(OrdersPaid::getUserId, userinfo.getId()).eq(OrdersPaid::getStatus, 4));
        if (ordersPaidPage.getRecords() == null) {
            return null;
        }
        List<OrdersPaid> records = ordersPaidPage.getRecords();
        System.out.println("records==========" + records);
        Page<OrdersPaidDetails> detailsPage = new Page<>();
        BeanUtils.copyProperties(ordersPaidPage, detailsPage);
        ArrayList<OrdersPaidDetails> ordersPaidDetailsArrayList = new ArrayList<>();
        records.forEach(i -> {
            OrdersPaidDetails ordersPaidDetails = new OrdersPaidDetails();
            // 对于每个订单，把订单的用户信息，产品信息，套餐信息，导游信息，出行人信息，保险信息查出来
            BeanUtils.copyProperties(i, ordersPaidDetails);
            ordersPaidDetails.setUserinfo(userinfo);

            // 产品信息
            Product product = productMapper.selectById(i.getProductId());
            ordersPaidDetails.setProduct(product);

            // 套餐信息
            JourneyPackage journeyPackage = journeyPackageMapper.selectById(i.getJourneypackageId());
            ordersPaidDetails.setJourneyPackage(journeyPackage);

            // 导游信息
            ProductGuide productGuide = productGuideMapper.selectById(i.getBatchpackageId());
            ordersPaidDetails.setProductGuide(productGuide);

            // 保险信息
            ArrayList<Insure> insureArrayList = new ArrayList<>();
            List<String> iInsureIds = i.getInsureId();
            if (iInsureIds != null) {
                iInsureIds.forEach(insureId -> {
                    insureArrayList.add(insureMapper.selectById(insureId));
                });
                ordersPaidDetails.setInsures(insureArrayList);
            }

            // 出行人信息
            ArrayList<Traveler> travelerArrayList = new ArrayList<>();
            List<String> travelerIds = i.getTravellerId();
            travelerIds.forEach(travelerId -> {
                travelerArrayList.add(travelerMapper.selectById(travelerId));
            });
            ordersPaidDetails.setTravelers(travelerArrayList);

            ordersPaidDetailsArrayList.add(ordersPaidDetails);
        });

        detailsPage.setRecords(ordersPaidDetailsArrayList);

        return detailsPage;
    }

}
