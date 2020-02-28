package com.unionman.springbootsecurityauth2.entity;

import lombok.Data;
import java.io.Serializable;

@Data
public class User2Group implements Serializable {
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
     * 组id
     */
    private String groupId;

    /**
     * 组名
     */
    private String groupName;

    /**
     * 原用户id
     */
    private String oldUserId;

    /**
     * 原组id
     */
    private String oldGroupId;
}
