package life.majiang.community.service;

import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    public PaginationDTO list(Integer page, Integer size, String search, String tag){
        if (StringUtils.isNotBlank(search)){
            String[] split = StringUtils.split(search, " ");
            search = Arrays.stream(split).collect(Collectors.joining("|"));
        }
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount;
        if (StringUtils.isNotBlank(search)){
            totalCount = questionMapper.countBySearch(search);
        }else if (StringUtils.isNotBlank(tag)) {
            totalCount = questionMapper.countByTag(tag);
        }else{
            totalCount = questionMapper.count();
        }
        Integer totalPage;
        //34除于5等于6页，还余4，所以共7页
        if (totalCount % size == 0){
            totalPage = totalCount/size;
        }else{
            totalPage = totalCount/size + 1;
        }
        //校验处理：当页码小于或大于已有页码，就以首页或未页来计算
        if (page < 1){
            page = 1;
        }
        if (page > totalPage){
            page = totalPage;
        }
        paginationDTO.setPagination(totalPage, page);

        //通过size*(page-1)来算出limit，应该查询出怎样的数据
        Integer offset = size * (page - 1);
        //之前是展示所有数据，现在要做分页功能，所以改成了一次展示5条数据
        List<Question> questionList;
        if (StringUtils.isNotBlank(search)){
            questionList = questionMapper.listBySearch(offset, size, search);
        }else if (StringUtils.isNotBlank(tag)) {
            questionList = questionMapper.listByTag(offset, size, tag);
        }else{
            questionList = questionMapper.list(offset, size);
        }
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questionList) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        //把遍历得到的数据赋值到PaginationDTO类里的List<QuestionDTO> questions里
        paginationDTO.setData(questionDTOList);
        return paginationDTO;
    }

    public PaginationDTO listByUserId(Long id, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.countByUserId(id);       //通过查询得到我的问题的数据总数
        Integer totalPage;
        if (totalCount % size == 0){
            totalPage = totalCount/size;
        }else{
            totalPage = totalCount/size + 1;
        }
        if (page < 1){
            page = 1;
        }
        if (page > totalPage){
            page = totalPage;
        }
        paginationDTO.setPagination(totalPage, page);

        Integer offset = size * (page - 1);
        List<Question> questionList = questionMapper.listByUserId(id, offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questionList) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);
        return paginationDTO;
    }

    public QuestionDTO getById(Long id) {
        QuestionDTO questionDTO = new QuestionDTO();
        Question question = questionMapper.getById(id);
        if (question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        User user = userMapper.findById(question.getCreator());
        BeanUtils.copyProperties(question, questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }

    /**
     * 根据问题的标签来关联出其它问题
     * @param questionDTO
     * @return
     */
    public List<Question> selectRelated(QuestionDTO questionDTO) {
        //判断问题的标签是否为空，但是这种情况不存在，因为我们前面就做了校验
        if(StringUtils.isBlank(questionDTO.getTag())){
            return new ArrayList<>();
        }
        //通过逗号来分隔标签里面的字符串
        String[] tags = StringUtils.split(questionDTO.getTag(), ",");
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        List<Question> questions = questionMapper.selectRelated(questionDTO.getId(), regexpTag);
        return questions;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null){
            //说明是创建问题
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.create(question);
        }else{
            //说明是修改问题
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.update(question);
        }
    }

    public void incView(Long id) {
//        Question question = questionMapper.getById(id);
//        question.setViewCount(question.getViewCount() + 1);
        questionMapper.updateByViewCount(id);
    }


}
