package com.unionman.springbootsecurityauth2.controller;

import com.unionman.springbootsecurityauth2.dto.SystemDTO;
import com.unionman.springbootsecurityauth2.service.SystemService;
import com.unionman.springbootsecurityauth2.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Validated
@RestController
@RequestMapping("/system/")
public class SystemController {
    @Autowired
    private SystemService systemService;

    /**
     * @description 添加参数
     * @return
     */
    @PostMapping("system")
    public ResponseVO add(@Valid @RequestBody SystemDTO systemDTO) throws Exception{
        systemService.addSystem(systemDTO);
        return ResponseVO.success();
    }

    /**
     * @description 查询所有参数
     * @return
     */
    @GetMapping("system")
    public ResponseVO findAllSystem(){
        return systemService.findAllSystemVO();
    }

    /**
     * @description 删除参数
     * @return
     */
    @DeleteMapping("system/{callTime}")
    public ResponseVO delete(@PathVariable("callTime") int callTime) throws Exception{
        systemService.deleteSystem(callTime);
        return ResponseVO.success();
    }

    /**
     * @description 更新参数
     * @return
     */
    @PutMapping("system")
    public ResponseVO update(@Valid @RequestBody SystemDTO systemDTO){
        systemService.updateSystem(systemDTO);
        return ResponseVO.success();
    }
}
