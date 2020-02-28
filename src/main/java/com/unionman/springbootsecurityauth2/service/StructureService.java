package com.unionman.springbootsecurityauth2.service;

import com.unionman.springbootsecurityauth2.entity.Node;
import com.unionman.springbootsecurityauth2.entity.NodeEchart;

import java.util.List;
import java.util.Map;

public interface StructureService {

    List<Node> selectAll(Map<String,Object> param);

    List<NodeEchart> treeEchart(Map<String,Object> param);

    int insert(Node node);

    int delete(Map<String, Object> param);

    int update(Map<String,Object> param);

    List<Map<String,Integer>> foreachIdAndPId();
}
