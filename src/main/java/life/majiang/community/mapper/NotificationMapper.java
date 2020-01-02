package life.majiang.community.mapper;

import life.majiang.community.model.Notification;
import life.majiang.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotificationMapper {

    @Insert("insert into notification (notifier, receiver, outerId, type, gmt_create, status, notifier_name, outer_title) values (#{notifier}, #{receiver}, #{outerId}, #{type}, #{gmtCreate}, #{status}, #{notifierName}, #{outerTitle})")
    void insert(Notification notification);

    //当前用户的未读消息
    @Select("select count(1) from notification where status=0 and receiver = #{userId}")
    Long unreadCount(@Param(value = "userId") Long userId);

    //通过查询得到我的通知的总数
    @Select("select count(1) from notification where receiver=#{id}")
    Integer countById(@Param(value = "id") Long id);

    @Select("select * from notification where id=#{id}")
    Notification selectById(@Param(value = "id") Long id);

    //通过分页规律（0，5）和用户名来倒序排序得出我的所有数据
    @Select("select * from notification where receiver=#{id} order by gmt_create desc limit #{offset}, #{size}")
    List<Notification> listByUserId(@Param(value = "id") Long id, @Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    //通过设置status为1来把数据更新为已读
    @Update("update notification set status=#{status} where id=#{id}")
    void updateStatus(Notification notification);
}
