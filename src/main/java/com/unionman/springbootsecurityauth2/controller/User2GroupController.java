package com.unionman.springbootsecurityauth2.controller;

import com.unionman.springbootsecurityauth2.entity.Group;
import com.unionman.springbootsecurityauth2.entity.User2Group;
import com.unionman.springbootsecurityauth2.service.GroupService;
import com.unionman.springbootsecurityauth2.service.User2GroupService;
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
 * @description 用户和组关联管理
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/user2group/")
public class User2GroupController {

    @Autowired
    private User2GroupService user2GroupService;

    @Autowired
    private AuthController authController;

    @Autowired
    private GroupController groupController;

    @Autowired
    private StructureController structureController;

    /**
     * @param req
     * @return
     * @description 删除用户和组关联
     */
    @GetMapping("deleteUser2Group")
    public ResponseVO deleteUser2Group(HttpServletRequest req) throws Exception {
        String userId = req.getParameter("userId");
        String groupId = req.getParameter("groupId");
        System.out.println("**********************************");
        System.out.println(userId);
        System.out.println(groupId);
        System.out.println("**********************************");
        Map<String, Object> param = new HashMap<String, Object>() {{
            put("userId", userId);
            put("groupId", groupId);
        }};
        //发送UDP
        UDPTest.user2groupUDP(userId,groupId,3);
        user2GroupService.deleteUser2Group(param);
        return ResponseVO.success();
    }

    /**
     * @param user2Group
     * @return
     * @descripiton 修改用户和组关联
     */
    @PostMapping("updateUser2Group")
    public ResponseVO updateUser2Group(@Valid @RequestBody User2Group user2Group) {
        System.out.println("**********************************");
        System.out.println(user2Group);
        System.out.println("**********************************");
        user2GroupService.updateUser2Group(user2Group);
        //发送UDP
        UDPTest.user2groupUDP(user2Group.getUserId(),user2Group.getGroupId(),2);
        return ResponseVO.success();
    }

    /**
     * @param user2Group
     * @return
     * @description 添加用户和组关联
     */
    @PostMapping("addUser2Group")
    public ResponseVO addUser2Group(@Valid @RequestBody User2Group user2Group) {
        /*System.out.println("**********************************");
        System.out.println(user2Group);
        System.out.println("**********************************");*/
        user2GroupService.addUser2Group(user2Group);
        //发送UDP
        UDPTest.user2groupUDP(user2Group.getUserId(),user2Group.getGroupId(),1);
        return ResponseVO.success();
    }

    /**
     * @return
     * @description 获取指定用户和组关联信息
     */
    @GetMapping("checkUser2Group")
    public ResponseVO checkUser2Group(HttpServletRequest req) {
        String userId = req.getParameter("userId");
        String groupId = req.getParameter("groupId");
        Map<String, Object> param = new HashMap<String, Object>() {{
            put("userId", userId);
            put("groupId", groupId);
        }};
        return user2GroupService.findUser2GroupById(param);
    }

    /**
     * @return
     * @description 获取用户和组列表
     */
    @GetMapping("user2groupList")
    public ResponseVO findAllUser2Group(HttpServletRequest req) {
        String userId = req.getParameter("userId");
        String userName = req.getParameter("userName");
        String groupId = req.getParameter("groupId");
        String groupName = req.getParameter("groupName");
        String start = req.getParameter("start");
        String limit = req.getParameter("limit");
        String structure = req.getParameter("structure");
        List<Integer> strList = structureController.foreachIdAndPIdForConnection(Integer.parseInt(structure));
        Map<String, Object> param = new HashMap<String, Object>() {{
            put("start", "".equals(start) ? -1 : Integer.parseInt(start));
            put("limit", "".equals(limit) ? -1 : Integer.parseInt(limit));
            put("userId", userId);
            put("userName", userName);
            put("groupId", groupId);
            put("groupName", groupName);
            put("strList", strList);
        }};
        return user2GroupService.selectUser2GroupList(param);
    }

