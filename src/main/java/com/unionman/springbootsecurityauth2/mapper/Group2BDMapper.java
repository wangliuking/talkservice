package com.unionman.springbootsecurityauth2.mapper;

import com.unionman.springbootsecurityauth2.entity.Group2BD;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface Group2BDMapper {
    @Select("select count(*) from table_group2bd where groupId = #{groupId}")
    int findIsExistGroupId(String groupId);

    @Select("select count(*) from table_group2bd where bdId = #{bdId}")
    int findIsExistBdId(String bdId);

    @Select("select * from table_group2bd where bdId = #{bdId} and groupId = #{groupId}")
    Group2BD findGroup2BDById(Map<String, Object> param);

    @Select("<script>" +
            "select a.*,b.bdName,c.groupName from table_group2bd a LEFT JOIN table_bureaudirection b on a.bdId=b.bdId LEFT JOIN table_group c on a.groupId=c.groupId where 1=1" +
            "<if test=\"bdId != null and bdId != ''\">" +
            "and a.bdId like concat('%',#{bdId},'%')" +
            "</if>" +
            "<if test=\"bdName != null and bdName != ''\">" +
            "and b.bdName like concat('%',#{bdName},'%')" +
            "</if>" +
            "<if test=\"groupId != null and groupId != ''\">" +
            "and a.groupId like concat('%',#{groupId},'%')" +
            "</if>" +
            "<if test=\"groupName != null and groupName != ''\">" +
            "and c.groupName like concat('%',#{groupName},'%')" +
            "</if>" +
            "<if test=\"start != null and start != -1\">" +
            "limit #{start},#{limit}"+
            "</if>" +
            "</script>")
    List<Group2BD> selectGroup2BDList(Map<String, Object> param);

    @Select("<script>" +
            "select count(*) from table_group2bd a LEFT JOIN table_bureaudirection b on a.bdId=b.bdId LEFT JOIN table_group c on a.groupId=c.groupId where 1=1" +
            "<if test=\"bdId != null and bdId != ''\">" +
            "and a.bdId like concat('%',#{bdId},'%')" +
            "</if>" +
            "<if test=\"bdName != null and bdName != ''\">" +
            "and b.bdName like concat('%',#{bdName},'%')" +
            "</if>" +
            "<if test=\"groupId != null and groupId != ''\">" +
            "and a.groupId like concat('%',#{groupId},'%')" +
            "</if>" +
            "<if test=\"groupName != null and groupName != ''\">" +
            "and c.groupName like concat('%',#{groupName},'%')" +
            "</if>" +
            "</script>")
    int selectGroup2BDListCount(Map<String, Object> param);

    @Insert("insert into table_group2bd(bdId,groupId) values(#{bdId},#{groupId})")
    int insertGroup2BD(Group2BD group2BD);

    @Update("update table_group2bd set bdId=#{bdId},groupId=#{groupId} where bdId=#{oldBdId} and groupId=#{oldGroupId}")
    int updateGroup2BD(Group2BD group2BD);

    @Delete("delete from table_group2bd where bdId=#{bdId} and groupId=#{groupId}")
    int deleteGroup2BD(Map<String, Object> param);
}
