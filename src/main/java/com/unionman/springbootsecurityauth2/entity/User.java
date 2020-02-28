package com.unionman.springbootsecurityauth2.entity;

import lombok.Data;
import java.io.Serializable;

@Data
public class User implements Serializable {
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
     * 鉴权码
     */
    private String authenticateCode;

    /**
     * 优先级
     */
    private int priority;

    /**
     * 用户类型
     */
    private int type;

    /**
     * 最近登录时间
     */
    private String loginTime;

    /**
     * 最近退出时间
     */
    private String logoutTime;

    /**
     * 登录状态
     */
    private int loginStatus;

    /**
     * 扫描使能
     */
    private int scanEn;

    /**
     * 当前附着组
     */
    private String attachGroup;

    /**
     * GPS上传时间间隔
     */
    private int gpsInterval;

    /**
     * 外接视频查看权限
     */
    private int externalVideoViewEn;

    /**
     * 用户权限
     */
    private int power;

    /**
     * 权限名称
     */
    private String powerName;

    /**
     * 通讯录id
     */
    private int userBook;

    /**
     * 原用户id
     */
    private String oldUserId;


}
