package com.unionman.springbootsecurityauth2.mapper;

import com.unionman.springbootsecurityauth2.entity.User;
import com.unionman.springbootsecurityauth2.entity.WebUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserMapper {
    @Select("<script>" +
            "select userId from table_user where 1=1 " +
            "<if test=\"power != null and power != -1\">" +
            "and power in " +
            "<foreach collection=\"strList\" index=\"index\" item=\"id\" open=\"(\" separator=\",\" close=\")\">"+
            "#{id}"+
            "</foreach>"+
            "</if>" +
            "</script>")
    List<String> selectManageUserId(Map<String,Object> param);

    @Select("select * from table_user where userId = #{username}")
    User findUserById(String username);

    @Select("select * from table_webuser where userId = #{username}")
    WebUser findWebUserById(String username);

    @Select("<script>" +
            "select * from table_user where power in " +
            "<foreach collection=\"strList\" index=\"index\" item=\"id\" open=\"(\" separator=\",\" close=\")\">"+
            "#{id}"+
            "</foreach>"+
            "<if test=\"userId != null and userId != ''\">" +
            "and userId like concat('%',#{userId},'%')" +
            "</if>" +
            "<if test=\"userName != null and userName != ''\">" +
            "and userName like concat('%',#{userName},'%')" +
            "</if>" +
            "<if test=\"power != null and power != -1\">" +
            "and power like concat('%',#{power},'%')" +
            "</if>" +
            "<if test=\"start != null and start != -1\">" +
            "limit #{start},#{limit}" +
            "</if>" +
            "</script>")
    List<User> selectUserList(Map<String, Object> param);

    @Select("<script>" +
            "select count(*) from table_user where power in " +
            "<foreach collection=\"strList\" index=\"index\" item=\"id\" open=\"(\" separator=\",\" close=\")\">"+
            "#{id}"+
            "</foreach>"+
            "<if test=\"userId != null and userId != ''\">" +
            "and userId like concat('%',#{userId},'%')" +
            "</if>" +
            "<if test=\"userName != null and userName != ''\">" +
            "and userName like concat('%',#{userName},'%')" +
            "</if>" +
            "<if test=\"power != null and power != -1\">" +
            "and power like concat('%',#{power},'%')" +
            "</if>" +
            "</script>")
    int selectUserListCount(Map<String, Object> param);

    @Select("<script>" +
            "select * from table_webuser where power in " +
            "<foreach collection=\"strList\" index=\"index\" item=\"id\" open=\"(\" separator=\",\" close=\")\">"+
            "#{id}"+
            "</foreach>"+
            "<if test=\"userId != null and userId != ''\">" +
            "and userId like concat('%',#{userId},'%')" +
            "</if>" +
            "<if test=\"userName != null and userName != ''\">" +
            "and userName like concat('%',#{userName},'%')" +
            "</if>" +
            "<if test=\"power != null and power != -1\">" +
            "and power like concat('%',#{power},'%')" +
            "</if>" +
            "<if test=\"start != null and start != -1\">" +
            "limit #{start},#{limit}" +
            "</if>" +
            "</script>")
    List<WebUser> selectWebUserList(Map<String, Object> param);

    @Select("<script>" +
            "select count(*) from table_webuser where power in " +
            "<foreach collection=\"strList\" index=\"index\" item=\"id\" open=\"(\" separator=\",\" close=\")\">"+
            "#{id}"+
            "</foreach>"+
            "<if test=\"userId != null and userId != ''\">" +
            "and userId like concat('%',#{userId},'%')" +
            "</if>" +
            "<if test=\"userName != null and userName != ''\">" +
            "and userName like concat('%',#{userName},'%')" +
            "</if>" +
            "<if test=\"power != null and power != -1\">" +
            "and power like concat('%',#{power},'%')" +
            "</if>" +
            "</script>")
    int selectWebUserListCount(Map<String, Object> param);

    @Insert("insert into table_user(userId,userName,password,authenticateCode,priority,type,scanEn,gpsInterval,externalVideoViewEn,power,userBook) values(#{userId},#{userName},#{password},#{authenticateCode},#{priority},#{type},#{scanEn},#{gpsInterval},#{externalVideoViewEn},#{power},#{userBook})")
    int insertUser(User user);

    @Insert("insert into table_webuser(userId,userName,password,power,type) values(#{userId},#{userName},#{password},#{power},#{type})")
    int insertWebUser(WebUser webUser);

    @Insert("<script>" +
            "insert into table_user(userId" +
            "<if test=\"userName != null\">,userName</if>" +
            "<if test=\"password != null\">,password</if>" +
            "<if test=\"authenticateCode != null\">,authenticateCode</if>" +
            "<if test=\"priority != -1\">,priority</if>" +
            "<if test=\"type != -1\">,type</if>" +
            "<if test=\"scanEn != -1\">,scanEn</if>" +
            "<if test=\"gpsInterval != -1\">,gpsInterval</if>" +
            "<if test=\"externalVideoViewEn != -1\">,externalVideoViewEn</if>" +
            "<if test=\"power != -1\">,power</if>" +
            "<if test=\"userBook != -1\">,userBook</if>" +
            ") values" +
            "<foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\">" +
            "(#{item.userId}" +
            "<if test=\"userName != null\">,#{item.userName}</if>" +
            "<if test=\"password != null\">,#{item.password}</if>" +
            "<if test=\"authenticateCode != null\">,#{item.authenticateCode}</if>" +
            "<if test=\"priority != -1\">,#{item.priority}</if>" +
            "<if test=\"type != -1\">,#{item.type}</if>" +
            "<if test=\"scanEn != -1\">,#{item.scanEn}</if>" +
            "<if test=\"gpsInterval != -1\">,#{item.gpsInterval}</if>" +
            "<if test=\"externalVideoViewEn != -1\">,#{item.externalVideoViewEn}</if>" +
            "<if test=\"power != -1\">,#{item.power}</if>" +
            "<if test=\"userBook != -1\">,#{item.userBook}</if>" +
            ")" +
            "</foreach>" +
            "</script>")
    int insertUserBatch(Map<String, Object> param);

    @Update("update table_user set userId=#{userId},userName=#{userName},password=#{password},authenticateCode=#{authenticateCode},priority=#{priority},type=#{type},scanEn=#{scanEn},gpsInterval=#{gpsInterval},externalVideoViewEn=#{externalVideoViewEn},power=#{power},userBook=#{userBook} where userId=#{oldUserId}")
    int updateUser(User user);

    @Update("<script>" +
            "update table_webuser set userId=#{userId},userName=#{userName},power=#{power},type=#{type}" +
            "<if test=\"password != null and password != ''\">,password=#{password}</if>" +
            "where userId=#{oldUserId}"+
            "</script>")
    int updateWebUser(WebUser webUser);

    @Update("<script>" +
            "update table_user set userId=userId" +
            "<if test=\"userName != null\">,userName=#{userName}</if>" +
            "<if test=\"password != null\">,password=#{password}</if>" +
            "<if test=\"authenticateCode != null\">,authenticateCode=#{authenticateCode}</if>" +
            "<if test=\"priority != -1\">,priority=#{priority}</if>" +
            "<if test=\"type != -1\">,type=#{type}</if>" +
            "<if test=\"scanEn != -1\">,scanEn=#{scanEn}</if>" +
            "<if test=\"gpsInterval != -1\">,gpsInterval=#{gpsInterval}</if>" +
            "<if test=\"externalVideoViewEn != -1\">,externalVideoViewEn=#{externalVideoViewEn}</if>" +
            "<if test=\"power != -1\">,power=#{power}</if>" +
            "<if test=\"userBook != -1\">,userBook=#{userBook}</if>" +
            "where userId in" +
            "<foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\" open=\"(\" close=\")\">" +
            "#{item}" +
            "</foreach>" +
            "</script>")
    int updateUserBatch(Map<String, Object> param);

    @Delete("delete from table_user where userId=#{userId}")
    int deleteUser(String userId);

    @Delete("delete from table_webuser where userId=#{userId}")
    int deleteWebUser(String userId);

    @Delete("<script>" +
            "delete from table_user where userId in" +
            "<foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\" open=\"(\" close=\")\">" +
            "#{item}" +
            "</foreach>" +
            "</script>")
    int deleteUserBatch(Map<String, Object> param);
}
