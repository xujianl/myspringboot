package com.example.demo.listener;

import com.example.demo.model.AyUser;
import com.example.demo.service.AyUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

@WebListener
public class AyUserListener implements ServletContextListener {
    private static final  String ALL_USER="ALL_USER_LIST";
    Logger logger = LogManager.getLogger(this.getClass());
    @Resource
    private AyUserService ayUserService;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        //查询数据库中所有用户
        List<AyUser> ayUserList = ayUserService.findAll();
        //清楚redis中缓存
        redisTemplate.delete(ALL_USER);
        //用户数据放入缓存数据库
        redisTemplate.opsForList().leftPushAll(ALL_USER,ayUserList);
//        System.out.println(ALL_USER + "缓存数据库长度" + redisTemplate.opsForList().size(ALL_USER));
        List<AyUser> list = redisTemplate.opsForList().range(ALL_USER,0,-1);
//        System.out.println("缓存中目前存在的用户数："+list.size());
//        System.out.println("ServletContext 上下文初始化");
        logger.info("缓存中目前存在的用户数："+list.size());
        logger.info("ServletContext 上下文初始化");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

//        System.out.println("ServletContext 上下门销毁");
        logger.info("ServletContext 上下门销毁");
    }
}
