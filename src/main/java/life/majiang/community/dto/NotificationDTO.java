package life.majiang.community.dto;

import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private Long notifier;          //评论者，通知的人
    private String notifierName;    //评论人的名字
    private String outerTitle;      //外部标题的名字
    private Long outerId;
    private String typeName;        //"回复了问题","回复了评论"
    private Integer type;
}
