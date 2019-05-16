package com.example.demo.service.impl;

import com.example.demo.model.AyUser;
import com.example.demo.repository.AyUserRepository;
import com.example.demo.service.AyUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
@Transactional
@Service
public class AyUserServiceImpl implements AyUserService {
    private static final  String ALL_USER="ALL_USER_LIST";
    Logger logger = LogManager.getLogger(this.getClass());
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private AyUserRepository ayUserRepository;
    @Override
    public AyUser findById(String id) {
        AyUser ayUser = new AyUser();
        //先从缓存里查数据
        List<AyUser> ayUserList = redisTemplate.opsForList().range(ALL_USER,0,-1);
        if (ayUserList != null && ayUserList.size()>0){
            for (AyUser user : ayUserList){
                System.out.println("缓存中数据为："+user.getId() + "|" + user.getName());
                if (user.getId().equals(id)){
                    System.out.println("从缓存中读到了数据"+user.getId());
                    return  user;
                }
            }
        }
        //缓存中没有从数据库查询
        Optional<AyUser> ayUserOptional = ayUserRepository.findById(id);
        if (ayUserOptional.isPresent()){
            ayUser = ayUserOptional.get();
            //数据插入缓存中
            redisTemplate.opsForList().leftPush(ALL_USER,ayUser);
            return ayUser;
        }else {
            return ayUser;
        }
    }

    @Override
    public List<AyUser> findAll() {
        return ayUserRepository.findAll();
    }

    @Transactional
    @Override
    public AyUser save(AyUser ayUser) {
        AyUser saveUser = ayUserRepository.save(ayUser);
        String error = null;
        error.split("/");
        return saveUser;
    }

    @Override
    public void delete(String id) {
        ayUserRepository.deleteById(id);
        logger.info(id + "用户被删除");
    }

    @Override
    public Page<AyUser> findAll(Pageable pageable) {
        return ayUserRepository.findAll(pageable);
    }

    @Override
    public List<AyUser> findByName(String name) {
        return ayUserRepository.findByName(name);
    }

    @Override
    public List<AyUser> findByNameLike(String name) {
        return ayUserRepository.findByNameLike(name);
    }

    @Override
    public List<AyUser> findByIdIn(Collection<String> ids) {
        return ayUserRepository.findByIdIn(ids);
    }
}
