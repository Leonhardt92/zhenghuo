package com.demo.dao;

import com.demo.model.Vedio;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface VedioDao {
    @Insert("insert into vedio(soundPath, name) VALUES (#{soundPath}, #{name})")
    @SelectKey(statement = "select last_insert_rowid()", keyProperty = "id", before = false, resultType = int.class)
    int insert(Vedio user);

    @Select("select count(distinct fa.id)\n" +
            "from face fa\n" +
            "inner join frame fr on fr.id=fa.frameId\n" +
            "inner join vedio v on v.id=fr.vedioId\n" +
            "where v.name=#{name}")
    int countFaces(String name);

    @Select("SELECT * FROM vedio WHERE name = #{name}")
    Vedio selectByName(String name);

    @Select("SELECT name FROM vedio")
    List<String> getAllVedios();

    @Select("select context\n" +
            "from vedio v\n" +
            "inner join subtitles s on s.vedioid=v.id\n" +
            "where v.name=#{name}\n" +
            "order by s.seq")
    List<String> getSubtitles(String name);

    @Delete("delete from vedio where id = #{id}")
    void delete(int id);
}
