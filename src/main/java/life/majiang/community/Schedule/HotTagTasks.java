package life.majiang.community.Schedule;

import life.majiang.community.cache.HotTagCache;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.model.Question;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

@Component
@Slf4j
public class HotTagTasks {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private HotTagCache hotTagCache;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 1000*60*60)
    public void reportCurrentTime() {
        int offset = 0;
        int limit = 50;
        List<Question> list = new ArrayList<>();
        log.info("start {}", dateFormat.format(new Date()));
        Map<String, Integer> priorities = new HashMap<>();
        while(offset == 0 || list.size() == limit) {
            list = questionMapper.list(offset, limit);
            for (Question question : list) {
                //从数据库里拿到问题的标签，通过逗号分隔出来
                String[] tags = StringUtils.split(question.getTag(), ",");
                //由于一个问题有多个标签，所以这里遍历标签组
                for (String tag : tags) {
                    Integer priority = priorities.get(tag);
                    if (priority != null){
                        //集合里的某标签的数量不为空，说明这个标签已有，那就加上 5 加上回复评论总数
                        priorities.put(tag, priority + 5 + question.getCommentCount());
                    }else{
                        //集合里的某标签的数量为空，说明这个标签还没有，就新建（比如：java标签为null，就新建java标签，数量为 5加上回复评论总数）
                        priorities.put(tag, 5 + question.getCommentCount());
                    }
                    //公式 = 标签的数量 + 5 + 回复评论总数
                }
            }
            offset += limit;
        }
//        priorities.forEach((k, v) ->{
//            System.out.print(k);
//            System.out.print(":");
//            System.out.print(v);
//            System.out.println();
//        });
        hotTagCache.updateTags(priorities);
        log.info("stop {}", dateFormat.format(new Date()));
    }
}
