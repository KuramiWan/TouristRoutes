package org.jeecg.modules.user.userinfo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.modules.user.userinfo.entity.WxClientUserinfo;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @author QiaoQi
 * @version 1.0
 */
@Data
public class WxClientUserinfoVo extends WxClientUserinfo {
    /**用户token*/
    private String token;
}
