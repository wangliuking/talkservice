package com.unionman.springbootsecurityauth2.service.impl;

import com.unionman.springbootsecurityauth2.mapper.UserBookMapper;
import com.unionman.springbootsecurityauth2.service.UserBookService;
import com.unionman.springbootsecurityauth2.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class UserBookServiceImpl implements UserBookService {
    @Autowired
    private UserBookMapper userBookMapper;

    @Override
    public ResponseVO<Map<String, Object>> findUserBookById(int id) {
        Map<String,Object> map = userBookMapper.findUserBookById(id);
        return ResponseVO.success(map);
    }

    @Override
    public ResponseVO<Map<String, Object>> selectUserBookList(Map<String, Object> param){
        List<Map<String,Object>> list = userBookMapper.selectUserBookList(param);
        int total = userBookMapper.selectUserBookListCount(param);
        for(Map<String,Object> map : list){
            String id = map.get("id")+"";
            List<Map<String,Object>> userBookDetail = userBookMapper.selectUserBookDetail(Integer.parseInt(id));
            map.put("userBookDetail",userBookDetail);
        }
        Map<String, Object> resultMap = new HashMap<String, Object>() {{
            put("list", list);
            put("total", total);
        }};
        return ResponseVO.success(resultMap);
    }

    @Override
    public void insertUserBook(Map<String, Object> param) {
        userBookMapper.insertUserBook(param);
    }

    @Override
    public void updateUserBook(Map<String, Object> param) {
        userBookMapper.updateUserBook(param);
    }

    @Override
    public void deleteUserBook(int id) {
        userBookMapper.deleteUserBook(id);
    }

    @Override
    public ResponseVO<Map<String, Object>> selectUserBookDetail(int id) {
        List<Map<String,Object>> list = userBookMapper.selectUserBookDetail(id);
        Map<String, Object> resultMap = new HashMap<String, Object>() {{
            put("list", list);
        }};
        return ResponseVO.success(resultMap);
    }

    @Override
    public void deleteUserBookDetail(int id) {
        userBookMapper.deleteUserBookDetail(id);
    }

    @Override
    public void insertUserBookDetail(Map<String, Object> param) {
        userBookMapper.insertUserBookDetail(param);
    }

}
