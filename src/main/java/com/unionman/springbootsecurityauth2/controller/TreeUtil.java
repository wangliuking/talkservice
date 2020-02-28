package com.unionman.springbootsecurityauth2.controller;

import com.unionman.springbootsecurityauth2.entity.Node;

import java.util.ArrayList;
import java.util.List;

public class TreeUtil {

    // 入口方法
    public List<Node> getInfiniteLevelTree(List<Node> nodeList, int id) {
        List<Node> list = new ArrayList<>();
        // 遍历节点列表
        for (Node node : nodeList) {
            if (node.getId() == id) {
                // 入口
                node.setFullName(node.getLabel());
                node.setChildren(getChildrenNode(node.getId(), node.getFullName(), nodeList));
                list.add(node);
            }
        }
        // 排序
        //list.sort(new NodeOrderComparator());
        return list;
    }

    // 获取子节点的递归方法
    public List<Node> getChildrenNode(int id, String fullName, List<Node> nodeList) {
        List<Node> lists = new ArrayList<>();
        for (Node node : nodeList) {
            if (node.getPId() == id) {
                // 递归获取子节点
                node.setFullName(fullName + "-" + node.getLabel());
                node.setChildren(getChildrenNode(node.getId(), node.getFullName(), nodeList));
                lists.add(node);
            }
        }
        // 排序
        //lists.sort(new NodeOrderComparator());
        return lists;
    }
}
