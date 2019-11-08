package life.majiang.community.controller;

import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import life.majiang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private QuestionService questionService;

    //编辑页面
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id") Long id, Model model){
        //原本这里应该是直接通过questionMapper.getById(id)拿到question对象，但是为了统一只注入QuestionService，
        //所以改成了使用questionService.getById(id)，反正QuestionDTO对象也可以拿到title、description、tag
        //Question question = questionMapper.getById(id);
        QuestionDTO questionDTO = questionService.getById(id);
        model.addAttribute("title", questionDTO.getTitle());
        model.addAttribute("description", questionDTO.getDescription());
        model.addAttribute("tag", questionDTO.getTag());
        model.addAttribute("id", questionDTO.getId());
        return "publish";
    }

    //渲染页面
    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    //执行发起/提交请求
    @PostMapping("/publish")
    public String doPublish(@RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("tag") String tag,
                            @RequestParam("id") Long id,
                            HttpServletRequest request,
                            Model model) {

        //把数据放到model里，为下面校验不成功回显数据做准备
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);

        //校验输入内容
        if (title == null || title == ""){
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (description == null || description == ""){
            model.addAttribute("error", "问题描述不能为空");
            return "publish";
        }
        if (tag == null || tag == ""){
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }

        User user = (User)request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登陆");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(id);
        questionService.createOrUpdate(question);
        //如果成功，跳转到首页，把信息展示到首页去
        return "redirect:/";
    }
}
