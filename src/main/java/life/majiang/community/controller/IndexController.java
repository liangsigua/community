package life.majiang.community.controller;

import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request){
        //当用户再次登陆的时候，根据cookie里的token去数据库里查询是否有这个用户，有就把这个用户写入到session中
        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for(Cookie cookie: cookies){
                if (cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);   //如果数据库里没有这个User，还可以加入返回的校验信息
                    if (user != null){
                        //把user写入到session里，这样子前端就能通过user在页面右上角展示用户名信息还是登陆按钮
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }
        return "index";
    }
}
