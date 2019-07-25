package com.van.app.service;

import com.van.app.mapper.UserMapper;
import com.van.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Primary
@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;
    @Resource
    RedisTemplate<String, User> redisTemplate;

    @Override
    public List<User> getList() {
        boolean hasKey = redisTemplate.hasKey("userList");
        Set<User> zlist;
        List<User> list;
        if (hasKey) {
            zlist = redisTemplate.opsForZSet().range("userList", 0, -1);
            list = new ArrayList<>(zlist);
        } else {
            list = userMapper.getList();
            //设置key的过期时间
            redisTemplate.expire("userList",60, TimeUnit.SECONDS);
        }

        return list;
    }

    @Override
    public User getOne(int id) {
        User user;
        boolean hasKey = redisTemplate.hasKey(String.valueOf(id));
        if (hasKey) {
            System.out.println("Cache : " + id);
            user = redisTemplate.opsForValue().get(String.valueOf(id));
        }else {
            System.err.println("no Cache");
            user = userMapper.getOne(id);
            redisTemplate.opsForValue().set(String.valueOf(id), user, 600, TimeUnit.SECONDS);
            redisTemplate.opsForZSet().add("userList", user, user.getId());
        }
        return user;
    }

    @Transactional()
    @Override
    public int update(User user) {
        int flag = userMapper.update(user);
        boolean hasKey = redisTemplate.hasKey(String.valueOf(user.getId()));
        if (flag == 1 && hasKey) {
            redisTemplate.opsForValue().set(String.valueOf(user.getId()), user, 600, TimeUnit.SECONDS);
//            redisTemplate.delete(String.valueOf(user.getId()));
        }
        return flag;
    }

    @Override
    public User getUserByName(String name, int age) {
        User user = userMapper.getByName(name);
        return user;
    }

    @Override
    public int addOne(User user) {
        int flag = userMapper.insertUser(user);
        return flag;
    }
}
