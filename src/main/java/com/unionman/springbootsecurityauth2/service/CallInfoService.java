package com.unionman.springbootsecurityauth2.service;

import com.unionman.springbootsecurityauth2.vo.ResponseVO;

import java.util.Map;

/**
 * @description 呼叫信息业务接口
 */
public interface CallInfoService {
    

    ResponseVO<Map<String,Object>> selectCallInfoList(Map<String, Object> param);

    void deleteCallInfo(int id);
}
