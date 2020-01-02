package life.majiang.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND(2001, "这个问题不存在！"),
    TARGET_PARAM_NOT_FOUND(2002, "回复问题不存在！"),
    NO_LOGIN(2003, "未登录，请登录"),
    SYS_ERROR(2004, "服务冒烟了，要不然你稍后再试试！"),
    TYPE_PARAM_WRONG(2005, "评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006, "这个评论不存在"),
    CONTENT_IS_EMPTY(2007, "回复的评论不能为空"),
    READ_NOTIFICATION_FAIL(2008, "这不是你的消息"),
    NOTIFICATION_NOT_FOUND(2009, "消息不见了！");


    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
