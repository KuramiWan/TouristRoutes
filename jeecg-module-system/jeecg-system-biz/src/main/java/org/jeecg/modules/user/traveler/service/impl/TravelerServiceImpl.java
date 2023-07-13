package org.jeecg.modules.user.traveler.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.user.traveler.entity.Traveler;
import org.jeecg.modules.user.traveler.service.TravelerService;
import org.jeecg.modules.user.traveler.mapper.TravelerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 出行人(Traveler)表服务实现类
 *
 * @author makejava
 * @since 2023-07-13 15:27:07
 */
@Service("travelerService")
public class TravelerServiceImpl extends ServiceImpl<TravelerDao, Traveler> implements TravelerService {
    @Autowired
    private TravelerDao travelerDao;

    /**
     * 分页查询所有数据
     *
     * @param page     分页对象
     * @param traveler 查询实体
     * @return 所有数据
     */

    public Result selectAll(Page<Traveler> page, Traveler traveler) {
        return Result.ok(travelerDao.selectPage(page, new QueryWrapper<>(traveler)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */

    public Result selectOne(Long id) {
        return Result.ok(travelerDao.selectById(id));
    }

    /**
     * 新增数据
     *
     * @param traveler 实体对象
     * @return 新增结果
     */

    public Result insert(Traveler traveler) {
        return Result.ok(travelerDao.insert(traveler));
    }

    /**
     * 修改数据
     *
     * @param traveler 实体对象
     * @return 修改结果
     */

    public Result update(Traveler traveler) {
        return Result.ok(travelerDao.updateById(traveler));
    }

    /**
     * 删除数据
     *
     * @param id 主键结合
     * @return 删除结果
     */

    public Result delete(Long id) {
        return Result.ok(travelerDao.deleteById(id));
    }
}

