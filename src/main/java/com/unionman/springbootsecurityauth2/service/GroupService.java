package com.unionman.springbootsecurityauth2.service;

import com.unionman.springbootsecurityauth2.entity.Group;
import com.unionman.springbootsecurityauth2.vo.ResponseVO;

import java.util.Map;

/**
 * @description 组业务接口
 */
public interface GroupService {
    Map<String,Object> selectManageGroupId(Map<String,Object> param);

    ResponseVO<Group> findGroupById(String groupId);

    ResponseVO<Map<String,Object>> selectGroupList(Map<String, Object> param,String structure);

    void addGroup(Group group);

    void deleteGroup(String groupId);

    void updateGroup(Group group);

    void addGroupBatch(Map<String,Object> param);

    void updateGroupBatch(Map<String,Object> param);

    void deleteGroupBatch(Map<String,Object> param);
}
