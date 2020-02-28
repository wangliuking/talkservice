package com.unionman.springbootsecurityauth2.controller;

import com.unionman.springbootsecurityauth2.entity.System;
import com.unionman.springbootsecurityauth2.service.SystemService;
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
 * @description 系统参数管理
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/system/")
public class SystemController {

    @Autowired
    private SystemService systemService;

    /**
     * @param system
     * @return
     * @descripiton 修改
     */
    @PostMapping("updateSystem")
    public ResponseVO updateSystem(@Valid @RequestBody System system) {
        systemService.updateSystem(system);
        return ResponseVO.success();
    }

    /**
     * @return
     * @description 获取指定组信息
     */
    @GetMapping("checkSystemId")
    public ResponseVO checkSystemId(HttpServletRequest req) {
        String id = req.getParameter("id");
        return systemService.findSystemById(Integer.parseInt(id));
    }

    /**
     * @return
     * @description 获取组列表
     */
    @GetMapping("systemList")
    public ResponseVO findAllSystem(HttpServletRequest req) {
        String start = req.getParameter("start");
        String limit = req.getParameter("limit");
        String id = req.getParameter("id");
        Map<String, Object> map = new HashMap<String, Object>() {{
            put("start", "".equals(start) ? -1 : Integer.parseInt(start));
            put("limit", "".equals(limit) ? -1 : Integer.parseInt(limit));
            put("id", "".equals(id) ? -1 : Integer.parseInt(id));
        }};
        return systemService.selectSystemList(map);
    }

}
