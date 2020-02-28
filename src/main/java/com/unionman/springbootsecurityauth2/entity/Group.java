package com.unionman.springbootsecurityauth2.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Group implements Serializable {
    private static final long serialVersionUID = -8478114427891717226L;

    /**
     * 组id
     */
    private String groupId;

    /**
     * 组名
     */
    private String groupName;

    /**
     * 类型
     */
    private int type;

    /**
     * PTT静默时长
     */
    private int pttSilentTime;

    /**
     * 一次呼叫总得通话时间限定
     */
    private int callTime;

    /**
     * 单次PTT授权最长时间
     */
    private int pttOnTime;

    /**
     * 组权限
     */
    private int power;

    /**
     * 权限名称
     */
    private String powerName;

    /**
     * 原组id
     */
    private String oldGroupId;
}
