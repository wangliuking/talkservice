package com.unionman.springbootsecurityauth2.service.impl;

import com.unionman.springbootsecurityauth2.entity.Node;
import com.unionman.springbootsecurityauth2.entity.NodeEchart;
import com.unionman.springbootsecurityauth2.mapper.StructureMapper;
import com.unionman.springbootsecurityauth2.service.StructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StructureServiceImpl implements StructureService {
    @Autowired
    StructureMapper structureMapper;

    public List<Node> selectAll(Map<String,Object> param){
        return structureMapper.selectAll(param);
    }

    public List<NodeEchart> treeEchart(Map<String,Object> param){
        return structureMapper.treeEchart(param);
    }

    public int insert(Node node){
        return structureMapper.insert(node);
    }

    public int delete(Map<String, Object> param){
        return structureMapper.delete(param);
    }

    public int update(Map<String,Object> param){
        return structureMapper.update(param);
    }

    public List<Map<String,Integer>> foreachIdAndPId(){
        return structureMapper.foreachIdAndPId();
    }
}
