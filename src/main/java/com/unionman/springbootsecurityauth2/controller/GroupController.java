package com.unionman.springbootsecurityauth2.controller;

import com.unionman.springbootsecurityauth2.entity.Group;
import com.unionman.springbootsecurityauth2.mapper.Group2BDMapper;
import com.unionman.springbootsecurityauth2.mapper.User2GroupMapper;
import com.unionman.springbootsecurityauth2.service.GroupService;
import com.unionman.springbootsecurityauth2.utils.UDPTest;
import com.unionman.springbootsecurityauth2.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

/**
 * @description 组管理
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/group/")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private StructureController structureController;

    @Autowired
    private User2GroupMapper user2GroupMapper;

    @Autowired
    private Group2BDMapper group2BDMapper;

    /**
     * @param req
     * @return
     * @description 删除组
     */
    @GetMapping("deleteGroup")
    public ResponseVO deleteGroup(HttpServletRequest req) throws Exception {
        String groupId = req.getParameter("groupId");
        System.out.println("**********************************");
        System.out.println(groupId);
        System.out.println("**********************************");
        //发送UDP
        delUDP(groupId);
        groupService.deleteGroup(groupId);
        return ResponseVO.success();
    }

    public void delUDP(String groupId){
        //查询是否有组和局向关联
        int count1 = group2BDMapper.findIsExistGroupId(groupId);
        if(count1 > 0){
            UDPTest.group2bdUDP("",groupId,3);
        }
        //查询是否有用户和组关联
        int count2 = user2GroupMapper.findIsExistGroupId(groupId);
        if(count2 > 0){
            UDPTest.user2groupUDP("",groupId,3);
        }
        UDPTest.groupUDP(groupId,3);
    }

    /**
     * @param group
     * @return
     * @descripiton 修改用户
     */
    @PostMapping("updateGroup")
    public ResponseVO updateGroup(@Valid @RequestBody Group group) {
        System.out.println("**********************************");
        System.out.println(group);
        System.out.println("**********************************");
        groupService.updateGroup(group);
        //发送UDP
        UDPTest.groupUDP(group.getGroupId(),2);
        return ResponseVO.success();
    }

    /**
     * @param group
     * @return
     * @description 添加组
     */
    @PostMapping("addGroup")
    public ResponseVO addGroup(@Valid @RequestBody Group group) {
        /*System.out.println("**********************************");
        System.out.println(group);
        System.out.println("**********************************");*/
        groupService.addGroup(group);
        //发送UDP
        UDPTest.groupUDP(group.getGroupId(),1);
        return ResponseVO.success();
    }

    /**
     * @return
     * @description 获取指定组信息
     */
    @GetMapping("checkGroupId")
    public ResponseVO checkGroupId(HttpServletRequest req) {
        String groupId = req.getParameter("groupId");
        return groupService.findGroupById(groupId);
    }

    /**
     * @return
     * @description 获取组列表
     */
    @GetMapping("groupList")
    public ResponseVO findAllGroup(HttpServletRequest req) {
        String start = req.getParameter("start");
        String limit = req.getParameter("limit");
        String groupId = req.getParameter("groupId");
        String groupName = req.getParameter("groupName");
        String type = req.getParameter("type");
        String structure = req.getParameter("structure");
        List<Integer> strList = structureController.foreachIdAndPIdForConnection(Integer.parseInt(structure));
        Map<String, Object> map = new HashMap<String, Object>() {{
            put("start", "".equals(start) ? -1 : Integer.parseInt(start));
            put("limit", "".equals(limit) ? -1 : Integer.parseInt(limit));
            put("groupId", groupId);
            put("groupName", groupName);
            put("type", "".equals(type) ? -1 : Integer.parseInt(type));
            put("strList", strList);
        }};
        return groupService.selectGroupList(map,structure);
    }

    /**
     * 查询组织结构下所有可管理组
     */
    public Map<String, Object> getManageGroup(int power) {
        Map<String, Object> param = new HashMap<>();
        if (power == -1) {
            param.put("strList", null);
            param.put("power", -1);
        } else {
            List<Integer> strList = structureController.foreachIdAndPIdForConnection(power);
            param.put("power", power);
            param.put("strList", strList);
        }
        return groupService.selectManageGroupId(param);
    }


    /**
     * @param group
     * @return
     * @description 批量添加组
     */
    @PostMapping("addGroupPatch")
    public ResponseVO addGroupPatch(@Valid @RequestBody Group group) {
        System.out.println("**********************************");
        System.out.println(group);
        System.out.println("**********************************");
        Map<String, Object> existGroupIdMap = getManageGroup(-1);
        Map<String, Object> param = new HashMap<String, Object>() {{
            put("groupName", group.getGroupName());
            put("type", group.getType());
            put("pttSilentTime", group.getPttSilentTime());
            put("callTime", group.getCallTime());
            put("pttOnTime", group.getPttOnTime());
            put("power", group.getPower());
        }};
        String groupIds = group.getGroupId();
        List<Map<String, Object>> list = new ArrayList<>();
        if (groupIds.contains(",")) {
            List<String> groupIdList = Arrays.asList(groupIds.split(","));
            for (String groupId : groupIdList) {
                //若已存在相同组id，则不加入
                if (!existGroupIdMap.containsKey(groupId)) {
                    Map<String, Object> map = new HashMap<String, Object>() {{
                        put("groupId", groupId);
                        put("groupName", group.getGroupName());
                        put("type", group.getType());
                        put("pttSilentTime", group.getPttSilentTime());
                        put("callTime", group.getCallTime());
                        put("pttOnTime", group.getPttOnTime());
                        put("power", group.getPower());
                    }};
                    list.add(map);
                }
            }
            param.put("list", list);
            System.out.println(param);
            groupService.addGroupBatch(param);
            //发送UDP
            for(Map<String,Object> m : list){
                String groupId = m.get("groupId")+"";
                UDPTest.groupUDP(groupId,1);
            }
        } else if (groupIds.contains("-")) {
            String[] arr = groupIds.split("-");
            int start = Integer.parseInt(arr[0]);
            int end = Integer.parseInt(arr[1]);
            for (int i = start; i <= end; i++) {
                if (!existGroupIdMap.containsKey(i + "")) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("groupId", i + "");
                    map.put("groupName", group.getGroupName());
                    map.put("type", group.getType());
                    map.put("pttSilentTime", group.getPttSilentTime());
                    map.put("callTime", group.getCallTime());
                    map.put("pttOnTime", group.getPttOnTime());
                    map.put("power", group.getPower());
                    list.add(map);
                }
            }
            param.put("list", list);
            System.out.println(param);
            groupService.addGroupBatch(param);
            //发送UDP
            for(Map<String,Object> m : list){
                String groupId = m.get("groupId")+"";
                UDPTest.groupUDP(groupId,1);
            }
        }
        return ResponseVO.success();
    }

    /**
     * @param group
     * @return
     * @description 批量修改组
     */
    @PostMapping("updateGroupBatch")
    public ResponseVO updateGroupBatch(@Valid @RequestBody Group group) {
        System.out.println("**********************************");
        System.out.println(group);
        System.out.println("**********************************");
        Map<String, Object> existGroupIdMap = getManageGroup(Integer.parseInt(group.getOldGroupId()));
        Map<String, Object> param = new HashMap<String, Object>() {{
            put("groupName", group.getGroupName());
            put("type", group.getType());
            put("pttSilentTime", group.getPttSilentTime());
            put("callTime", group.getCallTime());
            put("pttOnTime", group.getPttOnTime());
            put("power", group.getPower());
        }};
        String groupIds = group.getGroupId();
        if (groupIds.contains(",")) {
            List<String> groupIdList = new ArrayList<>();
            List<String> tempList = Arrays.asList(groupIds.split(","));
            for (String s : tempList) {
                //可管理组才能进行下一步操作
                if (existGroupIdMap.containsKey(s)) {
                    groupIdList.add(s);
                }
            }
            param.put("list", groupIdList);
            System.out.println(param);
            groupService.updateGroupBatch(param);
            //发送UDP
            for(String s : groupIdList){
                UDPTest.groupUDP(s,2);
            }
        } else if (groupIds.contains("-")) {
            List<String> list = new ArrayList<>();
            String[] arr = groupIds.split("-");
            int start = Integer.parseInt(arr[0]);
            int end = Integer.parseInt(arr[1]);
            for (int i = start; i <= end; i++) {
                //可管理组才能进行下一步操作
                if (existGroupIdMap.containsKey(i + "")) {
                    list.add(i + "");
                }
            }
            param.put("list", list);
            System.out.println(param);
            groupService.updateGroupBatch(param);
            //发送UDP
            for(String s : list){
                UDPTest.groupUDP(s,2);
            }
        }
        return ResponseVO.success();
    }

    /**
     * @param group
     * @return
     * @description 批量删除组
     */
    @PostMapping("deleteGroupBatch")
    public ResponseVO deleteGroupBatch(@Valid @RequestBody Group group) {
        System.out.println("**********************************");
        System.out.println(group);
        System.out.println("**********************************");
        Map<String, Object> existGroupIdMap = getManageGroup(Integer.parseInt(group.getOldGroupId()));
        Map<String, Object> param = new HashMap<>();
        String groupIds = group.getGroupId();
        if (groupIds.contains(",")) {
            List<String> groupIdList = new ArrayList<>();
            List<String> tempList = Arrays.asList(groupIds.split(","));
            for (String s : tempList) {
                //可管理组才能进行下一步操作
                if (existGroupIdMap.containsKey(s)) {
                    groupIdList.add(s);
                }
            }
            param.put("list", groupIdList);
            System.out.println(param);
            //发送UDP
            for(String s : groupIdList){
                delUDP(s);
            }
            groupService.deleteGroupBatch(param);
        } else if (groupIds.contains("-")) {
            List<String> list = new ArrayList<>();
            String[] arr = groupIds.split("-");
            int start = Integer.parseInt(arr[0]);
            int end = Integer.parseInt(arr[1]);
            for (int i = start; i <= end; i++) {
                //可管理组才能进行下一步操作
                if (existGroupIdMap.containsKey(i + "")) {
                    list.add(i + "");
                }
            }
            param.put("list", list);
            System.out.println(param);
            //发送UDP
            for(String s : list){
                delUDP(s);
            }
            groupService.deleteGroupBatch(param);
        }
        return ResponseVO.success();
    }
}
