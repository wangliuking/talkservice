package com.unionman.springbootsecurityauth2.controller;

import com.unionman.springbootsecurityauth2.dto.UserDTO;
import com.unionman.springbootsecurityauth2.entity.User;
import com.unionman.springbootsecurityauth2.entity.User2Group;
import com.unionman.springbootsecurityauth2.entity.WebUser;
import com.unionman.springbootsecurityauth2.mapper.User2GroupMapper;
import com.unionman.springbootsecurityauth2.service.User2GroupService;
import com.unionman.springbootsecurityauth2.service.UserService;
import com.unionman.springbootsecurityauth2.utils.AssertUtils;
import com.unionman.springbootsecurityauth2.utils.PublicCallProtobuf;
import com.unionman.springbootsecurityauth2.utils.RedisUtil;
import com.unionman.springbootsecurityauth2.utils.UDPTest;
import com.unionman.springbootsecurityauth2.vo.UserVO;
import com.unionman.springbootsecurityauth2.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

/**
 * @author Zhifeng.Zeng
 * @description 用户权限管理
 * @date 2019/4/19 13:58
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/auth/")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTokenStore redisTokenStore;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private StructureController structureController;

    @Autowired
    private User2GroupMapper user2GroupMapper;

    /**
     * @param req
     * @return
     * @description 删除web用户
     */
    @GetMapping("deleteWebUser")
    public ResponseVO deleteWebUser(HttpServletRequest req) throws Exception {
        String userId = req.getParameter("userId");
        userService.deleteWebUser(userId);
        return ResponseVO.success();
    }

    /**
     * @param webUser
     * @return
     * @descripiton 修改web用户
     */
    @PostMapping("updateWebUser")
    public ResponseVO updateWebUser(@Valid @RequestBody WebUser webUser) {
        userService.updateWebUser(webUser);
        return ResponseVO.success();
    }

    /**
     * @param webUser
     * @return
     * @description 添加web用户
     */
    @PostMapping("addWebUser")
    public ResponseVO addWebUser(@Valid @RequestBody WebUser webUser) {
        userService.addWebUser(webUser);
        return ResponseVO.success();
    }

    /**
     * @param req
     * @return
     * @description 删除用户
     */
    @GetMapping("deleteUser")
    public ResponseVO deleteUser(HttpServletRequest req) throws Exception {
        String userId = req.getParameter("userId");
        //udp通知交换中心
        delUDP(userId);
        userService.deleteUser(userId);
        return ResponseVO.success();
    }

    public void delUDP(String userId){
        //查询是否有用户和组关联
        int count = user2GroupMapper.findIsExistUserId(userId);
        if(count > 0){
            UDPTest.user2groupUDP(userId,"",3);
        }
        UDPTest.userUDP(userId,3);
    }

    /**
     * @param user
     * @return
     * @descripiton 修改用户
     */
    @PostMapping("updateUser")
    public ResponseVO updateUser(@Valid @RequestBody User user) {
        userService.updateUser(user);
        // 发起udp通知交换中心操作
        UDPTest.userUDP(user.getUserId(),2);
        return ResponseVO.success();
    }

    /**
     * @param user
     * @return
     * @description 添加用户
     */
    @PostMapping("addUser")
    public ResponseVO addUser(@Valid @RequestBody User user) {
        userService.addUser(user);
        // 发起udp通知交换中心操作
        UDPTest.userUDP(user.getUserId(),1);
        return ResponseVO.success();
    }

    /**
     * @return
     * @description 获取指定用户信息
     */
    @GetMapping("checkUserId")
    public ResponseVO checkUserId(HttpServletRequest req) {
        String userId = req.getParameter("userId");
        return userService.findUserById(userId);
    }

    /**
     * @return
     * @description 获取指定web用户信息
     */
    @GetMapping("checkWebUserId")
    public ResponseVO checkWebUserId(HttpServletRequest req) {
        String userId = req.getParameter("userId");
        return userService.findWebUserById(userId);
    }

    /**
     * @return
     * @description 获取用户列表
     */
    @GetMapping("userList")
    public ResponseVO findAllUser(HttpServletRequest req) {
        String start = req.getParameter("start");
        String limit = req.getParameter("limit");
        String userId = req.getParameter("userId");
        String userName = req.getParameter("userName");
        String power = req.getParameter("power");
        String structure = req.getParameter("structure");
        List<Integer> strList = structureController.foreachIdAndPIdForConnection(Integer.parseInt(structure));
        Map<String, Object> map = new HashMap<String, Object>() {{
            put("start", "".equals(start) ? -1 : Integer.parseInt(start));
            put("limit", "".equals(limit) ? -1 : Integer.parseInt(limit));
            put("userId", userId);
            put("userName", userName);
            put("power", "".equals(power) ? -1 : Integer.parseInt(power));
            put("strList", strList);
        }};
        return userService.selectUserList(map,structure);
    }

    /**
     * @return
     * @description 获取web用户列表
     */
    @GetMapping("webuserList")
    public ResponseVO findAllWebUser(HttpServletRequest req) {
        String userId = req.getParameter("userId");
        String userName = req.getParameter("userName");
        String start = req.getParameter("start");
        String limit = req.getParameter("limit");
        String power = req.getParameter("power");
        String structure = req.getParameter("structure");
        List<Integer> strList = structureController.foreachIdAndPIdForConnection(Integer.parseInt(structure));
        System.out.println("strList : "+strList);
        Map<String, Object> map = new HashMap<String, Object>() {{
            put("userId", userId);
            put("userName", userName);
            put("start", "".equals(start) ? -1 : Integer.parseInt(start));
            put("limit", "".equals(limit) ? -1 : Integer.parseInt(limit));
            put("power", "".equals(power) ? -1 : Integer.parseInt(power));
            put("strList", strList);
        }};
        return userService.selectWebUserList(map,structure);
    }

    /**
     * @return
     * @description 获取指定用户信息
     */
    @GetMapping("userByAccount")
    public ResponseVO userByAccount(HttpServletRequest req) {
        String token = req.getParameter("token");
        if (token != null && !"".equals(token)) {
            UserVO loginUserVO = redisUtil.get(token, UserVO.class);
            log.info("loginUserVO : " + loginUserVO);
            return userService.findWebUserById(loginUserVO.getUserId());
        } else {
            return ResponseVO.tokenIsNull(null);
        }
    }

    /**
     * @param loginUserDTO
     * @return
     * @description 用户登录
     */
    @PostMapping("user/login")
    public ResponseVO login(UserDTO loginUserDTO) {
        log.info("==================");
        log.info("loginUserDTO : " + loginUserDTO);
        return userService.login(loginUserDTO);
    }


    /**
     * @param authorization
     * @return
     * @description 用户注销
     */
    @GetMapping("user/logout")
    public ResponseVO logout(@RequestHeader("Authorization") String authorization) {
        redisTokenStore.removeAccessToken(AssertUtils.extracteToken(authorization));
        return ResponseVO.success();
    }

    /**
     * 查询组织结构下所有可管理用户
     */
    public Map<String, Object> getManageUser(int power) {
        Map<String, Object> param = new HashMap<>();
        if (power == -1) {
            param.put("power", -1);
            param.put("strList", null);
        } else {
            List<Integer> strList = structureController.foreachIdAndPIdForConnection(power);
            param.put("power", power);
            param.put("strList", strList);
        }
        return userService.selectManageUserId(param);
    }

    /**
     * @param user
     * @return
     * @description 批量添加用户
     */
    @PostMapping("addUserPatch")
    public ResponseVO addUserPatch(@Valid @RequestBody User user) {
        Map<String, Object> existUserIdMap = getManageUser(-1);
        Map<String, Object> param = new HashMap<String, Object>() {{
            put("userName", user.getUserName());
            put("password", user.getPassword());
            put("authenticateCode", user.getAuthenticateCode());
            put("priority", user.getPriority());
            put("type", user.getType());
            put("scanEn", user.getScanEn());
            put("userBook", user.getUserBook());
            put("gpsInterval", user.getGpsInterval());
            put("externalVideoViewEn", user.getExternalVideoViewEn());
            put("power", user.getPower());
        }};
        String userIds = user.getUserId();
        List<Map<String, Object>> list = new ArrayList<>();
        if (userIds.contains(",")) {
            List<String> userIdList = Arrays.asList(userIds.split(","));
            for (String userId : userIdList) {
                //若已存在相同用户id，则不加入
                if (!existUserIdMap.containsKey(userId)) {
                    Map<String, Object> map = new HashMap<String, Object>() {{
                        put("userId", userId);
                        put("userName", user.getUserName());
                        put("password", user.getPassword());
                        put("authenticateCode", user.getAuthenticateCode());
                        put("priority", user.getPriority());
                        put("type", user.getType());
                        put("scanEn", user.getScanEn());
                        put("userBook", user.getUserBook());
                        put("gpsInterval", user.getGpsInterval());
                        put("externalVideoViewEn", user.getExternalVideoViewEn());
                        put("power", user.getPower());
                    }};
                    list.add(map);
                }
            }
            param.put("list", list);
            System.out.println(param);
            userService.addUserBatch(param);
            //发送udp
            for(Map<String,Object> m : list){
                String userId = m.get("userId")+"";
                UDPTest.userUDP(userId,1);
            }
        } else if (userIds.contains("-")) {
            String[] arr = userIds.split("-");
            int start = Integer.parseInt(arr[0]);
            int end = Integer.parseInt(arr[1]);
            for (int i = start; i <= end; i++) {
                if (!existUserIdMap.containsKey(i + "")) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("userId", i + "");
                    map.put("userName", user.getUserName());
                    map.put("password", user.getPassword());
                    map.put("authenticateCode", user.getAuthenticateCode());
                    map.put("priority", user.getPriority());
                    map.put("type", user.getType());
                    map.put("scanEn", user.getScanEn());
                    map.put("userBook", user.getUserBook());
                    map.put("gpsInterval", user.getGpsInterval());
                    map.put("externalVideoViewEn", user.getExternalVideoViewEn());
                    map.put("power", user.getPower());
                    list.add(map);
                }
            }
            param.put("list", list);
            System.out.println(param);
            userService.addUserBatch(param);
            //发送udp
            for(Map<String,Object> m : list){
                String userId = m.get("userId")+"";
                UDPTest.userUDP(userId,1);
            }
        }
        return ResponseVO.success();
    }

    /**
     * @param user
     * @return
     * @description 批量修改用户
     */
    @PostMapping("updateUserBatch")
    public ResponseVO updateUserBatch(@Valid @RequestBody User user) {
        Map<String, Object> existUserIdMap = getManageUser(Integer.parseInt(user.getOldUserId()));
        Map<String, Object> param = new HashMap<String, Object>() {{
            put("userName", user.getUserName());
            put("password", user.getPassword());
            put("authenticateCode", user.getAuthenticateCode());
            put("type", user.getType());
            put("priority", user.getPriority());
            put("scanEn", user.getScanEn());
            put("userBook", user.getUserBook());
            put("gpsInterval", user.getGpsInterval());
            put("externalVideoViewEn", user.getExternalVideoViewEn());
            put("power", user.getPower());
        }};
        String userIds = user.getUserId();
        if (userIds.contains(",")) {
            List<String> userIdList = new ArrayList<>();
            List<String> tempList = Arrays.asList(userIds.split(","));
            for (String s : tempList) {
                //可管理用户才能进行下一步操作
                if (existUserIdMap.containsKey(s)) {
                    userIdList.add(s);
                }
            }
            param.put("list", userIdList);
            System.out.println(param);
            userService.updateUserBatch(param);
            //发送udp
            for(String s : userIdList){
                UDPTest.userUDP(s,2);
            }
        } else if (userIds.contains("-")) {
            List<String> list = new ArrayList<>();
            String[] arr = userIds.split("-");
            int start = Integer.parseInt(arr[0]);
            int end = Integer.parseInt(arr[1]);
            for (int i = start; i <= end; i++) {
                //可管理用户才能进行下一步操作
                if (existUserIdMap.containsKey(i + "")) {
                    list.add(i + "");
                }
            }
            param.put("list", list);
            System.out.println(param);
            userService.updateUserBatch(param);
            //发送udp
            for(String s : list){
                UDPTest.userUDP(s,2);
            }
        }
        return ResponseVO.success();
    }

    /**
     * @param user
     * @return
     * @description 批量删除用户
     */
    @PostMapping("deleteUserBatch")
    public ResponseVO deleteUserBatch(@Valid @RequestBody User user) {
        Map<String, Object> existUserIdMap = getManageUser(Integer.parseInt(user.getOldUserId()));
        Map<String, Object> param = new HashMap<>();
        String userIds = user.getUserId();
        if (userIds.contains(",")) {
            List<String> userIdList = new ArrayList<>();
            List<String> tempList = Arrays.asList(userIds.split(","));
            for (String s : tempList) {
                //可管理用户才能进行下一步操作
                if (existUserIdMap.containsKey(s)) {
                    userIdList.add(s);
                }
            }
            param.put("list", userIdList);
            System.out.println(param);
            //发送udp
            for(String s : userIdList){
                delUDP(s);
            }
            userService.deleteUserBatch(param);
        } else if (userIds.contains("-")) {
            List<String> list = new ArrayList<>();
            String[] arr = userIds.split("-");
            int start = Integer.parseInt(arr[0]);
            int end = Integer.parseInt(arr[1]);
            for (int i = start; i <= end; i++) {
                //可管理用户才能进行下一步操作
                if (existUserIdMap.containsKey(i + "")) {
                    list.add(i + "");
                }
            }
            param.put("list", list);
            System.out.println(param);
            //发送udp
            for(String s : list){
                delUDP(s);
            }
            userService.deleteUserBatch(param);
        }
        return ResponseVO.success();
    }

}
