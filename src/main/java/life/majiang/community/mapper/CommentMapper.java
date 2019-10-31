package life.majiang.community.mapper;

import life.majiang.community.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {

    @Insert("insert into comment(parent_id,type,commentator,content,gmt_create,gmt_modified,like_count) values (#{parentId},#{type},#{commentator},#{content},#{gmtCreate},#{gmtModified},#{likeCount})")
    void insert(Comment comment);
}
