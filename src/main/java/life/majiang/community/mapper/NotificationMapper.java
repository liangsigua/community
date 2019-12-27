package life.majiang.community.mapper;

import life.majiang.community.model.Notification;
import life.majiang.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NotificationMapper {

    @Insert("insert into notification (notifier, receiver, outerId, type, gmt_create, status, notifier_name, outer_title) values (#{notifier}, #{receiver}, #{outerId}, #{type}, #{gmtCreate}, #{status}, #{notifierName}, #{outerTitle})")
    void insert(Notification notification);

//    @Select("select * from notification")
//    List<Notification> select();

    //通过查询得到我的通知的总数
    @Select("select count(1) from notification where receiver=#{id}")
    Integer countById(@Param(value = "id") Long id);

    @Select("select * from notification where receiver=#{id} limit #{offset}, #{size}")
    List<Notification> listByUserId(@Param(value = "id") Long id, @Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

}
