package com.demo.dao;

import com.demo.model.Face;
import com.demo.model.Frame;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface FaceDao {
    @Insert("insert into face(id, left, top, width, height, rotation, frameId)" +
            " values (#{id}, #{left}, #{top}, #{width}, #{height}, #{rotation}, #{frameId})")
    int insert(Face face);

    @Select("SELECT * FROM face WHERE frameId = #{id}")
    List<Face> select(int id);

    @Delete("delete from face where frameId = #{id}")
    void delete(int frameId);
}
