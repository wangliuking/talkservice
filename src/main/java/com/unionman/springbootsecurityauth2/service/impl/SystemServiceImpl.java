package com.unionman.springbootsecurityauth2.service.impl;

import com.unionman.springbootsecurityauth2.entity.System;
import com.unionman.springbootsecurityauth2.mapper.SystemMapper;
import com.unionman.springbootsecurityauth2.service.SystemService;
import com.unionman.springbootsecurityauth2.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    private SystemMapper systemMapper;

    @Override
    public void updateSystem(System system) {
        systemMapper.updateSystem(system);
    }

    @Override
    public ResponseVO<Map<String, Object>> selectSystemList(Map<String, Object> param) {
        List<System> list = systemMapper.selectSystemList(param);
        int total = systemMapper.selectSystemListCount(param);
        Map<String, Object> resultMap = new HashMap<String, Object>() {{
            put("list", list);
            put("total", total);
        }};
        return ResponseVO.success(resultMap);
    }

    @Override
    public ResponseVO<System> findSystemById(int systemId) {
        System System = systemMapper.findSystemById(systemId);
        return ResponseVO.success(System);
    }

}
