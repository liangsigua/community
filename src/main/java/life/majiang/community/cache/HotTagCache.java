package life.majiang.community.cache;

import life.majiang.community.dto.HotDTO;
import life.majiang.community.dto.HotTagDTO;
import life.majiang.community.mapper.QuestionMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Data
public class HotTagCache {
    @Autowired
    private QuestionMapper questionMapper;
    private List<HotDTO> hots = new ArrayList<>();

    public void updateTags(Map<String, Integer> tags){
        int max = 3;
        PriorityQueue<HotTagDTO> priorityQueue = new PriorityQueue<>(max);

        tags.forEach((name, priority) ->{
            HotTagDTO hotTagDTO = new HotTagDTO();
            hotTagDTO.setName(name);
            hotTagDTO.setPriority(priority);
            if(priorityQueue.size()<3){
                priorityQueue.add(hotTagDTO);
            }else{
                HotTagDTO minHot = priorityQueue.peek();
                if(hotTagDTO.compareTo(minHot) > 0){
                    priorityQueue.poll();
                    priorityQueue.add(hotTagDTO);
                }
            }
        });

        List<HotDTO> sortedTags = new ArrayList<>();
        while(!priorityQueue.isEmpty()){
            HotTagDTO poll1 = priorityQueue.poll();
            if (poll1 != null){
                HotDTO hotDTO = new HotDTO();
                hotDTO.setName(poll1.getName());
                Integer tagPriority = questionMapper.countByTag(poll1.getName());
                hotDTO.setPriority(tagPriority);
                sortedTags.add(0, hotDTO);
            }
        }
        hots = sortedTags;
    }
}
