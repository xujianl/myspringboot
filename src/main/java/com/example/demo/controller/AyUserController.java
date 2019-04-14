package com.example.demo.controller;

import com.example.demo.model.AyUser;
import com.example.demo.service.AyUserService;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.util.List;

public class AyUserController {
    @Resource
    AyUserService ayUserService;
    public  String test(Model model){
        List<AyUser> ayUserList = ayUserService.findAll();
        model.addAttribute("users",ayUserList);
        return  "ayUser";
    }
}
