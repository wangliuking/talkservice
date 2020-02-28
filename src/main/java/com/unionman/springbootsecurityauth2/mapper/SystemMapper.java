package com.unionman.springbootsecurityauth2.mapper;

import com.unionman.springbootsecurityauth2.entity.System;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SystemMapper {

    @Select("select * from table_system where id = #{id}")
    System findSystemById(int id);

    @Select("<script>" +
            "select * from table_system" +
            "<if test=\"start != null and start != -1\">" +
            "limit #{start},#{limit}"+
            "</if>" +
            "</script>")
    List<System> selectSystemList(Map<String, Object> param);

    @Select("<script>" +
            "select count(*) from table_system" +
            "</script>")
    int selectSystemListCount(Map<String, Object> param);

    @Update("update table_system set callTime=#{callTime},pttOnTime=#{pttOnTime},pttSilentTime=#{pttSilentTime},gpsReportInterval=#{gpsReportInterval},appHeartInterval=#{appHeartInterval},audioHeartInterval=#{audioHeartInterval},tcpListenPort=#{tcpListenPort},appVoicePort=#{appVoicePort},bdId=#{bdId},bdListenPort=#{bdListenPort},bdVoicePort=#{bdVoicePort},gpsServerPort=#{gpsServerPort},dbSynPort=#{dbSynPort},videoRecPath=#{videoRecPath},wavRecPath=#{wavRecPath},videoUrlPrefix=#{videoUrlPrefix},VAGWAddress=#{VAGWAddress},VAGWPort=#{VAGWPort} where id=#{id}")
    int updateSystem(System system);
}
