package life.majiang.community.service;

import life.majiang.community.dto.NotificationDTO;
import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.enums.NotificationStatusEnum;
import life.majiang.community.enums.NotificationTypeEnum;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.mapper.NotificationMapper;
import life.majiang.community.model.Notification;
import life.majiang.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;

    public PaginationDTO listByUserId(Long id, Integer page, Integer size) {
        //[Notification{id=1, notifier=451, receiver=451, outerId=97, type=1, gmtCreate=1577181124303, status=1}]
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = notificationMapper.countById(id);
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
        List<Notification> notificationList = notificationMapper.listByUserId(id, offset, size);

        if (notificationList.size() == 0){
            return paginationDTO;
        }

        List<NotificationDTO> notificationDTOList = new ArrayList<>();

        for (Notification notification : notificationList) {
            NotificationDTO notificationDTO = new NotificationDTO();
            String type = NotificationTypeEnum.nameOfType(notification.getType());
            notificationDTO.setTypeName(type);
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTOList.add(notificationDTO);
        }
        paginationDTO.setData(notificationDTOList);
        return paginationDTO;
    }

    public Long unread(Long userId) {
        Long count = notificationMapper.unreadCount(userId);
        return count;
    }

    //逻辑：把当前问题的status设置成已读，再存入到数据库里
    public Notification read(User user, Long id) {
        Notification notification = notificationMapper.selectById(id);
        if (notification == null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!Objects.equals(notification.getReceiver(), user.getId())){
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        //Notification{id=98, notifier=451, receiver=451, outerId=97, type=1, gmtCreate=1577932557442, status=0, notifierName='凉丝瓜', outerTitle='如何学习springboot？'}
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateStatus(notification);
        return notification;
    }
}
