package com.unionman.springbootsecurityauth2.service;

import com.unionman.springbootsecurityauth2.entity.GpsUser;
import com.unionman.springbootsecurityauth2.vo.ResponseVO;

import java.util.Map;

/**
 * @description GPS用户业务接口
 */
public interface GpsUserService {

    ResponseVO<GpsUser> findGpsUserById(int gpsUserId);

    ResponseVO<Map<String,Object>> selectGpsUserList(Map<String, Object> param);

    void addGpsUser(GpsUser gpsUser);

    void deleteGpsUser(int GpsUserId);

    void updateGpsUser(GpsUser gpsUser);
}
