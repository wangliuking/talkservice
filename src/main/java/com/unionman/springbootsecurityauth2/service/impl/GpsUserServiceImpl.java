package com.unionman.springbootsecurityauth2.service.impl;

import com.unionman.springbootsecurityauth2.entity.GpsUser;
import com.unionman.springbootsecurityauth2.mapper.GpsUserMapper;
import com.unionman.springbootsecurityauth2.service.GpsUserService;
import com.unionman.springbootsecurityauth2.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GpsUserServiceImpl implements GpsUserService {

    @Autowired
    private GpsUserMapper gpsUserMapper;

    @Override
    public void deleteGpsUser(int gpsUserId) {
        gpsUserMapper.deleteGpsUser(gpsUserId);
    }

    @Override
    public void addGpsUser(GpsUser gpsUser) {
        gpsUserMapper.insertGpsUser(gpsUser);
    }

    @Override
    public void updateGpsUser(GpsUser gpsUser) {
        gpsUserMapper.updateGpsUser(gpsUser);
    }

    @Override
    public ResponseVO<Map<String, Object>> selectGpsUserList(Map<String, Object> param) {
        List<GpsUser> list = gpsUserMapper.selectGpsUserList(param);
        int total = gpsUserMapper.selectGpsUserListCount(param);
        Map<String, Object> resultMap = new HashMap<String, Object>() {{
            put("list", list);
            put("total", total);
        }};
        return ResponseVO.success(resultMap);
    }

    @Override
    public ResponseVO<GpsUser> findGpsUserById(int gpsUserId) {
        GpsUser GpsUser = gpsUserMapper.findGpsUserById(gpsUserId);
        return ResponseVO.success(GpsUser);
    }

}
