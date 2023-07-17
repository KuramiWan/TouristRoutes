package org.jeecg.modules.user.userinfo.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import io.swagger.util.Json;
import net.sf.json.util.JSONUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.user.userinfo.entity.WxClientUserinfo;
import org.jeecg.modules.user.userinfo.service.IWxClientUserinfoService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

/**
 * @Description: 微信客户端用户信息表
 * @Author: jeecg-boot
 * @Date: 2023-07-17
 * @Version: V1.0
 */
@Api(tags = "微信客户端用户信息表")
@RestController
@RequestMapping("/userinfo/wxClientUserinfo")
@Slf4j
public class WxClientUserinfoController extends JeecgController<WxClientUserinfo, IWxClientUserinfoService> {

    @Value("${wx.client.app-id}")
    private String appId;

    @Value("${wx.client.app-secret}")
    private String appSecret;

    @Autowired
    private IWxClientUserinfoService wxClientUserinfoService;

    // 获取用户的openid，并将已有的信息返回给小程序
    @GetMapping("/getOpenId")
    public WxClientUserinfo getOpenId(String code, String username, String avatar) {
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        HashMap map = new HashMap();
        map.put("appid", appId);
        map.put("secret", appSecret);
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");
        String response = HttpUtil.post(url, map);
        JSONObject json = JSONUtil.parseObj(response);
        String openId = json.getStr("openid");
        String sessionKey = json.getStr("session_key");
        if (openId == null || openId.length() == 0)
            throw new RuntimeException("临时登录凭证错误");
        QueryWrapper<WxClientUserinfo> clientUserinfoQueryWrapper = new QueryWrapper<WxClientUserinfo>().eq("openid", openId);
        WxClientUserinfo clientUserinfoServiceOne = wxClientUserinfoService.getOne(clientUserinfoQueryWrapper);
        // 如果是第一次使用随心游,则添加用户
        if (clientUserinfoServiceOne == null) {
            WxClientUserinfo wxClientUserinfo = new WxClientUserinfo();
            wxClientUserinfo.setOpenid(openId);
            wxClientUserinfo.setAvatar(avatar);
            wxClientUserinfo.setUsername(username);
            wxClientUserinfo.setSessionKey(sessionKey);
            wxClientUserinfoService.save(wxClientUserinfo);
            return wxClientUserinfo;
        } else { // 之前使用过随心游,则更新用户信息
            clientUserinfoServiceOne.setUsername(username);
            clientUserinfoServiceOne.setAvatar(avatar);
            clientUserinfoServiceOne.setSessionKey(sessionKey);
            wxClientUserinfoService.updateById(clientUserinfoServiceOne);
            return clientUserinfoServiceOne;
        }
    }

    // 用户绑定手机号
    @GetMapping("/savePhone")
    public String savePhone(String id, String phone) {
        QueryWrapper<WxClientUserinfo> clientUserinfoQueryWrapper = new QueryWrapper<WxClientUserinfo>().eq("id", id);
        WxClientUserinfo clientUserinfoServiceOne = wxClientUserinfoService.getOne(clientUserinfoQueryWrapper);
        clientUserinfoServiceOne.setPhone(phone);
        wxClientUserinfoService.updateById(clientUserinfoServiceOne);
        return "success";
    }

    /**
     * 分页列表查询
     *
     * @param wxClientUserinfo
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "微信客户端用户信息表-分页列表查询")
    @ApiOperation(value = "微信客户端用户信息表-分页列表查询", notes = "微信客户端用户信息表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<WxClientUserinfo>> queryPageList(WxClientUserinfo wxClientUserinfo,
                                                         @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                         @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                         HttpServletRequest req) {
        QueryWrapper<WxClientUserinfo> queryWrapper = QueryGenerator.initQueryWrapper(wxClientUserinfo, req.getParameterMap());
        Page<WxClientUserinfo> page = new Page<WxClientUserinfo>(pageNo, pageSize);
        IPage<WxClientUserinfo> pageList = wxClientUserinfoService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param wxClientUserinfo
     * @return
     */
    @AutoLog(value = "微信客户端用户信息表-添加")
    @ApiOperation(value = "微信客户端用户信息表-添加", notes = "微信客户端用户信息表-添加")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody WxClientUserinfo wxClientUserinfo) {
        wxClientUserinfoService.save(wxClientUserinfo);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param wxClientUserinfo
     * @return
     */
    @AutoLog(value = "微信客户端用户信息表-编辑")
    @ApiOperation(value = "微信客户端用户信息表-编辑", notes = "微信客户端用户信息表-编辑")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody WxClientUserinfo wxClientUserinfo) {
        wxClientUserinfoService.updateById(wxClientUserinfo);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "微信客户端用户信息表-通过id删除")
    @ApiOperation(value = "微信客户端用户信息表-通过id删除", notes = "微信客户端用户信息表-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        wxClientUserinfoService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "微信客户端用户信息表-批量删除")
    @ApiOperation(value = "微信客户端用户信息表-批量删除", notes = "微信客户端用户信息表-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.wxClientUserinfoService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "微信客户端用户信息表-通过id查询")
    @ApiOperation(value = "微信客户端用户信息表-通过id查询", notes = "微信客户端用户信息表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<WxClientUserinfo> queryById(@RequestParam(name = "id", required = true) String id) {
        WxClientUserinfo wxClientUserinfo = wxClientUserinfoService.getById(id);
        if (wxClientUserinfo == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(wxClientUserinfo);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param wxClientUserinfo
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WxClientUserinfo wxClientUserinfo) {
        return super.exportXls(request, wxClientUserinfo, WxClientUserinfo.class, "微信客户端用户信息表");
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
        return super.importExcel(request, response, WxClientUserinfo.class);
    }

}
