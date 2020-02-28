package com.unionman.springbootsecurityauth2.controller;

import com.unionman.springbootsecurityauth2.entity.NodeEchart;

import java.util.ArrayList;
import java.util.List;

public class TreeEchartUtil {
    public static void main(String[] args) {
        String s = "测试";
        String[] arr = s.split("");
        String f = "";
        for(String str : arr){
            f = f + str +'\n';
        }
        System.out.println(f);
    }

    public String changeWord(String str){
        /*String[] arr = str.split("");
        String res = "";
        for(String s : arr){
            res = res + s +'\n';
        }
        return res;*/
        return str;
    }

    // 入口方法
    public List<NodeEchart> getInfiniteLevelTree(List<NodeEchart> NodeEchartList, int id) {
        List<NodeEchart> list = new ArrayList<>();
        // 遍历节点列表
        for (NodeEchart NodeEchart : NodeEchartList) {
            if (NodeEchart.getId() == id) {
                // 入口
                String name = NodeEchart.getName();
                NodeEchart.setName(changeWord(name));
                NodeEchart.setChildren(getChildrenNodeEchart(NodeEchart.getId(), NodeEchartList));
                list.add(NodeEchart);
            }
        }
        // 排序
        //list.sort(new NodeEchartOrderComparator());
        return list;
    }

    // 获取子节点的递归方法
    public List<NodeEchart> getChildrenNodeEchart(int id, List<NodeEchart> NodeEchartList) {
        List<NodeEchart> lists = new ArrayList<>();
        for (NodeEchart NodeEchart : NodeEchartList) {
            if (NodeEchart.getPId() == id) {
                // 递归获取子节点
                String name = NodeEchart.getName();
                NodeEchart.setName(changeWord(name));
                NodeEchart.setChildren(getChildrenNodeEchart(NodeEchart.getId(), NodeEchartList));
                lists.add(NodeEchart);
            }
        }
        // 排序
        //lists.sort(new NodeEchartOrderComparator());
        return lists;
    }
}
