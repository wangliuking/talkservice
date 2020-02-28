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
public interface StructureMapper {
    @Select("select id,pId from structure")
    List<Map<String,Integer>> foreachIdAndPId();

    @Select("<script>"+
            "select * from structure where id in "+
            "<foreach collection=\"strList\" index=\"index\" item=\"id\" open=\"(\" separator=\",\" close=\")\">"+
            "#{id}"+
            "</foreach>"+
            "</script>")
    List<Node> selectAll(Map<String, Object> param);

    @Select("<script>"+
            "select id,pId,label name,level from structure where id in "+
            "<foreach collection=\"strList\" index=\"index\" item=\"id\" open=\"(\" separator=\",\" close=\")\">"+
            "#{id}"+
            "</foreach>"+
            "</script>")
    List<NodeEchart> treeEchart(Map<String, Object> param);

    @Insert("insert into structure(pId,level,label) values(#{id},#{level}+1,#{label})")
    int insert(Node node);

    @Delete("<script>" +
            "delete from structure where id in "+
            "<foreach collection=\"strList\" index=\"index\" item=\"id\" open=\"(\" separator=\",\" close=\")\">"+
            "#{id}"+
            "</foreach>"+
            "</script>")
    int delete(Map<String, Object> param);

    @Update("update structure set label=#{label} where id=#{id}")
    int update(Map<String, Object> param);
}
