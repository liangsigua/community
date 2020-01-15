package life.majiang.community.dto;

import lombok.Data;

@Data
//provider的返回值
public class GithubUser {
    private String name;
    private Long id;     //long类型：除了支持github，还打算支持其它平台
    private String bio;
    private String avatarUrl;
}
