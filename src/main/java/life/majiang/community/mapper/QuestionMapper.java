package life.majiang.community.mapper;

import life.majiang.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    public void create(Question question);

    //根据分页栏规律（0，5）来查询出每一页的数据
    @Select("select * from question limit #{offset}, #{size}")
    List<Question> list(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);
    //使用搜索功能来根据分页栏规律（0，5）来查询出每一页的数据
    @Select("select * from question where title regexp #{search} limit #{offset}, #{size}")
    List<Question> listBySearch(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size, @Param(value = "search") String search);
    //使用热门话题功能来根据分页栏规律（0，5）来查询出每一页的数据
    @Select("select * from question where tag regexp #{tag} limit #{offset}, #{size}")
    List<Question> listByTag(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size, @Param(value = "tag") String tag);

    //总数量（分页栏要使用的总数量）
    @Select("select count(*) from question")
    Integer count();
    //搜索功能搜索关键字得出的总数量（分页栏要使用的总数量）
    @Select("select count(*) from question where title regexp #{search}")
    Integer countBySearch(@Param(value = "search") String search);
    //热门话题功能搜索关键字得出的总数量（分页栏要使用的总数量）
    @Select("select count(*) from question where tag regexp #{tag}")
    Integer countByTag(@Param(value = "tag") String tag);

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
