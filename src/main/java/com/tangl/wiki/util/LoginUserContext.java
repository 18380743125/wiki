package com.tangl.wiki.util;


import com.tangl.wiki.vo.UserLoginVO;

import java.io.Serializable;

public class LoginUserContext implements Serializable {

    private static ThreadLocal<UserLoginVO> user = new ThreadLocal<>();

    public static UserLoginVO getUser() {
        return user.get();
    }

    public static void setUser(UserLoginVO user) {
        LoginUserContext.user.set(user);
    }

}
