package com.unionman.springbootsecurityauth2.service.impl;

import com.unionman.springbootsecurityauth2.entity.BD;
import com.unionman.springbootsecurityauth2.mapper.BDMapper;
import com.unionman.springbootsecurityauth2.service.BDService;
import com.unionman.springbootsecurityauth2.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BDServiceImpl implements BDService {

    @Autowired
    private BDMapper bdMapper;

    @Override
    public void deleteBD(String bdId) {
        bdMapper.deleteBD(bdId);
    }

    @Override
    public void addBD(BD bd) {
        bdMapper.insertBD(bd);
    }

    @Override
    public void updateBD(BD bd) {
        bdMapper.updateBD(bd);
    }

    @Override
    public ResponseVO<Map<String, Object>> selectBDList(Map<String, Object> param) {
        List<BD> list = bdMapper.selectBDList(param);
        int total = bdMapper.selectBDListCount(param);
        Map<String, Object> resultMap = new HashMap<String, Object>() {{
            put("list", list);
            put("total", total);
        }};
        return ResponseVO.success(resultMap);
    }

    @Override
    public ResponseVO<BD> findBDById(String bdId) {
        BD bd = bdMapper.findBDById(bdId);
        return ResponseVO.success(bd);
    }

}
