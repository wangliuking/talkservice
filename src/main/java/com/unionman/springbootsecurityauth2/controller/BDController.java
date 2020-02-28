package com.unionman.springbootsecurityauth2.controller;

import com.unionman.springbootsecurityauth2.entity.BD;
import com.unionman.springbootsecurityauth2.mapper.Group2BDMapper;
import com.unionman.springbootsecurityauth2.service.BDService;
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
 * @description 局向管理
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/bd/")
public class BDController {

    @Autowired
    private BDService bdService;

    @Autowired
    private Group2BDMapper group2BDMapper;

    /**
     * @param req
     * @return
     * @description 删除局向
     */
    @GetMapping("deleteBD")
    public ResponseVO deleteBD(HttpServletRequest req) throws Exception {
        String bdId = req.getParameter("bdId");
        System.out.println("**********************************");
        System.out.println(bdId);
        System.out.println("**********************************");
        //发送UDP
        delUDP(bdId);
        bdService.deleteBD(bdId);
        return ResponseVO.success();
    }

    public void delUDP(String bdId){
        //查询是否有组和局向关联
        int count = group2BDMapper.findIsExistBdId(bdId);
        if(count > 0){
            UDPTest.group2bdUDP(bdId,"",3);
        }
        UDPTest.bdUDP(bdId,3);
    }

    /**
     * @param bd
     * @return
     * @descripiton 修改局向
     */
    @PostMapping("updateBD")
    public ResponseVO updateBD(@Valid @RequestBody BD bd) {
        System.out.println("**********************************");
        System.out.println(bd);
        System.out.println("**********************************");
        bdService.updateBD(bd);
        //发送UDP
        UDPTest.bdUDP(bd.getBdId(),2);
        return ResponseVO.success();
    }

    /**
     * @param bd
     * @return
     * @description 添加局向
     */
    @PostMapping("addBD")
    public ResponseVO addBD(@Valid @RequestBody BD bd) {
        /*System.out.println("**********************************");
        System.out.println(bd);
        System.out.println("**********************************");*/
        bdService.addBD(bd);
        //发送UDP
        UDPTest.bdUDP(bd.getBdId(),1);
        return ResponseVO.success();
    }

    /**
     * @return
     * @description 获取指定局向信息
     */
    @GetMapping("checkBdId")
    public ResponseVO checkBdId(HttpServletRequest req) {
        String bdId = req.getParameter("bdId");
        return bdService.findBDById(bdId);
    }

    /**
     * @return
     * @description 获取局向列表
     */
    @GetMapping("bdList")
    public ResponseVO findAllBD(HttpServletRequest req) {
        String start = req.getParameter("start");
        String limit = req.getParameter("limit");
        String bdId = req.getParameter("bdId");
        String bdName = req.getParameter("bdName");
        String address = req.getParameter("address");
        Map<String, Object> map = new HashMap<String, Object>() {{
            put("start", "".equals(start) ? -1 : Integer.parseInt(start));
            put("limit", "".equals(limit) ? -1 : Integer.parseInt(limit));
            put("bdId", bdId);
            put("bdName", bdName);
            put("address", address);
        }};
        return bdService.selectBDList(map);
    }


}
