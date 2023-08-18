package org.jeecg.modules.orders.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.Insure.entity.Insure;
import org.jeecg.modules.Insure.mapper.InsureMapper;
import org.jeecg.modules.guide.entity.TouristGuide;
import org.jeecg.modules.guide.mapper.TouristGuideMapper;
import org.jeecg.modules.orders.entity.OrdersPaid;
import org.jeecg.modules.orders.entity.OrdersUnpaid;
import org.jeecg.modules.orders.mapper.OrdersPaidMapper;
import org.jeecg.modules.orders.mapper.OrdersUnpaidMapper;
import org.jeecg.modules.orders.service.IOrdersUnpaidService;
import org.jeecg.modules.orders.vo.OrdersAllDetails;
import org.jeecg.modules.orders.vo.OrdersPaidDetails;
import org.jeecg.modules.orders.vo.OrdersUnpaidDetails;
import org.jeecg.modules.ordersFee.entity.OrdersFee;
import org.jeecg.modules.ordersFee.mapper.OrdersFeeMapper;
import org.jeecg.modules.product.entity.JourneyPackage;
import org.jeecg.modules.product.entity.Product;
import org.jeecg.modules.product.mapper.JourneyPackageMapper;
import org.jeecg.modules.product.mapper.ProductMapper;
import org.jeecg.modules.productguide.entity.ProductGuide;
import org.jeecg.modules.productguide.mapper.ProductGuideMapper;
import org.jeecg.modules.user.traveler.entity.Traveler;
import org.jeecg.modules.user.traveler.mapper.TravelerDao;
import org.jeecg.modules.user.userinfo.entity.WxClientUserinfo;
import org.jeecg.modules.user.userinfo.mapper.WxClientUserinfoMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.sql.Array;
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
    private TouristGuideMapper touristGuideMapper;

    @Autowired
    private InsureMapper insureMapper;

    @Autowired
    private TravelerDao travelerMapper;

    @Autowired
    private WxClientUserinfoMapper wxClientUserinfoMapper;

    @Autowired
    private OrdersFeeMapper ordersFeeMapper;

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
            if (i.getBatchpackageId() != null) {
                TouristGuide productGuide = touristGuideMapper.selectById(i.getBatchpackageId());
                ordersPaidDetails.setTouristGuide(productGuide);
            }

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
        Page<OrdersUnpaid> ordersUnpaidPage = null;
        if (userinfo != null) {
            ordersUnpaidPage = ordersUnpaidMapper.selectPage(page, new LambdaQueryWrapper<OrdersUnpaid>().eq(OrdersUnpaid::getUserId, userinfo.getId()).eq(OrdersUnpaid::getStatus, '0'));
        } else {
            ordersUnpaidPage = ordersUnpaidMapper.selectPage(page, new LambdaQueryWrapper<OrdersUnpaid>().eq(OrdersUnpaid::getStatus, '0'));
        }
        List<OrdersUnpaid> records = ordersUnpaidPage.getRecords();
        Page<OrdersUnpaidDetails> detailsPage = new Page<>();
        BeanUtils.copyProperties(ordersUnpaidPage, detailsPage);
        ArrayList<OrdersUnpaidDetails> ordersUnpaidDetailsArrayList = new ArrayList<>();
        records.forEach(i -> {
            OrdersUnpaidDetails ordersUnpaidDetails = new OrdersUnpaidDetails();
            // 对于每个订单，把订单的用户信息，产品信息，套餐信息，导游信息，出行人信息，保险信息查出来
            BeanUtils.copyProperties(i, ordersUnpaidDetails);
            if (userinfo == null) {
                WxClientUserinfo wxClientUserinfo = wxClientUserinfoMapper.selectOne(new LambdaQueryWrapper<WxClientUserinfo>().eq(WxClientUserinfo::getId, i.getUserId()));
                ordersUnpaidDetails.setUserinfo(wxClientUserinfo);
            } else {
                ordersUnpaidDetails.setUserinfo(userinfo);
            }

            // 产品信息
            Product product = productMapper.selectById(i.getProductId());
            ordersUnpaidDetails.setProduct(product);

            // 套餐信息
            JourneyPackage journeyPackage = journeyPackageMapper.selectById(i.getJourneypackageId());
            ordersUnpaidDetails.setJourneyPackage(journeyPackage);

            // 导游信息
            if (i.getBatchpackageId() != null) {
                TouristGuide productGuide = touristGuideMapper.selectById(i.getBatchpackageId());
                ordersUnpaidDetails.setTouristGuide(productGuide);
            }


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
        Page<OrdersPaid> ordersPaidPage = ordersPaidMapper.selectPage(page, new LambdaQueryWrapper<OrdersPaid>().eq(OrdersPaid::getUserId, userinfo.getId()).eq(OrdersPaid::getStatus, '1'));
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
            if (i.getBatchpackageId() != null) {
                TouristGuide productGuide = touristGuideMapper.selectById(i.getBatchpackageId());
                ordersPaidDetails.setTouristGuide(productGuide);
            }

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
        Page<OrdersPaid> ordersPaidPage = ordersPaidMapper.selectPage(page, new LambdaQueryWrapper<OrdersPaid>().eq(OrdersPaid::getUserId, userinfo.getId()).eq(OrdersPaid::getStatus, '2'));
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
            if (i.getBatchpackageId() != null) {
                TouristGuide productGuide = touristGuideMapper.selectById(i.getBatchpackageId());
                ordersPaidDetails.setTouristGuide(productGuide);
            }

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
        Page<OrdersPaid> ordersPaidPage = ordersPaidMapper.selectPage(page, new LambdaQueryWrapper<OrdersPaid>().eq(OrdersPaid::getUserId, userinfo.getId()).eq(OrdersPaid::getStatus, '3'));
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
            if (i.getBatchpackageId() != null) {
                TouristGuide productGuide = touristGuideMapper.selectById(i.getBatchpackageId());
                ordersPaidDetails.setTouristGuide(productGuide);
            }

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
        Page<OrdersPaid> ordersPaidPage = ordersPaidMapper.selectPage(page, new LambdaQueryWrapper<OrdersPaid>().eq(OrdersPaid::getUserId, userinfo.getId()).eq(OrdersPaid::getStatus, '4'));
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
            if (i.getBatchpackageId() != null) {
                TouristGuide productGuide = touristGuideMapper.selectById(i.getBatchpackageId());
                ordersPaidDetails.setTouristGuide(productGuide);
            }

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
    public IPage<OrdersUnpaidDetails> getOrderAllUnpaid(Page<OrdersUnpaid> page) {
        return null;
    }

    @Override
    public IPage<OrdersUnpaidDetails> getAllOrderPaid(Page<OrdersUnpaid> page) {
        return null;
    }

    @Override
    public IPage<OrdersAllDetails> getOrdersAllUnPaidDetails(Page<OrdersAllDetails> page) {
        // 获取第几页和每页几条
        long current = page.getCurrent();
        long size = page.getSize();

        // 创建OrdersUnpaid的page对象
        Page<OrdersUnpaid> unpaidPage = new Page<>(current, size);
        // 查询orders_unpaid表，status = 0 的数据,并且按照创建时间倒序排列
        Page<OrdersUnpaid> ordersUnpaidPage = ordersUnpaidMapper.selectPage(unpaidPage, new LambdaQueryWrapper<OrdersUnpaid>().eq(OrdersUnpaid::getStatus, "0").orderByDesc(OrdersUnpaid::getCreateTime));

        // 得到未付款的订单初始集合
        List<OrdersUnpaid> ordersUnpaidPageRecords = ordersUnpaidPage.getRecords();

        // 创建 IPage<OrdersAllDetails> 对象，等待处理完数据填充其records部分
        Page<OrdersAllDetails> ordersAllDetailsPage = new Page<>();
        BeanUtils.copyProperties(ordersUnpaidPage, ordersAllDetailsPage);
        // 创建 空集合，等待填充数据
        ArrayList<OrdersAllDetails> ordersAllDetails = new ArrayList<>();

        // 先处理未付款订单的初始集合
        if (ordersUnpaidPageRecords != null) {
            ordersUnpaidPageRecords.forEach(record -> {
                // 创建orderAllDetails对象 等待赋值
                OrdersAllDetails ordersUnpaidDetails = new OrdersAllDetails();

                // 订单id
                ordersUnpaidDetails.setId(record.getId());

                // 订单创建时间
                ordersUnpaidDetails.setCreateTime(record.getCreateTime());

                // 微信支付订单号
                ordersUnpaidDetails.setTransactionId(null); // 未付款，所以没有微信支付订单号

                // 产品信息
                String productId = record.getProductId();
                Product product = productMapper.selectById(productId);
                ordersUnpaidDetails.setProduct(product);

                // 出发日期
                ordersUnpaidDetails.setDateStarted(record.getDateStarted());

                // 结束日期
                ordersUnpaidDetails.setDateClosed(record.getDateClosed());


                OrdersFee ordersFee = ordersFeeMapper.selectOne(new LambdaQueryWrapper<OrdersFee>().eq(OrdersFee::getOrdersPaidId, record.getId()));
                // 行程套餐（在ordersFee里面查到）
                JourneyPackage journeyPackage = new JourneyPackage();
                journeyPackage.setJpContent(ordersFee.getPackageName());
                journeyPackage.setJpPriceAdult(ordersFee.getPackageFeeAdult());
                journeyPackage.setJpPriceChild(ordersFee.getPackageFeeChild());
                // 组装journeyPackage对象完毕，加入OrdersAllDetails类的属性中
                ordersUnpaidDetails.setJourneypackage(journeyPackage);

                // 导游信息（可能没有导游，非空判断）
                if (StringUtils.isNotEmpty(record.getBatchpackageId())) {
                    TouristGuide touristGuide = touristGuideMapper.selectById(record.getBatchpackageId());
                    ordersUnpaidDetails.setTouristGuide(touristGuide);
                }

                // 用户信息
                String userId = record.getUserId();
                WxClientUserinfo userinfo = wxClientUserinfoMapper.selectOne(new LambdaQueryWrapper<WxClientUserinfo>().eq(WxClientUserinfo::getId, userId));
                ordersUnpaidDetails.setUserinfo(userinfo);

                // 联系人姓名
                ordersUnpaidDetails.setContactName(record.getContactName());

                // 联系人手机号
                ordersUnpaidDetails.setContactPhone(record.getContactPhone());

                // 出行人信息
                ArrayList<Traveler> travelerArrayList = new ArrayList<>();
                record.getTravelerId().forEach(travelerId -> {
                    travelerArrayList.add(travelerMapper.selectById(travelerId));
                });
                ordersUnpaidDetails.setTravellers(travelerArrayList);

                // 成人个数
                ordersUnpaidDetails.setAdultCount(record.getAdultCount());

                // 儿童个数
                ordersUnpaidDetails.setChildrenCount(record.getChildrenCount());

                // 实付金额
                ordersUnpaidDetails.setPaidMoney(record.getPayingMoney());

                // 付款方式
                ordersUnpaidDetails.setPaidMethod("微信支付");

                // 优惠卷抵扣金额
                ordersUnpaidDetails.setCoupon(null);

                // 保险信息
                ArrayList<Insure> insures = new ArrayList<>();
                List<String> insureName = ordersFee.getInsureName();
                List<String> insureFee = ordersFee.getInsureFee();
                for (int i = 0; i < insureName.size(); i++) {
                    String name = insureName.get(i);
                    if ("".equals(name)) {
                        break;
                    }
                    String price = insureFee.get(i);
                    insures.add(new Insure().setContent(name).setPrice(Integer.valueOf(price)));
                }
                ordersUnpaidDetails.setInsures(insures);

                // 订单备注
                ordersUnpaidDetails.setNote(record.getNote());

                // 游侠币抵扣金额
                ordersUnpaidDetails.setYouxiabi(null);

                // 订单状态
                ordersUnpaidDetails.setStatus(record.getStatus());

                // 每次处理完之后，将orderAllDetails对象加入ordersAllDetails集合中
                ordersAllDetails.add(ordersUnpaidDetails);
            });
        }
        // 设置page对象的records属性
        ordersAllDetailsPage.setRecords(ordersAllDetails);

        return ordersAllDetailsPage;
    }

    @Override
    public IPage<OrdersAllDetails> getOrdersAllPaidDetails(Page<OrdersAllDetails> page) {
        // 获取第几页和每页几条
        long current = page.getCurrent();
        long size = page.getSize();

        // 创建OrdersPaid的page对象
        Page<OrdersPaid> paidPage = new Page<>(current, size);
        // 查询orders_paid表的数据,并且按照创建时间倒序排列
        Page<OrdersPaid> ordersPaidPage = ordersPaidMapper.selectPage(paidPage, new LambdaQueryWrapper<OrdersPaid>().orderByDesc(OrdersPaid::getCreateTime));
        // 得到已付款的订单初始集合
        List<OrdersPaid> ordersPaidPageRecords = ordersPaidPage.getRecords();


        // 创建 IPage<OrdersAllDetails> 对象，等待处理完数据填充其records部分
        Page<OrdersAllDetails> ordersAllDetailsPage = new Page<>();
        BeanUtils.copyProperties(ordersPaidPage, ordersAllDetailsPage);
        // 创建 空集合，等待填充数据
        ArrayList<OrdersAllDetails> ordersAllDetails = new ArrayList<>();

        // 再处理已付款订单的初始集合
        if (ordersPaidPageRecords != null) {
            ordersPaidPageRecords.forEach(record -> {
                // 创建orderAllDetails对象 等待赋值
                OrdersAllDetails ordersPaidDetails = new OrdersAllDetails();

                // 订单id
                ordersPaidDetails.setId(record.getId());

                // 订单创建时间
                ordersPaidDetails.setCreateTime(record.getCreateTime());

                // 微信支付订单号
                ordersPaidDetails.setTransactionId(record.getTransactionId()); // 未付款，所以没有微信支付订单号

                // 产品信息
                String productId = record.getProductId();
                Product product = productMapper.selectById(productId);
                ordersPaidDetails.setProduct(product);

                // 出发日期
                ordersPaidDetails.setDateStarted(record.getDateStarted());

                // 结束日期
                ordersPaidDetails.setDateClosed(record.getDateClosed());

                OrdersFee ordersFee = ordersFeeMapper.selectOne(new LambdaQueryWrapper<OrdersFee>().eq(OrdersFee::getOrdersPaidId, record.getId()));
                // 行程套餐（在ordersFee里面查到）
                JourneyPackage journeyPackage = new JourneyPackage();
                journeyPackage.setJpContent(ordersFee.getPackageName());
                journeyPackage.setJpPriceAdult(ordersFee.getPackageFeeAdult());
                journeyPackage.setJpPriceChild(ordersFee.getPackageFeeChild());
                // 组装journeyPackage对象完毕，加入OrdersAllDetails类的属性中
                ordersPaidDetails.setJourneypackage(journeyPackage);

                // 导游信息（可能没有导游，非空判断）
                if (StringUtils.isNotEmpty(record.getBatchpackageId())) {
                    TouristGuide touristGuide = touristGuideMapper.selectById(record.getBatchpackageId());
                    ordersPaidDetails.setTouristGuide(touristGuide);
                }

                // 用户信息
                String userId = record.getUserId();
                WxClientUserinfo userinfo = wxClientUserinfoMapper.selectOne(new LambdaQueryWrapper<WxClientUserinfo>().eq(WxClientUserinfo::getId, userId));
                ordersPaidDetails.setUserinfo(userinfo);

                // 联系人姓名
                ordersPaidDetails.setContactName(record.getContactName());

                // 联系人手机号
                ordersPaidDetails.setContactPhone(record.getContactPhone());

                // 出行人信息
                ArrayList<Traveler> travelerArrayList = new ArrayList<>();
                record.getTravellerId().forEach(travelerId -> {
                    travelerArrayList.add(travelerMapper.selectById(travelerId));
                });
                ordersPaidDetails.setTravellers(travelerArrayList);

                // 成人个数
                ordersPaidDetails.setAdultCount(record.getAdultCount());

                // 儿童个数
                ordersPaidDetails.setChildrenCount(record.getChildrenCount());

                // 实付金额
                ordersPaidDetails.setPaidMoney(record.getPaidMoney());

                // 付款方式
                ordersPaidDetails.setPaidMethod(record.getPaidMethod());

                // 优惠卷抵扣金额
                ordersPaidDetails.setCoupon(null);

                // 保险信息
                ArrayList<Insure> insures = new ArrayList<>();
                List<String> insureName = ordersFee.getInsureName();
                List<String> insureFee = ordersFee.getInsureFee();
                for (int i = 0; i < insureName.size(); i++) {
                    String name = insureName.get(i);
                    if ("".equals(name)) {
                        break;
                    }
                    String price = insureFee.get(i);
                    insures.add(new Insure().setContent(name).setPrice(Integer.valueOf(price)));
                }
                ordersPaidDetails.setInsures(insures);

                // 订单备注
                ordersPaidDetails.setNote(record.getNote());

                // 游侠币抵扣金额
                ordersPaidDetails.setYouxiabi(null);

                // 订单状态
                ordersPaidDetails.setStatus(record.getStatus());

                // 每次处理完之后，将orderAllDetails对象加入ordersAllDetails集合中
                ordersAllDetails.add(ordersPaidDetails);
            });
        }

        // 设置page对象的records属性
        ordersAllDetailsPage.setRecords(ordersAllDetails);

        return ordersAllDetailsPage;
    }

    @Override
    public IPage<OrdersAllDetails> getUnpaidOrdersBySearch(Page<OrdersAllDetails> page, String keyword) {
        // 获取第几页和每页几条
        long current = page.getCurrent();
        long size = page.getSize();

        // 创建OrdersUnpaid的page对象
        Page<OrdersUnpaid> unpaidPage = new Page<>(current, size);
        // 查询orders_unpaid表，status = 0 的数据,并且按照创建时间倒序排列
        Page<OrdersUnpaid> ordersUnpaidPage = ordersUnpaidMapper.selectPage(unpaidPage, new LambdaQueryWrapper<OrdersUnpaid>().eq(OrdersUnpaid::getStatus, "0")
                .orderByDesc(OrdersUnpaid::getCreateTime).eq(OrdersUnpaid::getId, keyword)
        );

        // 得到未付款的订单初始集合
        List<OrdersUnpaid> ordersUnpaidPageRecords = ordersUnpaidPage.getRecords();

        // 创建 IPage<OrdersAllDetails> 对象，等待处理完数据填充其records部分
        Page<OrdersAllDetails> ordersAllDetailsPage = new Page<>();
        BeanUtils.copyProperties(ordersUnpaidPage, ordersAllDetailsPage);
        // 创建 空集合，等待填充数据
        ArrayList<OrdersAllDetails> ordersAllDetails = new ArrayList<>();

        // 先处理未付款订单的初始集合
        if (ordersUnpaidPageRecords != null) {
            ordersUnpaidPageRecords.forEach(record -> {
                // 创建orderAllDetails对象 等待赋值
                OrdersAllDetails ordersUnpaidDetails = new OrdersAllDetails();

                // 订单id
                ordersUnpaidDetails.setId(record.getId());

                // 订单创建时间
                ordersUnpaidDetails.setCreateTime(record.getCreateTime());

                // 微信支付订单号
                ordersUnpaidDetails.setTransactionId(null); // 未付款，所以没有微信支付订单号

                // 产品信息
                String productId = record.getProductId();
                Product product = productMapper.selectById(productId);
                ordersUnpaidDetails.setProduct(product);

                // 出发日期
                ordersUnpaidDetails.setDateStarted(record.getDateStarted());

                // 结束日期
                ordersUnpaidDetails.setDateClosed(record.getDateClosed());


                OrdersFee ordersFee = ordersFeeMapper.selectOne(new LambdaQueryWrapper<OrdersFee>().eq(OrdersFee::getOrdersPaidId, record.getId()));
                // 行程套餐（在ordersFee里面查到）
                JourneyPackage journeyPackage = new JourneyPackage();
                journeyPackage.setJpContent(ordersFee.getPackageName());
                journeyPackage.setJpPriceAdult(ordersFee.getPackageFeeAdult());
                journeyPackage.setJpPriceChild(ordersFee.getPackageFeeChild());
                // 组装journeyPackage对象完毕，加入OrdersAllDetails类的属性中
                ordersUnpaidDetails.setJourneypackage(journeyPackage);

                // 导游信息（可能没有导游，非空判断）
                if (StringUtils.isNotEmpty(record.getBatchpackageId())) {
                    TouristGuide touristGuide = touristGuideMapper.selectById(record.getBatchpackageId());
                    ordersUnpaidDetails.setTouristGuide(touristGuide);
                }

                // 用户信息
                String userId = record.getUserId();
                WxClientUserinfo userinfo = wxClientUserinfoMapper.selectOne(new LambdaQueryWrapper<WxClientUserinfo>().eq(WxClientUserinfo::getId, userId));
                ordersUnpaidDetails.setUserinfo(userinfo);

                // 联系人姓名
                ordersUnpaidDetails.setContactName(record.getContactName());

                // 联系人手机号
                ordersUnpaidDetails.setContactPhone(record.getContactPhone());

                // 出行人信息
                ArrayList<Traveler> travelerArrayList = new ArrayList<>();
                record.getTravelerId().forEach(travelerId -> {
                    travelerArrayList.add(travelerMapper.selectById(travelerId));
                });
                ordersUnpaidDetails.setTravellers(travelerArrayList);

                // 成人个数
                ordersUnpaidDetails.setAdultCount(record.getAdultCount());

                // 儿童个数
                ordersUnpaidDetails.setChildrenCount(record.getChildrenCount());

                // 实付金额
                ordersUnpaidDetails.setPaidMoney(record.getPayingMoney());

                // 付款方式
                ordersUnpaidDetails.setPaidMethod("微信支付");

                // 优惠卷抵扣金额
                ordersUnpaidDetails.setCoupon(null);

                // 保险信息
                ArrayList<Insure> insures = new ArrayList<>();
                List<String> insureName = ordersFee.getInsureName();
                List<String> insureFee = ordersFee.getInsureFee();
                for (int i = 0; i < insureName.size(); i++) {
                    String name = insureName.get(i);
                    if ("".equals(name)) {
                        break;
                    }
                    String price = insureFee.get(i);
                    insures.add(new Insure().setContent(name).setPrice(Integer.valueOf(price)));
                }
                ordersUnpaidDetails.setInsures(insures);

                // 订单备注
                ordersUnpaidDetails.setNote(record.getNote());

                // 游侠币抵扣金额
                ordersUnpaidDetails.setYouxiabi(null);

                // 订单状态
                ordersUnpaidDetails.setStatus(record.getStatus());

                // 每次处理完之后，将orderAllDetails对象加入ordersAllDetails集合中
                ordersAllDetails.add(ordersUnpaidDetails);
            });
        }
        // 设置page对象的records属性
        ordersAllDetailsPage.setRecords(ordersAllDetails);

        return ordersAllDetailsPage;
    }

    @Override
    public IPage<OrdersAllDetails> getPaidOrdersBySearch(Page<OrdersAllDetails> page, String keyword) {
        // 获取第几页和每页几条
        long current = page.getCurrent();
        long size = page.getSize();

        // 创建OrdersPaid的page对象
        Page<OrdersPaid> paidPage = new Page<>(current, size);
        // 查询orders_paid表的数据,并且按照创建时间倒序排列
        Page<OrdersPaid> ordersPaidPage = ordersPaidMapper.selectPage(paidPage, new LambdaQueryWrapper<OrdersPaid>()
                .orderByDesc(OrdersPaid::getCreateTime).eq(OrdersPaid::getId, keyword));
        // 得到已付款的订单初始集合
        List<OrdersPaid> ordersPaidPageRecords = ordersPaidPage.getRecords();


        // 创建 IPage<OrdersAllDetails> 对象，等待处理完数据填充其records部分
        Page<OrdersAllDetails> ordersAllDetailsPage = new Page<>();
        BeanUtils.copyProperties(ordersPaidPage, ordersAllDetailsPage);
        // 创建 空集合，等待填充数据
        ArrayList<OrdersAllDetails> ordersAllDetails = new ArrayList<>();

        // 再处理已付款订单的初始集合
        if (ordersPaidPageRecords != null) {
            ordersPaidPageRecords.forEach(record -> {
                // 创建orderAllDetails对象 等待赋值
                OrdersAllDetails ordersPaidDetails = new OrdersAllDetails();

                // 订单id
                ordersPaidDetails.setId(record.getId());

                // 订单创建时间
                ordersPaidDetails.setCreateTime(record.getCreateTime());

                // 微信支付订单号
                ordersPaidDetails.setTransactionId(record.getTransactionId()); // 未付款，所以没有微信支付订单号

                // 产品信息
                String productId = record.getProductId();
                Product product = productMapper.selectById(productId);
                ordersPaidDetails.setProduct(product);

                // 出发日期
                ordersPaidDetails.setDateStarted(record.getDateStarted());

                // 结束日期
                ordersPaidDetails.setDateClosed(record.getDateClosed());

                OrdersFee ordersFee = ordersFeeMapper.selectOne(new LambdaQueryWrapper<OrdersFee>().eq(OrdersFee::getOrdersPaidId, record.getId()));
                // 行程套餐（在ordersFee里面查到）
                JourneyPackage journeyPackage = new JourneyPackage();
                journeyPackage.setJpContent(ordersFee.getPackageName());
                journeyPackage.setJpPriceAdult(ordersFee.getPackageFeeAdult());
                journeyPackage.setJpPriceChild(ordersFee.getPackageFeeChild());
                // 组装journeyPackage对象完毕，加入OrdersAllDetails类的属性中
                ordersPaidDetails.setJourneypackage(journeyPackage);

                // 导游信息（可能没有导游，非空判断）
                if (StringUtils.isNotEmpty(record.getBatchpackageId())) {
                    TouristGuide touristGuide = touristGuideMapper.selectById(record.getBatchpackageId());
                    ordersPaidDetails.setTouristGuide(touristGuide);
                }

                // 用户信息
                String userId = record.getUserId();
                WxClientUserinfo userinfo = wxClientUserinfoMapper.selectOne(new LambdaQueryWrapper<WxClientUserinfo>().eq(WxClientUserinfo::getId, userId));
                ordersPaidDetails.setUserinfo(userinfo);

                // 联系人姓名
                ordersPaidDetails.setContactName(record.getContactName());

                // 联系人手机号
                ordersPaidDetails.setContactPhone(record.getContactPhone());

                // 出行人信息
                ArrayList<Traveler> travelerArrayList = new ArrayList<>();
                record.getTravellerId().forEach(travelerId -> {
                    travelerArrayList.add(travelerMapper.selectById(travelerId));
                });
                ordersPaidDetails.setTravellers(travelerArrayList);

                // 成人个数
                ordersPaidDetails.setAdultCount(record.getAdultCount());

                // 儿童个数
                ordersPaidDetails.setChildrenCount(record.getChildrenCount());

                // 实付金额
                ordersPaidDetails.setPaidMoney(record.getPaidMoney());

                // 付款方式
                ordersPaidDetails.setPaidMethod(record.getPaidMethod());

                // 优惠卷抵扣金额
                ordersPaidDetails.setCoupon(null);

                // 保险信息
                ArrayList<Insure> insures = new ArrayList<>();
                List<String> insureName = ordersFee.getInsureName();
                List<String> insureFee = ordersFee.getInsureFee();
                for (int i = 0; i < insureName.size(); i++) {
                    String name = insureName.get(i);
                    if ("".equals(name)) {
                        break;
                    }
                    String price = insureFee.get(i);
                    insures.add(new Insure().setContent(name).setPrice(Integer.valueOf(price)));
                }
                ordersPaidDetails.setInsures(insures);

                // 订单备注
                ordersPaidDetails.setNote(record.getNote());

                // 游侠币抵扣金额
                ordersPaidDetails.setYouxiabi(null);

                // 订单状态
                ordersPaidDetails.setStatus(record.getStatus());

                // 每次处理完之后，将orderAllDetails对象加入ordersAllDetails集合中
                ordersAllDetails.add(ordersPaidDetails);
            });
        }

        // 设置page对象的records属性
        ordersAllDetailsPage.setRecords(ordersAllDetails);

        return ordersAllDetailsPage;
    }

}
