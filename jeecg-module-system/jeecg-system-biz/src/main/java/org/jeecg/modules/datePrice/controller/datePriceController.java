package org.jeecg.modules.datePrice.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.datePrice.vo.DatePrice;
import org.jeecg.modules.follow.entity.Follow;
import org.jeecg.modules.product.entity.PriceDate;
import org.jeecg.modules.product.mapper.PriceDateMapper;
import org.jeecg.modules.product.service.IPriceDateService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 14015
 * @version 1.0
 * @data 2023/8/3 10:34
 */
@Api(tags="后台查询日程价格表")
@RestController
@RequestMapping("/priceDate/priceDate")
@Slf4j
public class datePriceController {
    @Autowired
    IPriceDateService priceDateService;

    /**
     * 通过proId查询
     *
     * @param proId
     * @return
     */
    //@AutoLog(value = "后台查询日程价格表-通过id查询")
    @ApiOperation(value="后台查询日程价格表-通过proId查询", notes="后台查询日程价格表-通过proId查询")
    @GetMapping(value = "/queryByProId")
    public Result<List<PriceDate>> queryById(@RequestParam(name="proId",required=true) String proId) {
        List<PriceDate> list = priceDateService.list(new LambdaQueryWrapper<PriceDate>().eq(PriceDate::getProId, proId));

        return Result.OK(list);
    }


    /**
     *   修改
     *
     * @param datePrices
     * @return
     */
    @AutoLog(value = "后台查询日程价格表-修改")
    @ApiOperation(value="后台查询日程价格表-修改", notes="后台查询日程价格表-修改")
    @PostMapping(value = "/updatePriceAndDate")
    public Result<String> updatePriceAndDate(@RequestBody List<PriceDate> priceDates) {
        for (PriceDate priceDate : priceDates) {
            priceDateService.updateById(priceDate);
        }
        return Result.OK("保存成功！");
    }

    /**
     *   通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "后台查询日程价格表-通过id删除")
    @ApiOperation(value="后台查询日程价格表-通过id删除", notes="后台查询日程价格表-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestBody(required=true) Map<String,String> body) {
        System.out.println(body);
        priceDateService.remove(new LambdaQueryWrapper<PriceDate>().eq(PriceDate::getId,body.get("id")));
        return Result.OK("删除成功!");
    }

    /**
     *   添加
     *
     * @param priceDate
     * @return
     */
    @AutoLog(value = "关注人表-添加")
    @ApiOperation(value="关注人表-添加", notes="关注人表-添加")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody PriceDate priceDate) {
        priceDateService.save(priceDate);
        String id = priceDate.getId();
        return Result.OK("添加成功",id);
    }




}
