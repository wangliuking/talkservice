package com.unionman.springbootsecurityauth2.service;

import com.unionman.springbootsecurityauth2.entity.BD;
import com.unionman.springbootsecurityauth2.vo.ResponseVO;

import java.util.Map;

/**
 * @description 组业务接口
 */
public interface BDService {

    ResponseVO<BD> findBDById(String bdId);

    ResponseVO<Map<String,Object>> selectBDList(Map<String, Object> param);

    void addBD(BD bd);

    void deleteBD(String bdId);

    void updateBD(BD bd);
}
