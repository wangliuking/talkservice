package com.unionman.springbootsecurityauth2.dto;

import lombok.Data;


/**
 * @description 登录用户传输参数
 * @author Zhifeng.Zeng
 * @date 2019/4/19 14:26
 */
@Data
public class UserDTO {

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

}
