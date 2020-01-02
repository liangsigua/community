package life.majiang.community.controller;

import life.majiang.community.dto.NotificationDTO;
import life.majiang.community.model.Notification;
import life.majiang.community.model.User;
import life.majiang.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(@PathVariable("id") Long id, HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if (user == null){
            return "redirect:/";
        }
        //逻辑：把当前问题的status设置成已读，再存入到数据库里，再跳转到questionController
        Notification notification = notificationService.read(user, id);
        return "redirect:/question/" + notification.getOuterId();
    }
}
