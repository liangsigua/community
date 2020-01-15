package life.majiang.community.enums;

import lombok.Getter;

@Getter
public enum  CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);
    private Integer type;

    CommentTypeEnum(Integer type) {
        this.type = type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public static boolean isExit(Integer type) {
        for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()) {
            if (commentTypeEnum.getType() == type){
                return true;
            }
        }
        return false;
    }
}
