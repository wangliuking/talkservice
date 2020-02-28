package com.unionman.springbootsecurityauth2.mapper;

import com.unionman.springbootsecurityauth2.entity.Sms;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SmsMapper {

    @Select("<script>" +
            "select * from table_sms where (srcId in " +
            "<foreach collection=\"strList\" index=\"index\" item=\"id\" open=\"(\" separator=\",\" close=\")\">"+
            "#{id}"+
            "</foreach>"+
            "or tarId in "+
            "<foreach collection=\"strList\" index=\"index\" item=\"id\" open=\"(\" separator=\",\" close=\")\">"+
            "#{id}"+
            "</foreach>"+
            ")"+
            "<if test=\"srcId != null and srcId != ''\">" +
            "and srcId like concat('%',#{srcId},'%')" +
            "</if>" +
            "<if test=\"tarId != null and tarId != ''\">" +
            "and tarId like concat('%',#{tarId},'%')" +
            "</if>" +
            "<if test=\"startTime != null and startTime != ''\">" +
            "and sendTime between #{startTime} and #{endTime}" +
            "</if>" +
            "order by sendTime desc"+
            "<if test=\"start != null and start != -1\">" +
            "limit #{start},#{limit}"+
            "</if>" +
            "</script>")
    List<Sms> selectSmsList(Map<String, Object> param);

    @Select("<script>" +
            "select count(*) from table_sms where (srcId in " +
            "<foreach collection=\"strList\" index=\"index\" item=\"id\" open=\"(\" separator=\",\" close=\")\">"+
            "#{id}"+
            "</foreach>"+
            "or tarId in "+
            "<foreach collection=\"strList\" index=\"index\" item=\"id\" open=\"(\" separator=\",\" close=\")\">"+
            "#{id}"+
            "</foreach>"+
            ")"+
            "<if test=\"srcId != null and srcId != ''\">" +
            "and srcId like concat('%',#{srcId},'%')" +
            "</if>" +
            "<if test=\"tarId != null and tarId != ''\">" +
            "and tarId like concat('%',#{tarId},'%')" +
            "</if>" +
            "<if test=\"startTime != null and startTime != ''\">" +
            "and sendTime between #{startTime} and #{endTime}" +
            "</if>" +
            "</script>")
    int selectSmsListCount(Map<String, Object> param);

    @Delete("delete from table_sms where id=#{id}")
    int deleteSms(int id);
}
