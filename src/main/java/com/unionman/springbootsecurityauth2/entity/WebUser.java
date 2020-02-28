package com.unionman.springbootsecurityauth2.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class WebUser implements Serializable {
    private static final long serialVersionUID = -8478114427891717226L;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 最近登录时间
     */
    private String loginTime;

    /**
     * 权限
     */
    private int power;

    /**
     *
     */
    private String powerName;

    /**
     * 账号类型 0 超级管理员 1 普通用户
     */
    private int type;

    /**
     * 原用户id
     */
    private String oldUserId;


}
