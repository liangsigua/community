package life.majiang.community.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//接受Github回调的地址，获取地址里的code和state
@Controller
public class AuthorizeController {
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state){
        return "index";
    }
}
