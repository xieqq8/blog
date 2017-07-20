package com.xxx.blog.domain.entity;

import java.util.Date;

public class User {
    // 属性       名称可以和数据库字段名称保持一致
    private Integer contentId;
    // 姓名
    private String username;
    // 性别
    private String sex;
    // 地址
    private String address;
    // 生日
    private Date birthday;

//    public Integer getId() {
//        return contentId;
//    }
//
//    public void setId(Integer id) {
//        this.contentId = id;
//    }

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "User [id=" + contentId + ", username=" + username + ", sex=" + sex
                + ", address=" + address + ", birthday=" + birthday + "]";
    }

}