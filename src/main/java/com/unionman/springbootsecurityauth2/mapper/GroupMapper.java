package com.unionman.springbootsecurityauth2.mapper;

import com.unionman.springbootsecurityauth2.entity.Group;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface GroupMapper {
    @Select("<script>" +
            "select groupId from table_group where 1=1 " +
            "<if test=\"power != null and power != -1\">" +
            "and power in " +
            "<foreach collection=\"strList\" index=\"index\" item=\"id\" open=\"(\" separator=\",\" close=\")\">"+
            "#{id}"+
            "</foreach>"+
            "</if>" +
            "</script>")
    List<String> selectManageGroupId(Map<String,Object> param);

    @Select("select * from table_group where groupId = #{groupId}")
    Group findGroupById(String groupId);

    @Select("<script>" +
            "select * from table_group where power in " +
            "<foreach collection=\"strList\" index=\"index\" item=\"id\" open=\"(\" separator=\",\" close=\")\">"+
            "#{id}"+
            "</foreach>"+
            "<if test=\"groupId != null and groupId != ''\">" +
            "and groupId like concat('%',#{groupId},'%')" +
            "</if>" +
            "<if test=\"groupName != null and groupName != ''\">" +
            "and groupName like concat('%',#{groupName},'%')" +
            "</if>" +
            "<if test=\"type != null and type != -1\">" +
            "and type like concat('%',#{type},'%')" +
            "</if>" +
            "<if test=\"start != null and start != -1\">" +
            "limit #{start},#{limit}"+
            "</if>" +
            "</script>")
    List<Group> selectGroupList(Map<String, Object> param);

    @Select("<script>" +
            "select count(*) from table_group where power in " +
            "<foreach collection=\"strList\" index=\"index\" item=\"id\" open=\"(\" separator=\",\" close=\")\">"+
            "#{id}"+
            "</foreach>"+
            "<if test=\"groupId != null and groupId != ''\">" +
            "and groupId like concat('%',#{groupId},'%')" +
            "</if>" +
            "<if test=\"groupName != null and groupName != ''\">" +
            "and groupName like concat('%',#{groupName},'%')" +
            "</if>" +
            "<if test=\"type != null and type != -1\">" +
            "and type like concat('%',#{type},'%')" +
            "</if>" +
            "</script>")
    int selectGroupListCount(Map<String, Object> param);

    @Insert("insert into table_group(groupId,groupName,type,pttSilentTime,callTime,pttOnTime,power) values(#{groupId},#{groupName},#{type},#{pttSilentTime},#{callTime},#{pttOnTime},#{power})")
    int insertGroup(Group group);

    @Insert("<script>" +
            "insert into table_group(groupId" +
            "<if test=\"groupName != null\">,groupName</if>" +
            "<if test=\"type != -1\">,type</if>" +
            "<if test=\"pttSilentTime != -1\">,pttSilentTime</if>" +
            "<if test=\"callTime != -1\">,callTime</if>" +
            "<if test=\"pttOnTime != -1\">,pttOnTime</if>" +
            "<if test=\"power != -1\">,power</if>" +
            ") values" +
            "<foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\">" +
            "(#{item.groupId}" +
            "<if test=\"groupName != null\">,#{item.groupName}</if>" +
            "<if test=\"type != -1\">,#{item.type}</if>" +
            "<if test=\"pttSilentTime != -1\">,#{item.pttSilentTime}</if>" +
            "<if test=\"callTime != -1\">,#{item.callTime}</if>" +
            "<if test=\"pttOnTime != -1\">,#{item.pttOnTime}</if>" +
            "<if test=\"power != -1\">,#{item.power}</if>" +
            ")" +
            "</foreach>" +
            "</script>")
    int insertGroupBatch(Map<String, Object> param);

    @Update("update table_group set groupId=#{groupId},groupName=#{groupName},type=#{type},pttSilentTime=#{pttSilentTime},callTime=#{callTime},pttOnTime=#{pttOnTime},power=#{power} where groupId=#{oldGroupId}")
    int updateGroup(Group group);

    @Update("<script>" +
            "update table_group set groupId=groupId" +
            "<if test=\"groupName != null\">,groupName=#{groupName}</if>" +
            "<if test=\"type != -1\">,type=#{type}</if>" +
            "<if test=\"pttSilentTime != -1\">,pttSilentTime=#{pttSilentTime}</if>" +
            "<if test=\"callTime != -1\">,callTime=#{callTime}</if>" +
            "<if test=\"pttOnTime != -1\">,pttOnTime=#{pttOnTime}</if>" +
            "<if test=\"power != -1\">,power=#{power}</if>" +
            "where groupId in" +
            "<foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\" open=\"(\" close=\")\">" +
            "#{item}" +
            "</foreach>" +
            "</script>")
    int updateGroupBatch(Map<String, Object> param);

    @Delete("delete from table_group where groupId=#{groupId}")
    int deleteGroup(String groupId);

    @Delete("<script>" +
            "delete from table_group where groupId in" +
            "<foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\" open=\"(\" close=\")\">" +
            "#{item}" +
            "</foreach>" +
            "</script>")
    int deleteGroupBatch(Map<String, Object> param);
}
