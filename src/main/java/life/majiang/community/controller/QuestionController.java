package life.majiang.community.controller;

import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.mapper.CommentMapper;
import life.majiang.community.model.Comment;
import life.majiang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentMapper commentMapper;
    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Long id, Model model){
        questionService.incView(id);
        QuestionDTO questionDTO = questionService.getById(id);

        List<Comment> all = commentMapper.getAllById(id);
        model.addAttribute("questionDTO", questionDTO);
        model.addAttribute("commentAll", all);
        return "question";
    }
}
