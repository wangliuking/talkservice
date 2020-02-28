package com.unionman.springbootsecurityauth2.controller;

import com.unionman.springbootsecurityauth2.entity.Node;
import com.unionman.springbootsecurityauth2.entity.NodeEchart;
import com.unionman.springbootsecurityauth2.service.StructureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
@Validated
@RestController
@RequestMapping("/structure/")
public class StructureController {
    @Autowired
    private StructureService structureService;

    @RequestMapping(value = "/treeEchart")
    public List<NodeEchart> treeEchart (HttpServletRequest req){
        String structure = req.getParameter("structure");
        List<Integer> strList = foreachIdAndPIdForConnection(Integer.parseInt(structure));
        System.out.println("strList : ++++++++++++"+strList);
        Map<String,Object> param = new HashMap<>();
        param.put("strList",strList);

        List<NodeEchart> nodeEchartList = structureService.treeEchart(param);
        TreeEchartUtil treeEchartUtil = new TreeEchartUtil();
        List<NodeEchart> resultList = treeEchartUtil.getInfiniteLevelTree(nodeEchartList, Integer.parseInt(structure));
        return resultList;

    }

    @RequestMapping(value = "/selectAllStructure")
    public List<Node> selectAllStructure (HttpServletRequest req){
        String structure = req.getParameter("structure");
        List<Integer> strList = foreachIdAndPIdForConnection(Integer.parseInt(structure));
        System.out.println("strList : ++++++++++++"+strList);
        Map<String,Object> param = new HashMap<>();
        param.put("strList",strList);

        List<Node> nodeList = structureService.selectAll(param);
        TreeUtil treeUtil = new TreeUtil();
        List<Node> resultList = treeUtil.getInfiniteLevelTree(nodeList, Integer.parseInt(structure));
        return resultList;

    }

    @RequestMapping(value = "/selectStructureList")
    public Map<String,Object> selectStructureList (HttpServletRequest req){
        Map<String,Object> param = new HashMap<>();
        String structure = req.getParameter("structure");
        List<Integer> strList = foreachIdAndPIdForConnection(Integer.parseInt(structure));
        System.out.println("strList : ++++++++++++"+strList);
        param.put("strList",strList);

        List<Node> nodeList = structureService.selectAll(param);
        TreeUtil treeUtil = new TreeUtil();
        treeUtil.getInfiniteLevelTree(nodeList, Integer.parseInt(structure));

        Map<String,Object> finalMap = new HashMap<>();
        finalMap.put("nodeList",nodeList);
        return finalMap;
    }

    @RequestMapping(value = "/insertStructure")
    public Map<String, Object> insertStructure (@RequestBody Node node){
        Map<String,Object> params = new HashMap<>();
        System.out.println("node : "+node);
        int result = structureService.insert(node);
        Map<String,Object> map = new HashMap<>();
        if(result>0){
            map.put("success",true);
            map.put("message","成功添加了一个单位");
        }else {
            map.put("success",false);
            map.put("message","添加单位失败");
        }
        return map;
    }

    @RequestMapping(value = "/deleteStructure")
    public Map<String, Object> deleteStructure(HttpServletRequest req, HttpServletResponse resp){
        int id = Integer.parseInt(req.getParameter("id"));

        List<Integer> strList = foreachIdAndPIdForConnection(id);
        System.out.println("strList : ++++++++++++"+strList);
        Map<String,Object> param = new HashMap<>();
        param.put("strList",strList);

        int res = structureService.delete(param);
        Map<String,Object> map = new HashMap<>();
        if(res>0){
            map.put("success",true);
            map.put("message","成功删除了该单位");
        }else {
            map.put("success",false);
            map.put("message","删除单位失败");
        }
        return map;
    }

    @PostMapping(value = "/updateStructure")
    public Map<String, Object> updateStructure(@Valid @RequestBody Node node){
        Map<String,Object> param = new HashMap<>();
        param.put("id",node.getId());
        param.put("label",node.getLabel());
        int result = structureService.update(param);
        Map<String,Object> map = new HashMap<>();
        if(result>0){
            map.put("success",true);
            map.put("message","成功更新了该单位");
        }else {
            map.put("success",false);
            map.put("message","更新该单位失败");
        }
        return map;
    }

    @RequestMapping(value = "/foreachIdAndPId")
    public List<Integer> foreachIdAndPId (HttpServletRequest req){
        List<Map<String,Integer>> list = structureService.foreachIdAndPId();
        int id = Integer.parseInt(req.getParameter("id"));
        List<Integer> finalList = new LinkedList<>();
        List<Integer> tempList = new LinkedList<>();
        tempList.add(id);
        while(tempList.size()>0){
            List<Integer> otherList = new LinkedList<>();
            for(int i=0;i<tempList.size();i++){
                finalList.add(tempList.get(i));
                List<Integer> tList = getChildId(tempList.get(i),list);
                if(tList.size()>0){
                    for(int j=0;j<tList.size();j++){
                        otherList.add(tList.get(j));
                    }
                }
            }
            tempList.clear();
            for(int i=0;i<otherList.size();i++){
                tempList.add(otherList.get(i));
            }
        }
        return finalList;
    }

    public List<Integer> foreachIdAndPIdForConnection (int id){
        List<Map<String,Integer>> list = structureService.foreachIdAndPId();
        List<Integer> finalList = new LinkedList<>();
        List<Integer> tempList = new LinkedList<>();
        tempList.add(id);
        while(tempList.size()>0){
            List<Integer> otherList = new LinkedList<>();
            for(int i=0;i<tempList.size();i++){
                finalList.add(tempList.get(i));
                List<Integer> tList = getChildId(tempList.get(i),list);
                if(tList.size()>0){
                    for(int j=0;j<tList.size();j++){
                        otherList.add(tList.get(j));
                    }
                }
            }
            tempList.clear();
            for(int i=0;i<otherList.size();i++){
                tempList.add(otherList.get(i));
            }
        }
        return finalList;
    }

    public List<Integer> getChildId(int id, List<Map<String,Integer>> list){
        List<Integer> returnlist = new LinkedList<>();
        for(int i=0;i<list.size();i++){
            if(list.get(i).get("pId") == id){
                returnlist.add(list.get(i).get("id"));
            }
        }
        return returnlist;
    }
}
