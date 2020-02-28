package com.unionman.springbootsecurityauth2.service;


import com.unionman.springbootsecurityauth2.dto.UserDTO;
import com.unionman.springbootsecurityauth2.entity.User;
import com.unionman.springbootsecurityauth2.entity.User2Group;
import com.unionman.springbootsecurityauth2.vo.ResponseVO;

import java.util.List;
import java.util.Map;

/**
 * @description 用户和组关联业务接口
 */
public interface User2GroupService {

    ResponseVO<User2Group> findUser2GroupById(Map<String, Object> param);

    ResponseVO<Map<String,Object>> selectUser2GroupList(Map<String, Object> param);

    void addUser2Group(User2Group user2Group);

    void deleteUser2Group(Map<String, Object> param);

    void updateUser2Group(User2Group user2Group);

    void addUser2GroupBatch(Map<String,Object> param);

    void deleteUser2GroupBatch(Map<String,Object> param);

    Map<String,Object> selectUser2GroupId();
}
