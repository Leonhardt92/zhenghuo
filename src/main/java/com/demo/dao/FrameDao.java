package com.demo.dao;

import com.demo.model.FaceSO;
import com.demo.model.Frame;
import com.demo.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface FrameDao {
    @Insert("insert into frame(path, vedioId, seq) VALUES (#{path}, #{vedioId}, #{seq})")
    @SelectKey(statement="select last_insert_rowid()", keyProperty="id", before=false, resultType=int.class)
    int insert(Frame frame);

    @Select("SELECT * FROM frame WHERE vedioId = #{id}")
    List<Frame> selectByVedio(int id);

    @Delete("delete from frame where vedioId = #{id}")
    void deleteByVedioId(int id);

    List<FaceSO> findFaces(int vedioId);
}
