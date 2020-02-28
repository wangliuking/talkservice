package com.unionman.springbootsecurityauth2.service;

import com.unionman.springbootsecurityauth2.entity.Sms;
import com.unionman.springbootsecurityauth2.vo.ResponseVO;

import java.util.Map;

/**
 * @description 短信记录业务接口
 */
public interface SmsService {
    

    ResponseVO<Map<String,Object>> selectSmsList(Map<String, Object> param);

    void deleteSms(int id);
}
