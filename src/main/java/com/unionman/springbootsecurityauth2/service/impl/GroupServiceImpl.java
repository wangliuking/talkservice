package com.unionman.springbootsecurityauth2.service.impl;

import com.unionman.springbootsecurityauth2.controller.TreeUtil;
import com.unionman.springbootsecurityauth2.entity.Group;
import com.unionman.springbootsecurityauth2.entity.Node;
import com.unionman.springbootsecurityauth2.mapper.GroupMapper;
import com.unionman.springbootsecurityauth2.service.GroupService;
import com.unionman.springbootsecurityauth2.service.StructureService;
import com.unionman.springbootsecurityauth2.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private StructureService structureService;

    @Override
    public void deleteGroup(String groupId) {
        groupMapper.deleteGroup(groupId);
    }

    @Override
    public void addGroup(Group group) {
        groupMapper.insertGroup(group);
    }

    @Override
    public void updateGroup(Group group) {
        groupMapper.updateGroup(group);
    }

    @Override
    public ResponseVO<Map<String, Object>> selectGroupList(Map<String, Object> param,String structure) {
        List<Group> list = groupMapper.selectGroupList(param);
        int total = groupMapper.selectGroupListCount(param);

        List<Node> nodeList = structureService.selectAll(param);
        TreeUtil treeUtil = new TreeUtil();
        treeUtil.getInfiniteLevelTree(nodeList, Integer.parseInt(structure));

        for(Group group : list){
            for(Node node : nodeList){
                if(group.getPower() == node.getId()){
                    group.setPowerName(node.getFullName());
                }
            }
        }

        Map<String, Object> resultMap = new HashMap<String, Object>() {{
            put("list", list);
            put("total", total);
        }};
        return ResponseVO.success(resultMap);
    }

    @Override
    public Map<String,Object> selectManageGroupId(Map<String,Object> param){
        Map<String,Object> map = new HashMap<>();
        List<String> list = groupMapper.selectManageGroupId(param);
        for(String key : list){
            map.put(key,"");
        }
        return map;
    }

    @Override
    public ResponseVO<Group> findGroupById(String groupId) {
        Group group = groupMapper.findGroupById(groupId);
        return ResponseVO.success(group);
    }

    @Override
    public void addGroupBatch(Map<String,Object> param) {
        groupMapper.insertGroupBatch(param);
    }

    @Override
    public void updateGroupBatch(Map<String,Object> param) {
        groupMapper.updateGroupBatch(param);
    }

    @Override
    public void deleteGroupBatch(Map<String,Object> param) {
        groupMapper.deleteGroupBatch(param);
    }

}
