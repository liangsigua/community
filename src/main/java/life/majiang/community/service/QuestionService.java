package life.majiang.community.service;

import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    public PaginationDTO list(Integer page, Integer size){
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.count();              //通过查询得到question表的数据总数
        paginationDTO.setPagination(totalCount, page, size);
        //校验处理：当页码小于或大于已有页码，就以首页或未页来计算
        if (page < 1){
            page = 1;
        }
        //page值大于最后一页的逻辑处理
        if (page > paginationDTO.getTotalPage()){
            page = paginationDTO.getTotalPage();
        }
        //通过size*(page-1)来算出limit，应该查询出怎样的数据
        Integer offset = size * (page - 1);
        //之前是展示所有数据，现在要做分页功能，所以改成了一次展示5条数据
        List<Question> questionList = questionMapper.list(offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questionList) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        //把遍历得到的数据赋值到PaginationDTO类里的List<QuestionDTO> questions里
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }
}
