package com.studyonline2.www.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.studyonline2.www.dto.UserDTO;
import com.studyonline2.www.model.domain.Friend;

import java.util.List;

public interface FriendService extends IService<Friend> {
    Boolean apply(Integer userid, Integer addid);

    Boolean agree(Integer userid, Integer applyid);

    Boolean refuse(Integer userid, Integer applyid);

    Boolean delete(Integer userid, Integer deleteid);

    Long getApplyNum(Integer userid);

    List<UserDTO> getfriends(Integer userid);

    List<UserDTO> showApplyList(Integer userid);
}
