package com.unionman.springbootsecurityauth2.service.impl;

import com.unionman.springbootsecurityauth2.entity.CallInfo;
import com.unionman.springbootsecurityauth2.mapper.CallInfoMapper;
import com.unionman.springbootsecurityauth2.service.CallInfoService;
import com.unionman.springbootsecurityauth2.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CallInfoServiceImpl implements CallInfoService {

    @Autowired
    private CallInfoMapper callInfoMapper;

    @Override
    public void deleteCallInfo(int id) {
        callInfoMapper.deleteCallInfo(id);
    }

    @Override
    public ResponseVO<Map<String, Object>> selectCallInfoList(Map<String, Object> param) {
        List<CallInfo> list = callInfoMapper.selectCallInfoList(param);
        int total = callInfoMapper.selectCallInfoListCount(param);
        Map<String, Object> resultMap = new HashMap<String, Object>() {{
            put("list", list);
            put("total", total);
        }};
        return ResponseVO.success(resultMap);
    }

}
