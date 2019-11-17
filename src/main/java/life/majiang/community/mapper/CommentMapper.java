package life.majiang.community.mapper;

import life.majiang.community.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Insert("insert into comment(parent_id,type,commentator,content,gmt_create,gmt_modified,like_count) values (#{parentId},#{type},#{commentator},#{content},#{gmtCreate},#{gmtModified},#{likeCount})")
    void insert(Comment comment);

    @Select("select * from comment where id=#{id}")
    Comment getById(@Param("id") Long id);

    @Select("select * from comment where parent_id=#{parentId}")
    List<Comment> getAllById(@Param("parentId") Long id);
}
