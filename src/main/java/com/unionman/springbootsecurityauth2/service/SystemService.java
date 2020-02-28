package com.unionman.springbootsecurityauth2.service;

import com.unionman.springbootsecurityauth2.entity.System;
import com.unionman.springbootsecurityauth2.vo.ResponseVO;

import java.util.Map;

/**
 * @description 系统参数业务接口
 */
public interface SystemService {

    ResponseVO<System> findSystemById(int id);

    ResponseVO<Map<String,Object>> selectSystemList(Map<String, Object> param);

    void updateSystem(System system);
}
