package com.unionman.springbootsecurityauth2.service;


import com.unionman.springbootsecurityauth2.dto.UserDTO;
import com.unionman.springbootsecurityauth2.entity.User;
import com.unionman.springbootsecurityauth2.entity.WebUser;
import com.unionman.springbootsecurityauth2.vo.ResponseVO;
import com.unionman.springbootsecurityauth2.vo.UserVO;

import java.util.List;
import java.util.Map;

/**
 * @description 用户业务接口
 */
public interface UserService {
    /**
     * @description 用户登录
     * @return
     */
    ResponseVO login(UserDTO loginUserDTO);

    Map<String,Object> selectManageUserId(Map<String,Object> param);

    ResponseVO<User> findUserById(String userId);

    ResponseVO<WebUser> findWebUserById(String userId);

    ResponseVO<Map<String,Object>> selectUserList(Map<String, Object> param,String structure);

    ResponseVO<Map<String,Object>> selectWebUserList(Map<String, Object> param,String structure);

    void addUser(User user);

    void deleteUser(String userId);

    void updateUser(User user);

    void addWebUser(WebUser webUser);

    void deleteWebUser(String userId);

    void updateWebUser(WebUser webUser);

    void addUserBatch(Map<String,Object> param);

    void updateUserBatch(Map<String,Object> param);

    void deleteUserBatch(Map<String,Object> param);
}
