package com.unionman.springbootsecurityauth2.service.impl;


import com.unionman.springbootsecurityauth2.entity.User2Group;
import com.unionman.springbootsecurityauth2.mapper.User2GroupMapper;
import com.unionman.springbootsecurityauth2.service.User2GroupService;
import com.unionman.springbootsecurityauth2.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class User2GroupServiceImpl implements User2GroupService {

    @Autowired
    private User2GroupMapper user2GroupMapper;

    @Override
    public void deleteUser2Group(Map<String, Object> param) {
        user2GroupMapper.deleteUser2Group(param);
    }

    @Override
    public void addUser2Group(User2Group user2Group) {
        user2GroupMapper.insertUser2Group(user2Group);
    }

    @Override
    public void updateUser2Group(User2Group user2Group) {
        user2GroupMapper.updateUser2Group(user2Group);
    }

    @Override
    public ResponseVO<Map<String, Object>> selectUser2GroupList(Map<String, Object> param) {
        List<User2Group> list = user2GroupMapper.selectUser2GroupList(param);
        int total = user2GroupMapper.selectUser2GroupListCount(param);
        Map<String, Object> resultMap = new HashMap<String, Object>() {{
            put("list", list);
            put("total", total);
        }};
        return ResponseVO.success(resultMap);
    }

    @Override
    public ResponseVO<User2Group> findUser2GroupById(Map<String, Object> param) {
        User2Group user2Group = user2GroupMapper.findUser2GroupById(param);
        return ResponseVO.success(user2Group);
    }

    @Override
    public void addUser2GroupBatch(Map<String, Object> param) {
        user2GroupMapper.insertUser2GroupBatch(param);
    }

    @Override
    public void deleteUser2GroupBatch(Map<String, Object> param) {
        user2GroupMapper.deleteUser2GroupBatch(param);
    }

    @Override
    public Map<String,Object> selectUser2GroupId() {
        Map<String,Object> map = new HashMap<>();
        List<User2Group> list = user2GroupMapper.selectUser2GroupId();
        for(User2Group user2Group : list){
            String userId = user2Group.getUserId();
            String groupId = user2Group.getGroupId();
            map.put(userId+groupId,"");
        }
        return map;
    }

}
