package life.majiang.community.dto;

import lombok.Data;

@Data
//access_token接口的参数
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
