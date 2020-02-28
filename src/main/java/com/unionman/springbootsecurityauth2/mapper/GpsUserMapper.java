package com.unionman.springbootsecurityauth2.mapper;

import com.unionman.springbootsecurityauth2.entity.GpsUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface GpsUserMapper {

    @Select("select * from table_gpsUser where gpsUserId = #{gpsUserId}")
    GpsUser findGpsUserById(int gpsUserId);

    @Select("<script>" +
            "select * from table_gpsUser where 1=1" +
            "<if test=\"gpsUserId != null and gpsUserId != -1\">" +
            "and gpsUserId like concat('%',#{gpsUserId},'%')" +
            "</if>" +
            "<if test=\"start != null and start != -1\">" +
            "limit #{start},#{limit}"+
            "</if>" +
            "</script>")
    List<GpsUser> selectGpsUserList(Map<String, Object> param);

    @Select("<script>" +
            "select count(*) from table_gpsUser where 1=1" +
            "<if test=\"gpsUserId != null and gpsUserId != -1\">" +
            "and gpsUserId like concat('%',#{gpsUserId},'%')" +
            "</if>" +
            "</script>")
    int selectGpsUserListCount(Map<String, Object> param);

    @Insert("insert into table_gpsUser(gpsUserId,gpsUserPassword) values(#{gpsUserId},#{gpsUserPassword})")
    int insertGpsUser(GpsUser GpsUser);

    @Update("update table_gpsUser set gpsUserId=#{gpsUserId},gpsUserPassword=#{gpsUserPassword} where gpsUserId=#{oldGpsUserId}")
    int updateGpsUser(GpsUser GpsUser);

    @Delete("delete from table_gpsUser where gpsUserId=#{gpsUserId}")
    int deleteGpsUser(int GpsUserId);
}
