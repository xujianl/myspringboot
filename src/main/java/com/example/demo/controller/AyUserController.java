package com.example.demo.controller;

import com.example.demo.model.AyUser;
import com.example.demo.service.AyUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;
@Controller
@RequestMapping("/ayUser")
public class AyUserController {
    @Resource
    private AyUserService ayUserService;
    @RequestMapping("/test")
    public  String test( Model model){
        List<AyUser> ayUserList = ayUserService.findAll();
        model.addAttribute("users",ayUserList);
        System.out.println("准备跳转");
        return  "ayUser";
    }
}
