package life.majiang.community.service;

import life.majiang.community.dto.CommentDTO;
import life.majiang.community.enums.CommentTypeEnum;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.mapper.CommentMapper;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Comment;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserMapper userMapper;
    @Transactional
    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExit(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if (comment.getType() == CommentTypeEnum.QUESTION.getType()){
            //回复问题
            Question question = questionMapper.getById(comment.getParentId());
            if (question == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            questionMapper.updateByCommentCount(question.getId());
        }else{
            //回复评论
            Comment dbComment = commentMapper.getById(comment.getParentId());
            if (dbComment == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
        }
    }

    public List<CommentDTO> listByQuestionId(Long id) {
        List<Comment> comments = commentMapper.getAllById(id);
        //Commentator = user.getId()
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (Comment comment : comments) {
            CommentDTO commentDTO = new CommentDTO();
            User user = userMapper.findById(comment.getCommentator());
            commentDTO.setId(comment.getId());
            commentDTO.setParentId(comment.getParentId());
            commentDTO.setType(comment.getType());
            commentDTO.setCommentator(comment.getCommentator());
            commentDTO.setContent(comment.getContent());
            commentDTO.setGmtCreate(comment.getGmtCreate());
            commentDTO.setGmtModified(comment.getGmtModified());
            commentDTO.setLikeCount(comment.getLikeCount());
            commentDTO.setUser(user);
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }
}
