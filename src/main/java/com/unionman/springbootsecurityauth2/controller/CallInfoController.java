package com.unionman.springbootsecurityauth2.controller;

import com.unionman.springbootsecurityauth2.mapper.GroupMapper;
import com.unionman.springbootsecurityauth2.mapper.UserMapper;
import com.unionman.springbootsecurityauth2.service.CallInfoService;
import com.unionman.springbootsecurityauth2.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description 呼叫信息管理
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/callInfo/")
public class CallInfoController {

    @Autowired
    private CallInfoService callInfoService;

    @Autowired
    private StructureController structureController;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GroupMapper groupMapper;

    /**
     * @param req
     * @return
     * @description 删除
     */
    @GetMapping("deleteCallInfo")
    public ResponseVO deleteCallInfo(HttpServletRequest req) throws Exception {
        String id = req.getParameter("id");
        System.out.println("**********************************");
        System.out.println(id);
        System.out.println("**********************************");
        callInfoService.deleteCallInfo(Integer.parseInt(id));
        return ResponseVO.success();
    }

    /**
     * @return
     * @description 获取列表
     */
    @GetMapping("callInfoList")
    public ResponseVO findAllCallInfo(HttpServletRequest req) {
        String start = req.getParameter("start");
        String limit = req.getParameter("limit");
        String callingId = req.getParameter("callingId");
        String calledId = req.getParameter("calledId");
        String startTime = req.getParameter("startTime");
        String endTime = req.getParameter("endTime");
        String structure = req.getParameter("structure");
        List<Integer> strList = structureController.foreachIdAndPIdForConnection(Integer.parseInt(structure));
        Map<String, Object> map = new HashMap<String, Object>() {{
            put("start", "".equals(start) ? -1 : Integer.parseInt(start));
            put("limit", "".equals(limit) ? -1 : Integer.parseInt(limit));
            put("callingId", callingId);
            put("calledId", calledId);
            put("startTime", startTime);
            put("endTime", endTime);
            put("strList", strList);
            put("power",1);
        }};

        List<String> userList = userMapper.selectManageUserId(map);
        List<String> groupList = groupMapper.selectManageGroupId(map);
        List<String> list = new ArrayList<>();
        for(String s : userList){
            list.add(s);
        }
        for(String s : groupList){
            list.add(s);
        }
        map.put("strList", list);

        return callInfoService.selectCallInfoList(map);
    }


}
