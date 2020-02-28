package com.unionman.springbootsecurityauth2.mapper;

import com.unionman.springbootsecurityauth2.entity.Node;
import com.unionman.springbootsecurityauth2.entity.NodeEchart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserBookMapper {
    @Select("select * from table_userbook where id = #{id}")
    Map<String,Object> findUserBookById(int id);

    @Select("<script>" +
            "select * from table_userbook where 1=1 " +
            "<if test=\"name != null and name != ''\">" +
            "and name like concat('%',#{name},'%')" +
            "</if>" +
            "<if test=\"start != null and start != -1\">" +
            "limit #{start},#{limit}" +
            "</if>" +
            "</script>")
    List<Map<String,Object>> selectUserBookList(Map<String, Object> param);

    @Select("<script>" +
            "select count(*) from table_userbook where 1=1 " +
            "<if test=\"name != null and name != ''\">" +
            "and name like concat('%',#{name},'%')" +
            "</if>" +
            "</script>")
    int selectUserBookListCount(Map<String, Object> param);

    @Insert("insert into table_userbook(id,name) values(#{id},#{name})")
    int insertUserBook(Map<String, Object> param);

    @Update("update table_userbook set name=#{name} where id=#{id}")
    int updateUserBook(Map<String,Object> param);

    @Delete("delete from table_userbook where id=#{id}")
    int deleteUserBook(int id);

    @Select("<script>" +
            "select a.*,b.userName from table_userbook_detail a left join table_user b on a.userId=b.userId where id=#{id}" +
            "</script>")
    List<Map<String,Object>> selectUserBookDetail(int id);

    @Delete("delete from table_userbook_detail where id=#{id}")
    int deleteUserBookDetail(int id);

    @Insert("<script>" +
            "insert into table_userbook_detail(id,userId) values" +
            "<foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\">" +
            "(#{item.id},#{item.userId})" +
            "</foreach>" +
            "</script>")
    int insertUserBookDetail(Map<String, Object> param);
}
