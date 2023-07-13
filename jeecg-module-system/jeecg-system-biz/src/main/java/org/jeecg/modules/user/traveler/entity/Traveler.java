package org.jeecg.modules.user.traveler.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * 出行人(Traveler)表实体类
 *
 * @author makejava
 * @since 2023-07-13 15:27:07
 */
@SuppressWarnings("serial")
public class Traveler extends Model<Traveler> {
    //ID
    private Long id;
    //真名
    private String realName;
    //身份证
    private String idCard;
    //电话
    private String phone;
    //生日
    private String birthday;
    //性别
    private String gender;
    //类型
    private String type;
    //用户id
    private String userId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}

