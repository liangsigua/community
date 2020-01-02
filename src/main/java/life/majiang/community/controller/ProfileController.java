package life.majiang.community.controller;

import life.majiang.community.dto.NotificationDTO;
import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.enums.NotificationTypeEnum;
import life.majiang.community.mapper.CommentMapper;
import life.majiang.community.mapper.NotificationMapper;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Comment;
import life.majiang.community.model.Notification;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import life.majiang.community.service.NotificationService;
import life.majiang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProfileController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{active}")
    public String profile(@PathVariable(value = "active") String active,
                          HttpServletRequest request, Model model,
                          @RequestParam(value = "page", defaultValue = "1") Integer page,
                          @RequestParam(value = "size", defaultValue = "5") Integer size) {
        User user = (User)request.getSession().getAttribute("user");
        if (user == null){
            return "redirect:/";
        }
        if ("questions".equals(active)){
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的问题");
            //获取“我的问题”列表数据并返回给前端
            PaginationDTO paginationDTO = questionService.listByUserId(user.getId(), page, size);
            model.addAttribute("paginationDTO", paginationDTO);
        }else if ("replies".equals(active)){
            //需要查询问题和分页栏数据
            PaginationDTO paginationDTO = notificationService.listByUserId(user.getId(), page, size);
            model.addAttribute("paginationDTO", paginationDTO);

            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回答");
        }
        return "profile";
    }
}