    /**
     * @param user2Group
     * @return
     * @description 批量添加关联
     */
    @PostMapping("addUser2GroupPatch")
    public ResponseVO addUser2GroupPatch(@Valid @RequestBody User2Group user2Group) {
        System.out.println("**********************************");
        System.out.println(user2Group);
        System.out.println("**********************************");
        List<Map<String,Object>> list = getParam(user2Group, "add", Integer.parseInt(user2Group.getOldUserId()));
        Map<String, Object> param = new HashMap<String, Object>() {{
            put("list", list);
        }};
        System.out.println(param);
        user2GroupService.addUser2GroupBatch(param);
        //发送UDP
        for(Map<String,Object> m : list){
            String userId = m.get("userId")+"";
            String groupId = m.get("groupId")+"";
            UDPTest.user2groupUDP(userId,groupId,1);
        }
        return ResponseVO.success();
    }

    public List<Map<String,Object>> getParam(User2Group user2Group, String action, int power) {
        Map<String, Object> existUserIdMap = authController.getManageUser(power);
        Map<String, Object> existGroupIdMap = groupController.getManageGroup(power);
        String userIds = user2Group.getUserId();
        String groupIds = user2Group.getGroupId();
        List<String> userIdlist = new ArrayList<>();
        List<String> groupIdlist = new ArrayList<>();
        if (userIds.contains(",")) {
            List<String> tempList = Arrays.asList(userIds.split(","));
            for (String s : tempList) {
                if (existUserIdMap.containsKey(s)) {
                    userIdlist.add(s);
                }
            }
        } else if (userIds.contains("-")) {
            String[] arr = userIds.split("-");
            int end = Integer.parseInt(arr[1]);
            int start = Integer.parseInt(arr[0]);
            for (int i = start; i <= end; i++) {
                if (existUserIdMap.containsKey(i + "")) {
                    userIdlist.add(i + "");
                }
            }
        } else if (!userIds.contains(",") && !userIds.contains("-")) {
            if (existUserIdMap.containsKey(userIds)) {
                userIdlist.add(userIds);
            }
        }
        if (groupIds.contains(",")) {
            List<String> tempList = Arrays.asList(groupIds.split(","));
            for (String s : tempList) {
                if (existGroupIdMap.containsKey(s)) {
                    groupIdlist.add(s);
                }
            }
        } else if (groupIds.contains("-")) {
            String[] arr = groupIds.split("-");
            int start = Integer.parseInt(arr[0]);
            int end = Integer.parseInt(arr[1]);
            for (int i = start; i <= end; i++) {
                if (existGroupIdMap.containsKey(i + "")) {
                    groupIdlist.add(i + "");
                }
            }
        } else if (!groupIds.contains(",") && !groupIds.contains("-")) {
            if (existGroupIdMap.containsKey(groupIds)) {
                groupIdlist.add(groupIds);
            }
        }
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> idMap = user2GroupService.selectUser2GroupId();
        for (int i = 0; i < userIdlist.size(); i++) {
            for (int j = 0; j < groupIdlist.size(); j++) {
                String userId = userIdlist.get(i);
                String groupId = groupIdlist.get(j);
                String key = userId + groupId;
                if (action.equals("add")) {
                    if (!idMap.containsKey(key)) {
                        Map<String, Object> map = new HashMap<String, Object>() {{
                            put("userId", userId);
                            put("groupId", groupId);
                        }};
                        list.add(map);
                    }
                } else if (action.equals("del")) {
                    if (idMap.containsKey(key)) {
                        Map<String, Object> map = new HashMap<String, Object>() {{
                            put("userId", userId);
                            put("groupId", groupId);
                        }};
                        list.add(map);
                    }
                }

            }
        }

        return list;
    }

    /**
     * @param user2Group
     * @return
     * @description 批量删除关联
     */
    @PostMapping("deleteUser2GroupBatch")
    public ResponseVO deleteUser2GroupBatch(@Valid @RequestBody User2Group user2Group) {
        System.out.println("**********************************");
        System.out.println(user2Group);
        System.out.println("**********************************");
        List<Map<String,Object>> list = getParam(user2Group, "del", Integer.parseInt(user2Group.getOldUserId()));
        Map<String, Object> param = new HashMap<String, Object>() {{
            put("list", list);
        }};
        System.out.println(param);
        //发送UDP
        for(Map<String,Object> m : list){
            String userId = m.get("userId")+"";
            String groupId = m.get("groupId")+"";
            UDPTest.user2groupUDP(userId,groupId,3);
        }
        user2GroupService.deleteUser2GroupBatch(param);
        return ResponseVO.success();
    }

}
