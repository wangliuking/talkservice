package com.unionman.springbootsecurityauth2.service;

import com.unionman.springbootsecurityauth2.dto.SystemDTO;
import com.unionman.springbootsecurityauth2.vo.ResponseVO;
import com.unionman.springbootsecurityauth2.vo.SystemVO;

import java.util.List;

public interface SystemService {
    /**
     * @description 添加参数
     */
    void addSystem(SystemDTO systemDTO) throws Exception;

    /**
     * 删除参数
     * @param callTime
     */
    void deleteSystem(int callTime) throws Exception;

    /**
     * @description 修改参数
     * @param systemDTO
     */
    void updateSystem(SystemDTO systemDTO);

    /**
     * @description 获取所有参数列表VO
     * @return
     */
    ResponseVO<List<SystemVO>> findAllSystemVO();

    /**
     * @description 获取指定参数
     * @return
     */
    ResponseVO<SystemVO> findSystemByCallTimeVO(String account);
    
}
