package com.example.demo.mail.impl;

import com.example.demo.mail.SendJunkMailService;
import com.example.demo.model.AyUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
public class SendJunkMailServiceImpl implements SendJunkMailService {
    private  static  final Logger logger= LogManager.getLogger(SendJunkMailServiceImpl.class);
    @Autowired
    JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String from;
    @Override
    public boolean sendJunkMail(List<AyUser> ayUsers) {
        try {
        if (ayUsers ==null && ayUsers.size()<=0)
        return false;
        for (AyUser ayUser :ayUsers){
            MimeMessage mimeMailMessage = this.javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMailMessage);
                messageHelper.setFrom(from);
            messageHelper.setSubject("李丽红今日特卖");
            messageHelper.setTo("1006726200@qq.com");
            messageHelper.setText(ayUser.getName() + ",你知道吗？广州丽红今日特卖，一斤只要9元！");
            this.javaMailSender.send(mimeMailMessage);
        }
        } catch (MessagingException e) {
            logger.info("error and ayUser=%s",ayUsers,e);
            e.printStackTrace();
            return  false;
        }
        return true;
    }
}
