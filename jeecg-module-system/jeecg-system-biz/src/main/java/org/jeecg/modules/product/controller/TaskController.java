package org.jeecg.modules.product.controller;

import java.sql.Array;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.jeecg.common.api.vo.Result;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.util.oss.OssBootUtil;
import org.jeecg.modules.product.entity.Product;
import org.jeecg.modules.product.entity.Schedule;
import org.jeecg.modules.product.entity.Task;
import org.jeecg.modules.product.service.IScheduleService;
import org.jeecg.modules.product.service.ITaskService;
import org.jeecg.modules.product.vo.ProductUpload;
import org.jeecg.modules.product.vo.ScheduleVo;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

/**
 * @Description: 某个产品某一天的所有任务
 * @Author: jeecg-boot
 * @Date: 2023-07-14
 * @Version: V1.0
 */
@Api(tags = "产品日程的任务")
@RestController
@RequestMapping("/core/task")
@Slf4j
public class TaskController extends JeecgController<Task, ITaskService> {
    @Autowired
    private ITaskService taskService;

    @Autowired
    private IScheduleService scheduleService;

    /**
     * 分页列表查询
     *
     * @param task
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "某个产品某一天的所有任务-分页列表查询")
    @ApiOperation(value = "某个产品某一天的所有任务-分页列表查询", notes = "某个产品某一天的所有任务-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<Task>> queryPageList(Task task,
                                             @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                             @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                             HttpServletRequest req) {
//		QueryWrapper<Task> queryWrapper = QueryGenerator.initQueryWrapper(task, req.getParameterMap());
        Page<Task> page = new Page<Task>(pageNo, pageSize);
        IPage<Task> pageList = taskService.page(page, new LambdaQueryWrapper<>());
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param task
     * @return
     */
    @AutoLog(value = "某个产品某一天的所有任务-添加")
    @ApiOperation(value = "某个产品某一天的所有任务-添加", notes = "某个产品某一天的所有任务-添加")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody Task task) {
        taskService.save(task);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param task
     * @return
     */
    @AutoLog(value = "某个产品某一天的所有任务-编辑")
    @ApiOperation(value = "某个产品某一天的所有任务-编辑", notes = "某个产品某一天的所有任务-编辑")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody Task task) {
        taskService.updateById(task);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param map
     * @return
     */
    @AutoLog(value = "某个产品某一天的所有任务-通过id删除")
    @ApiOperation(value = "某个产品某一天的所有任务-通过id删除", notes = "某个产品某一天的所有任务-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestBody Map<String, String> map) {
        taskService.removeById(map.get("id"));
        return Result.OK("删除成功!");
    }

    @AutoLog(value = "某个产品某一天的所有任务-上传图床返回url")
    @ApiOperation(value = "某个产品某一天的所有任务-上传图床返回url", notes = "某个产品某一天的所有任务-上传图床返回url")
    @PostMapping(value = "/uploadTaskImg")
    public Result<List<String>> uploadTaskImg(@RequestBody Map<String, String> map) {
        ArrayList<String> list = new ArrayList<>();
        String base64Img = map.get("base64Img");
        //上传图床并更新数据库中的图片字段
        list.add(uploadImg(base64Img));
        return Result.OK(list);
    }

    public String uploadImg(String base64Img) {
        try {
            // 将Base64数据转换为字节数组
            byte[] pageImg = Base64.getDecoder().decode(base64Img);
            String fileDir1 = "suixinyou-wx-client/pages-product/产品日程/产品任务/"; // 文件保存目录，根据实际情况调整
            String fileUrl1 = OssBootUtil.upload(new Date().toString(), pageImg, fileDir1);

            if (fileUrl1 == null) return "上传失败";

            return fileUrl1;

        } catch (Exception e) {
            e.printStackTrace();
            return "上传失败";
        }
    }


    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "某个产品某一天的所有任务-批量删除")
    @ApiOperation(value = "某个产品某一天的所有任务-批量删除", notes = "某个产品某一天的所有任务-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.taskService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "某个产品某一天的所有任务-通过id查询")
    @ApiOperation(value = "某个产品某一天的所有任务-通过id查询", notes = "某个产品某一天的所有任务-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<Task> queryById(@RequestParam(name = "id", required = true) String id) {
        Task task = taskService.getById(id);
        if (task == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(task);
    }

    /**
     * 通过产品id查询所有任务
     *
     * @param productId
     * @return
     */
    //@AutoLog(value = "某个产品某一天的所有任务-通过id查询")
    @ApiOperation(value = "某个产品某一天的所有任务-通过产品id查询所有任务", notes = "某个产品某一天的所有任务-通过产品id查询所有任务")
    @GetMapping(value = "/queryAllByProId")
    public Result<List<ScheduleVo>> queryAllByProId(@RequestParam(name = "productId", required = true) String productId) {
        List<ScheduleVo> scheduleVos = new ArrayList<>();
        // 根据产品id查询日程
        List<Schedule> scheduleList = scheduleService.list(new LambdaQueryWrapper<Schedule>().eq(Schedule::getProId, productId));
        List<String> ids = scheduleList.stream().map(Schedule::getId).collect(Collectors.toList());
        // 根据日程id查询任务
        ids.forEach(i -> {
            ScheduleVo scheduleVo = new ScheduleVo();
            List<Task> taskList = taskService.list(new LambdaQueryWrapper<Task>().eq(Task::getSchId, i));
            scheduleVo.setTasks(taskList);
            scheduleVos.add(scheduleVo);
        });
        return Result.OK(scheduleVos);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param task
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Task task) {
        return super.exportXls(request, task, Task.class, "某个产品某一天的所有任务");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, Task.class);
    }

}
