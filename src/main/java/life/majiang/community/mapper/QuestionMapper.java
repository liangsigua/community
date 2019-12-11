package life.majiang.community.mapper;

import life.majiang.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    public void create(Question question);

    @Select("select * from question limit #{offset}, #{size}")
    List<Question> list(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select count(1) from question where creator=#{id}")
    Integer countByUserId(@Param(value = "id") Long id);

    @Select("select * from question where creator=#{id} limit #{offset}, #{size}")
    List<Question> listByUserId(@Param(value = "id") Long id, @Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select * from question where id=#{id}")
    Question getById(@Param("id") Long id);

    @Update("update question set title=#{title}, description=#{description}, gmt_modified=#{gmtModified}, tag=#{tag} where id=#{id}")
    void update(Question question);

    @Update("update question set view_count= view_count + 1 where id=#{id}")
    void updateByViewCount(@Param("id") Long id);

    @Update("update question set comment_count= comment_count + 1 where id=#{id}")
    void updateByCommentCount(@Param("id") Long id);

    @Select("select * from question where id != #{id} and tag regexp #{tag}")
    List<Question> selectRelated(@Param("id")Long id, @Param("tag")String tag);
}
