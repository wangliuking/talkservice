package com.unionman.springbootsecurityauth2.controller;

import com.unionman.springbootsecurityauth2.entity.GpsUser;
import com.unionman.springbootsecurityauth2.service.GpsUserService;
import com.unionman.springbootsecurityauth2.utils.UDPTest;
import com.unionman.springbootsecurityauth2.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @description gps用户管理
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/gpsuser/")
public class GpsUserController {

    @Autowired
    private GpsUserService gpsUserService;

    /**
     * @param req
     * @return
     * @description 删除
     */
    @GetMapping("deleteGpsUser")
    public ResponseVO deleteGpsUser(HttpServletRequest req) throws Exception {
        String gpsUserId = req.getParameter("gpsUserId");
        System.out.println("**********************************");
        System.out.println(gpsUserId);
        System.out.println("**********************************");
        //发送UDP
        UDPTest.gpsUserUDP(Integer.parseInt(gpsUserId),3);
        gpsUserService.deleteGpsUser(Integer.parseInt(gpsUserId));
        return ResponseVO.success();
    }

    /**
     * @param gpsUser
     * @return
     * @descripiton 修改
     */
    @PostMapping("updateGpsUser")
    public ResponseVO updateGpsUser(@Valid @RequestBody GpsUser gpsUser) {
        System.out.println("**********************************");
        System.out.println(gpsUser);
        System.out.println("**********************************");
        gpsUserService.updateGpsUser(gpsUser);
        //发送UDP
        UDPTest.gpsUserUDP(gpsUser.getGpsUserId(),2);
        return ResponseVO.success();
    }

    /**
     * @param gpsUser
     * @return
     * @description 添加
     */
    @PostMapping("addGpsUser")
    public ResponseVO addGpsUser(@Valid @RequestBody GpsUser gpsUser) {
        /*System.out.println("**********************************");
        System.out.println(gpsUser);
        System.out.println("**********************************");*/
        gpsUserService.addGpsUser(gpsUser);
        //发送UDP
        UDPTest.gpsUserUDP(gpsUser.getGpsUserId(),1);
        return ResponseVO.success();
    }

    /**
     * @return
     * @description 获取指定信息
     */
    @GetMapping("checkGpsUserId")
    public ResponseVO checkGpsUserId(HttpServletRequest req) {
        String gpsUserId = req.getParameter("gpsUserId");
        return gpsUserService.findGpsUserById(Integer.parseInt(gpsUserId));
    }

    /**
     * @return
     * @description 获取列表
     */
    @GetMapping("gpsUserList")
    public ResponseVO findAllGpsUser(HttpServletRequest req) {
        String start = req.getParameter("start");
        String limit = req.getParameter("limit");
        String gpsUserId = req.getParameter("gpsUserId");
        Map<String, Object> map = new HashMap<String, Object>() {{
            put("start", "".equals(start) ? -1 : Integer.parseInt(start));
            put("limit", "".equals(limit) ? -1 : Integer.parseInt(limit));
            put("gpsUserId", "".equals(gpsUserId) ? -1 : Integer.parseInt(gpsUserId));
        }};
        return gpsUserService.selectGpsUserList(map);
    }


}
