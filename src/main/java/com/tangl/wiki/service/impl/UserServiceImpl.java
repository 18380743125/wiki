package com.tangl.wiki.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tangl.wiki.domain.User;
import com.tangl.wiki.domain.UserExample;
import com.tangl.wiki.exception.BusinessException;
import com.tangl.wiki.exception.BusinessExceptionCode;
import com.tangl.wiki.mapper.UserMapper;
import com.tangl.wiki.po.UserLoginPO;
import com.tangl.wiki.po.UserQueryPO;
import com.tangl.wiki.po.UserResetPasswordPO;
import com.tangl.wiki.po.UserSavePO;
import com.tangl.wiki.service.UserService;
import com.tangl.wiki.util.CopyUtil;
import com.tangl.wiki.util.SnowFlake;
import com.tangl.wiki.vo.PageVO;
import com.tangl.wiki.vo.UserLoginVO;
import com.tangl.wiki.vo.UserQueryVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author tangl
 * @description
 * @create 2023-08-27 9:12
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Resource
    private UserMapper userMapper;

    @Resource
    private SnowFlake snowFlake;

    @Override
    public PageVO<UserQueryVO> list(UserQueryPO userPO) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        if (!ObjectUtils.isEmpty(userPO.getLoginName())) {
            criteria.andLoginNameEqualTo(userPO.getLoginName());
        }

        PageHelper.startPage(userPO.getPage(), userPO.getSize());
        List<User> users = userMapper.selectByExample(userExample);

        PageInfo<User> pageInfo = new PageInfo<>(users);
        PageVO<UserQueryVO> pageVO = new PageVO<>();
        pageVO.setList(CopyUtil.copyList(users, UserQueryVO.class));
        pageVO.setTotal(pageInfo.getTotal());
        return pageVO;
    }

    @Override
    public List<UserQueryVO> all() {
        UserExample userExample = new UserExample();
        userExample.setOrderByClause("sort asc");
        List<User> users = userMapper.selectByExample(userExample);
        return CopyUtil.copyList(users, UserQueryVO.class);
    }

    @Override
    public void save(@RequestBody UserSavePO userSavePO) {
        User user = CopyUtil.copy(userSavePO, User.class);
        if (ObjectUtils.isEmpty(userSavePO.getId())) {
            User userDB = selectByLoginName(userSavePO.getLoginName());
            if (ObjectUtils.isEmpty(userDB)) {
                // 新增
                user.setId(snowFlake.nextId());
                userMapper.insert(user);
            } else {
                // 用户名已存在
                throw new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_EXIST);
            }
        } else {
            // 更新
            user.setLoginName(null);
            user.setPassword(null);
            userMapper.updateByPrimaryKeySelective(user);
        }
    }

    @Override
    public void delete(Long id) {
        userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public User selectByLoginName(String LoginName) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andLoginNameEqualTo(LoginName);
        List<User> userList = userMapper.selectByExample(userExample);
        if (CollectionUtils.isEmpty(userList)) {
            return null;
        } else {
            return userList.get(0);
        }
    }

    /**
     * 修改密码
     */
    @Override
    public void resetPassword(UserResetPasswordPO userResetPasswordPO) {
        User user = CopyUtil.copy(userResetPasswordPO, User.class);
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public UserLoginVO login(UserLoginPO userLoginPO) {
        User userDb = selectByLoginName(userLoginPO.getLoginName());
        if (ObjectUtils.isEmpty(userDb)) {
            // 用户名不存在
            LOG.info("用户名不存在, {}", userLoginPO.getLoginName());
            throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
        } else {
            if (Objects.equals(userDb.getPassword(), userLoginPO.getPassword())) {
                // 登录成功
                return CopyUtil.copy(userDb, UserLoginVO.class);
            } else {
                // 密码不正确
                LOG.info("密码不对, 输入密码：{}, 数据库密码：{}", userLoginPO.getPassword(), userDb.getPassword());
                throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
            }
        }
    }
}
