package com.unionman.springbootsecurityauth2.controller;

import com.unionman.springbootsecurityauth2.entity.Group2BD;
import com.unionman.springbootsecurityauth2.service.Group2BDService;
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
 * @description 组和局向关联管理
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/group2bd/")
public class Group2BDController {

    @Autowired
    private Group2BDService group2BDService;

    /**
     * @param req
     * @return
     * @description 删除组和局向关联
     */
    @GetMapping("deleteGroup2BD")
    public ResponseVO deleteGroup2BD(HttpServletRequest req) throws Exception {
        String bdId = req.getParameter("bdId");
        String groupId = req.getParameter("groupId");
        System.out.println("**********************************");
        System.out.println(bdId);
        System.out.println(groupId);
        System.out.println("**********************************");
        Map<String, Object> param = new HashMap<String, Object>() {{
            put("bdId", bdId);
            put("groupId", groupId);
        }};
        //发送UDP
        UDPTest.group2bdUDP(bdId,groupId,3);
        group2BDService.deleteGroup2BD(param);
        return ResponseVO.success();
    }

    /**
     * @param group2BD
     * @return
     * @descripiton 修改组和局向关联
     */
    @PostMapping("updateGroup2BD")
    public ResponseVO updateGroup2BD(@Valid @RequestBody Group2BD group2BD) {
        System.out.println("**********************************");
        System.out.println(group2BD);
        System.out.println("**********************************");
        group2BDService.updateGroup2BD(group2BD);
        //发送UDP
        UDPTest.group2bdUDP(group2BD.getBdId(),group2BD.getGroupId(),2);
        return ResponseVO.success();
    }

    /**
     * @param group2BD
     * @return
     * @description 添加组和局向关联
     */
    @PostMapping("addGroup2BD")
    public ResponseVO addGroup2BD(@Valid @RequestBody Group2BD group2BD) {
        /*System.out.println("**********************************");
        System.out.println(group2BD);
        System.out.println("**********************************");*/
        group2BDService.addGroup2BD(group2BD);
        //发送UDP
        UDPTest.group2bdUDP(group2BD.getBdId(),group2BD.getGroupId(),1);
        return ResponseVO.success();
    }

    /**
     * @return
     * @description 获取指定组和局向关联信息
     */
    @GetMapping("checkGroup2BD")
    public ResponseVO checkGroup2BD(HttpServletRequest req) {
        String bdId = req.getParameter("bdId");
        String groupId = req.getParameter("groupId");
        Map<String, Object> param = new HashMap<String, Object>() {{
            put("bdId", bdId);
            put("groupId", groupId);
        }};
        return group2BDService.findGroup2BDById(param);
    }

    /**
     * @return
     * @description 获取组和局向列表
     */
    @GetMapping("group2bdList")
    public ResponseVO findAllGroup2BD(HttpServletRequest req) {
        String bdId = req.getParameter("bdId");
        String bdName = req.getParameter("bdName");
        String groupId = req.getParameter("groupId");
        String groupName = req.getParameter("groupName");
        String start = req.getParameter("start");
        String limit = req.getParameter("limit");
        Map<String, Object> param = new HashMap<String, Object>() {{
            put("start", "".equals(start) ? -1 : Integer.parseInt(start));
            put("limit", "".equals(limit) ? -1 : Integer.parseInt(limit));
            put("bdId", bdId);
            put("bdName", bdName);
            put("groupId", groupId);
            put("groupName", groupName);
        }};
        return group2BDService.selectGroup2BDList(param);
    }


}
