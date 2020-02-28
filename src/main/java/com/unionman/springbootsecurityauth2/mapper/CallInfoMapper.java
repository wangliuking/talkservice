package com.unionman.springbootsecurityauth2.mapper;

import com.unionman.springbootsecurityauth2.entity.CallInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CallInfoMapper {

    @Select("<script>" +
            "select * from table_callInfo where (callingId in " +
            "<foreach collection=\"strList\" index=\"index\" item=\"id\" open=\"(\" separator=\",\" close=\")\">"+
            "#{id}"+
            "</foreach>"+
            "or calledId in "+
            "<foreach collection=\"strList\" index=\"index\" item=\"id\" open=\"(\" separator=\",\" close=\")\">"+
            "#{id}"+
            "</foreach>"+
            ")"+
            "<if test=\"callingId != null and callingId != ''\">" +
            "and callingId like concat('%',#{callingId},'%')" +
            "</if>" +
            "<if test=\"calledId != null and calledId != ''\">" +
            "and calledId like concat('%',#{calledId},'%')" +
            "</if>" +
            "<if test=\"startTime != null and startTime != ''\">" +
            "and startTime between #{startTime} and #{endTime}" +
            "</if>" +
            "order by startTime desc"+
            "<if test=\"start != null and start != -1\">" +
            "limit #{start},#{limit}"+
            "</if>" +
            "</script>")
    List<CallInfo> selectCallInfoList(Map<String, Object> param);

    @Select("<script>" +
            "select count(*) from table_callInfo where (callingId in " +
            "<foreach collection=\"strList\" index=\"index\" item=\"id\" open=\"(\" separator=\",\" close=\")\">"+
            "#{id}"+
            "</foreach>"+
            "or calledId in "+
            "<foreach collection=\"strList\" index=\"index\" item=\"id\" open=\"(\" separator=\",\" close=\")\">"+
            "#{id}"+
            "</foreach>"+
            ")"+
            "<if test=\"callingId != null and callingId != ''\">" +
            "and callingId like concat('%',#{callingId},'%')" +
            "</if>" +
            "<if test=\"calledId != null and calledId != ''\">" +
            "and calledId like concat('%',#{calledId},'%')" +
            "</if>" +
            "<if test=\"startTime != null and startTime != ''\">" +
            "and startTime between #{startTime} and #{endTime}" +
            "</if>" +
            "</script>")
    int selectCallInfoListCount(Map<String, Object> param);

    @Delete("delete from table_callInfo where id=#{id}")
    int deleteCallInfo(int id);
}
