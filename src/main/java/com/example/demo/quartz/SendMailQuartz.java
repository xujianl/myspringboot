package com.example.demo.quartz;

import com.example.demo.mail.SendJunkMailService;
import com.example.demo.model.AyUser;
import com.example.demo.service.AyUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
@Configurable
@EnableScheduling
public class SendMailQuartz {
    @Resource
    AyUserService ayUserService;
    @Resource
    SendJunkMailService sendJunkMailService;
    private  static  final Logger logger= LogManager.getLogger(SendMailQuartz.class);
    @Scheduled(cron = "*/5 * * * * *")
    public  void reportCurrentByCon(){
        List<AyUser> ayUserList = ayUserService.findAll();
        if (ayUserList == null && ayUserList.size()<0) return;
        sendJunkMailService.sendJunkMail(ayUserList);
        logger.info("job运行了");
    }
}
