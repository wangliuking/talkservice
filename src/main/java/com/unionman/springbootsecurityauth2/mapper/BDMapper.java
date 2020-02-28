package com.unionman.springbootsecurityauth2.mapper;

import com.unionman.springbootsecurityauth2.entity.BD;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BDMapper {

    @Select("select * from table_bureaudirection where bdId = #{bdId}")
    BD findBDById(String bdId);

    @Select("<script>" +
            "select * from table_bureaudirection where 1=1" +
            "<if test=\"bdId != null and bdId != ''\">" +
            "and bdId like concat('%',#{bdId},'%')" +
            "</if>" +
            "<if test=\"address != null and address != ''\">" +
            "and address like concat('%',#{address},'%')" +
            "</if>" +
            "<if test=\"bdName != null and bdName != ''\">" +
            "and bdName like concat('%',#{bdName},'%')" +
            "</if>" +
            "<if test=\"start != null and start != -1\">" +
            "limit #{start},#{limit}"+
            "</if>" +
            "</script>")
    List<BD> selectBDList(Map<String, Object> param);

    @Select("<script>" +
            "select count(*) from table_bureaudirection where 1=1" +
            "<if test=\"bdId != null and bdId != ''\">" +
            "and bdId like concat('%',#{bdId},'%')" +
            "</if>" +
            "<if test=\"address != null and address != ''\">" +
            "and address like concat('%',#{address},'%')" +
            "</if>" +
            "<if test=\"bdName != null and bdName != ''\">" +
            "and bdName like concat('%',#{bdName},'%')" +
            "</if>" +
            "</script>")
    int selectBDListCount(Map<String, Object> param);

    @Insert("insert into table_bureaudirection(bdId,address,csPort,voicePort,bdName) values(#{bdId},#{address},#{csPort},#{voicePort},#{bdName})")
    int insertBD(BD bd);

    @Update("update table_bureaudirection set bdId=#{bdId},address=#{address},csPort=#{csPort},voicePort=#{voicePort},bdName=#{bdName} where bdId=#{oldBdId}")
    int updateBD(BD bd);

    @Delete("delete from table_bureaudirection where bdId=#{bdId}")
    int deleteBD(String bdId);
}
