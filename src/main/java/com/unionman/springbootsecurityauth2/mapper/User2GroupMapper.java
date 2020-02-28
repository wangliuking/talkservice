package com.unionman.springbootsecurityauth2.mapper;

import com.unionman.springbootsecurityauth2.entity.User2Group;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public interface User2GroupMapper {
    @Select("select count(*) from table_user2group where userId = #{userId}")
    int findIsExistUserId(String userId);

    @Select("select count(*) from table_user2group where groupId = #{groupId}")
    int findIsExistGroupId(String groupId);

    @Select("select * from table_user2group")
    List<User2Group> selectUser2GroupId();

    @Select("select * from table_user2group where userId = #{userId} and groupId = #{groupId}")
    User2Group findUser2GroupById(Map<String, Object> param);

    @Select("<script>" +
            "select a.*,b.userName,c.groupName from table_user2group a LEFT JOIN table_user b on a.userId=b.userId LEFT JOIN table_group c on a.groupId=c.groupId where b.power in " +
            "<foreach collection=\"strList\" index=\"index\" item=\"id\" open=\"(\" separator=\",\" close=\")\">"+
            "#{id}"+
            "</foreach>"+
            "<if test=\"userId != null and userId != ''\">" +
            "and a.userId like concat('%',#{userId},'%')" +
            "</if>" +
            "<if test=\"userName != null and userName != ''\">" +
            "and b.userName like concat('%',#{userName},'%')" +
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
    List<User2Group> selectUser2GroupList(Map<String, Object> param);

    @Select("<script>" +
            "select count(*) from table_user2group a LEFT JOIN table_user b on a.userId=b.userId LEFT JOIN table_group c on a.groupId=c.groupId where b.power in " +
            "<foreach collection=\"strList\" index=\"index\" item=\"id\" open=\"(\" separator=\",\" close=\")\">"+
            "#{id}"+
            "</foreach>"+
            "<if test=\"userId != null and userId != ''\">" +
            "and a.userId like concat('%',#{userId},'%')" +
            "</if>" +
            "<if test=\"userName != null and userName != ''\">" +
            "and b.userName like concat('%',#{userName},'%')" +
            "</if>" +
            "<if test=\"groupId != null and groupId != ''\">" +
            "and a.groupId like concat('%',#{groupId},'%')" +
            "</if>" +
            "<if test=\"groupName != null and groupName != ''\">" +
            "and c.groupName like concat('%',#{groupName},'%')" +
            "</if>" +
            "</script>")
    int selectUser2GroupListCount(Map<String, Object> param);

    @Insert("insert into table_user2group(userId,groupId) values(#{userId},#{groupId})")
    int insertUser2Group(User2Group user2Group);

    @Update("update table_user2group set userId=#{userId},groupId=#{groupId} where userId=#{oldUserId} and groupId=#{oldGroupId}")
    int updateUser2Group(User2Group user2Group);

    @Delete("delete from table_user2group where userId=#{userId} and groupId=#{groupId}")
    int deleteUser2Group(Map<String, Object> param);

    @Insert("<script>" +
            "insert into table_user2group(userId,groupId) values" +
            "<foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\">" +
            "(#{item.userId},#{item.groupId})" +
            "</foreach>" +
            "</script>")
    int insertUser2GroupBatch(Map<String, Object> param);

    @Delete("<script>" +
            "delete from table_user2group where " +
            "<foreach collection=\"list\" item=\"item\" index=\"index\" separator=\"or\">" +
            "(userId=#{item.userId} and groupId=#{item.groupId})" +
            "</foreach>" +
            "</script>")
    int deleteUser2GroupBatch(Map<String, Object> param);
}
