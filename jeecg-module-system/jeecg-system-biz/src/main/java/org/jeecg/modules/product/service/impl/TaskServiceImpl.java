package org.jeecg.modules.product.service.impl;

import org.jeecg.modules.product.entity.Task;
import org.jeecg.modules.product.mapper.TaskMapper;
import org.jeecg.modules.product.service.ITaskService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 某个产品某一天的所有任务
 * @Author: jeecg-boot
 * @Date:   2023-07-14
 * @Version: V1.0
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements ITaskService {

}
