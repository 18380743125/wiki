package com.tangl.wiki.controller;

import com.alibaba.fastjson.JSONObject;
import com.tangl.wiki.po.UserLoginPO;
import com.tangl.wiki.po.UserQueryPO;
import com.tangl.wiki.po.UserResetPasswordPO;
import com.tangl.wiki.po.UserSavePO;
import com.tangl.wiki.response.CommonResponse;
import com.tangl.wiki.service.UserService;
import com.tangl.wiki.util.SnowFlake;
import com.tangl.wiki.vo.PageVO;
import com.tangl.wiki.vo.UserLoginVO;
import com.tangl.wiki.vo.UserQueryVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author tangl
 * @description
 * @create 2023-08-27 8:58
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

    @Resource
    private SnowFlake snowFlake;

    @Resource
    private RedisTemplate redisTemplate;

    @GetMapping("/list")
    public CommonResponse<PageVO<UserQueryVO>> list(@Valid UserQueryPO userQueryPO) {
        CommonResponse<PageVO<UserQueryVO>> commonResponse = new CommonResponse<>();
        PageVO<UserQueryVO> list = userService.list(userQueryPO);
        commonResponse.setContent(list);
        return commonResponse;
    }

    @GetMapping("/all")
    public CommonResponse<List<UserQueryVO>> all() {
        CommonResponse<List<UserQueryVO>> commonResponse = new CommonResponse<>();
        List<UserQueryVO> list = userService.all();
        commonResponse.setContent(list);
        return commonResponse;
    }

    @PostMapping("/save")
    public CommonResponse<?> save(@RequestBody @Valid UserSavePO userSavePO) {
        userSavePO.setPassword(DigestUtils.md5DigestAsHex(userSavePO.getPassword().getBytes()));
        CommonResponse<?> commonResponse = new CommonResponse<>();
        userService.save(userSavePO);
        return commonResponse;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResponse<?> delete(@PathVariable Long id) {
        CommonResponse<?> commonResponse = new CommonResponse<>();
        userService.delete(id);
        return commonResponse;
    }

    @PostMapping("/reset-password")
    public CommonResponse<?> resetPassword(@Valid @RequestBody UserResetPasswordPO userResetPasswordPO) {
        userResetPasswordPO.setPassword(DigestUtils.md5DigestAsHex(userResetPasswordPO.getPassword().getBytes()));
        CommonResponse<?> resp = new CommonResponse<>();
        userService.resetPassword(userResetPasswordPO);
        return resp;
    }

    @PostMapping("/login")
    public CommonResponse<?> login(@Valid @RequestBody UserLoginPO userLoginPO) {
        userLoginPO.setPassword(DigestUtils.md5DigestAsHex(userLoginPO.getPassword().getBytes()));
        CommonResponse<UserLoginVO> commonResponse = new CommonResponse<>();
        UserLoginVO userLoginVO = userService.login(userLoginPO);

        Long token = snowFlake.nextId();
        LOG.info("生成单点登录token：{}，并放入redis中", token);
        userLoginVO.setToken(token.toString());
        redisTemplate.opsForValue().set(token.toString(), JSONObject.toJSONString(userLoginVO), 3600 * 24, TimeUnit.SECONDS);
        commonResponse.setContent(userLoginVO);
        return commonResponse;
    }

    @GetMapping("/logout/{token}")
    public CommonResponse<?> logout(@PathVariable String token) {
        CommonResponse<?> resp = new CommonResponse<>();
        redisTemplate.delete(token);
        LOG.info("从redis中删除token: {}", token);
        return resp;
    }
}
