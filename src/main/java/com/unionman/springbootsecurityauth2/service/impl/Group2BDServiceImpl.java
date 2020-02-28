package com.unionman.springbootsecurityauth2.service.impl;


import com.unionman.springbootsecurityauth2.entity.Group2BD;
import com.unionman.springbootsecurityauth2.mapper.Group2BDMapper;
import com.unionman.springbootsecurityauth2.service.Group2BDService;
import com.unionman.springbootsecurityauth2.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Group2BDServiceImpl implements Group2BDService {

    @Autowired
    private Group2BDMapper group2BDMapper;

    @Override
    public void deleteGroup2BD(Map<String, Object> param) {
        group2BDMapper.deleteGroup2BD(param);
    }

    @Override
    public void addGroup2BD(Group2BD group2BD) {
        group2BDMapper.insertGroup2BD(group2BD);
    }

    @Override
    public void updateGroup2BD(Group2BD group2BD) {
        group2BDMapper.updateGroup2BD(group2BD);
    }

    @Override
    public ResponseVO<Map<String, Object>> selectGroup2BDList(Map<String, Object> param) {
        List<Group2BD> list = group2BDMapper.selectGroup2BDList(param);
        int total = group2BDMapper.selectGroup2BDListCount(param);
        Map<String, Object> resultMap = new HashMap<String, Object>() {{
            put("list", list);
            put("total", total);
        }};
        return ResponseVO.success(resultMap);
    }

    @Override
    public ResponseVO<Group2BD> findGroup2BDById(Map<String, Object> param) {
        Group2BD group2BD = group2BDMapper.findGroup2BDById(param);
        return ResponseVO.success(group2BD);
    }

}
