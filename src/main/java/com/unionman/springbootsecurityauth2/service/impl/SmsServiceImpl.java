package com.unionman.springbootsecurityauth2.service.impl;

import com.unionman.springbootsecurityauth2.entity.Sms;
import com.unionman.springbootsecurityauth2.mapper.SmsMapper;
import com.unionman.springbootsecurityauth2.service.SmsService;
import com.unionman.springbootsecurityauth2.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    private SmsMapper smsMapper;

    @Override
    public void deleteSms(int id) {
        smsMapper.deleteSms(id);
    }

    @Override
    public ResponseVO<Map<String, Object>> selectSmsList(Map<String, Object> param) {
        List<Sms> list = smsMapper.selectSmsList(param);
        int total = smsMapper.selectSmsListCount(param);
        Map<String, Object> resultMap = new HashMap<String, Object>() {{
            put("list", list);
            put("total", total);
        }};
        return ResponseVO.success(resultMap);
    }

}
