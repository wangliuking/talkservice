package com.unionman.springbootsecurityauth2.service;

import com.unionman.springbootsecurityauth2.entity.Group2BD;
import com.unionman.springbootsecurityauth2.vo.ResponseVO;

import java.util.Map;

/**
 * @description 组和局向业务接口
 */
public interface Group2BDService {

    ResponseVO<Group2BD> findGroup2BDById(Map<String, Object> param);

    ResponseVO<Map<String,Object>> selectGroup2BDList(Map<String, Object> param);

    void addGroup2BD(Group2BD group2BD);

    void deleteGroup2BD(Map<String, Object> param);

    void updateGroup2BD(Group2BD group2BD);
}
