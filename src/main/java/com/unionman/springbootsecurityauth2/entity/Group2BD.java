package com.unionman.springbootsecurityauth2.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Group2BD implements Serializable {
    private static final long serialVersionUID = -8478114427891717226L;

    /**
     * 局向码
     */
    private String bdId;

    /**
     * 局向名
     */
    private String bdName;

    /**
     * 组id
     */
    private String groupId;

    /**
     * 组名
     */
    private String groupName;

    /**
     * 原局向码
     */
    private String oldBdId;

    /**
     * 原组id
     */
    private String oldGroupId;
}
