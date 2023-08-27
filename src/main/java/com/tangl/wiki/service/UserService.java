package com.tangl.wiki.service;

import com.tangl.wiki.domain.User;
import com.tangl.wiki.po.UserLoginPO;
import com.tangl.wiki.po.UserQueryPO;
import com.tangl.wiki.po.UserResetPasswordPO;
import com.tangl.wiki.po.UserSavePO;
import com.tangl.wiki.vo.UserLoginVO;
import com.tangl.wiki.vo.UserQueryVO;
import com.tangl.wiki.vo.PageVO;

import java.util.List;

/**
 * @author tangl
 * @description
 * @create 2023-08-27 9:12
 */
public interface UserService {
    PageVO<UserQueryVO> list(UserQueryPO userQueryPO);

    void save(UserSavePO userSavePO);

    void delete(Long id);

    List<UserQueryVO> all();

    User selectByLoginName(String LoginName);

    void resetPassword(UserResetPasswordPO userResetPasswordPO);

    UserLoginVO login(UserLoginPO userLoginPO);
}
