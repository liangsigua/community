package life.majiang.community.mapper;

import life.majiang.community.model.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Insert("insert into comment(parent_id,type,commentator,content,gmt_create,gmt_modified,like_count) values (#{parentId},#{type},#{commentator},#{content},#{gmtCreate},#{gmtModified},#{likeCount})")
    void insert(Comment comment);

    @Select("select * from comment where id=#{id}")
    Comment getById(@Param("id") Long id);

    @Select("select * from comment where parent_id=#{parentId} and type=#{type} order by gmt_create desc")
    List<Comment> getAllById(@Param("parentId") Long id, @Param("type") Integer type);

    //当发布二级评论的时候，把一级回复评论的commentCount回复数加一
    @Update("update comment set comment_count= comment_count + 1 where id=#{id}")
    void updateByCommentCount(@Param("id") Long id);

    @Select("select * from comment where id=#{id}")
    Comment getByCommentator(@Param("id") Long id);
}
