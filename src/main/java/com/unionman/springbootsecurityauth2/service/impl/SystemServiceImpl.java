package com.unionman.springbootsecurityauth2.service.impl;

import com.unionman.springbootsecurityauth2.dto.SystemDTO;
import com.unionman.springbootsecurityauth2.entity.SystemData;
import com.unionman.springbootsecurityauth2.repository.SystemRepository;
import com.unionman.springbootsecurityauth2.service.SystemService;
import com.unionman.springbootsecurityauth2.utils.BeanUtils;
import com.unionman.springbootsecurityauth2.vo.ResponseVO;
import com.unionman.springbootsecurityauth2.vo.SystemVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    private SystemRepository systemRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addSystem(SystemDTO systemDTO) {
        SystemData systemData = new SystemData();
        SystemData systemDataRep = systemRepository.findSystemByCallTime(systemDTO.getCallTime());
        if (systemDataRep != null) {
            //此处应该用自定义异常去返回，在这里我就不去具体实现了
            try {
                throw new Exception("This systemData already exists!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        BeanUtils.copyPropertiesIgnoreNull(systemDTO, systemData);
        systemRepository.save(systemData);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSystem(int callTime){
        SystemData systemData = systemRepository.findSystemByCallTime(callTime);
        if(systemData == null){
            try {
                throw new Exception("this system not exists can not delete");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        systemRepository.delete(systemData);
    }

    @Override
    public void updateSystem(SystemDTO systemDTO) {
        SystemData systemData = systemRepository.findSystemByCallTime(systemDTO.getCallTime());
        if(systemData == null){
            try {
                throw new Exception("this system is not exists can not update");
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            BeanUtils.copyPropertiesIgnoreNull(systemDTO, systemData);
            systemRepository.saveAndFlush(systemData);
        }
    }

    @Override
    public ResponseVO<List<SystemVO>> findAllSystemVO() {
        List<SystemData> systemDataPOList = systemRepository.findAll();
        List<SystemVO> systemVOList = new ArrayList<>();
        systemDataPOList.forEach(systemDataPO -> {
            SystemVO systemVO = new SystemVO();
            BeanUtils.copyPropertiesIgnoreNull(systemDataPO, systemVO);
            systemVOList.add(systemVO);
        });
        log.info("systemDataPOList : "+ systemDataPOList);
        log.info("systemVOList : "+systemVOList);
        return ResponseVO.success(systemVOList);
    }

    @Override
    public ResponseVO<SystemVO> findSystemByCallTimeVO(String account) {
        return null;
    }
}
