package com.gec.controller;

import com.gec.repository.UserRepository;
import com.gec.result.R;
import com.gec.model.po.User;
import com.gec.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@CrossOrigin
public class UserController {


    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public R login(@RequestBody User user){
        String name = user.getUsername();
        String password = user.getPassword();
        User userDB = userRepository.findByUsernameAndPassword(name, MD5Util.encryptMD5(password))
                .orElse(null);
        if(userDB==null){
            return R.error(20001,"没有该用户");
        }
        return R.ok("登录成功").setData(userDB);

    }

    @PostMapping("/register")
    public R regiseter(@RequestBody User user){
        String name = user.getUsername();
        List<User> checkName = userRepository.findByUsername(name);
        if(checkName!=null && checkName.size()>0){
            return R.error(20001,"用户名已被注册");
        }
        user.setPassword(MD5Util.encryptMD5(user.getPassword()));
        userRepository.save(user);
        return R.ok("注册成功").setData(user);

    }

    @GetMapping("/userlist/{currentPage}/{size}")
    public Page<User> findAll(@PathVariable("currentPage") Integer page, @PathVariable("size") Integer size){
        PageRequest request = PageRequest.of(page,size);
        return userRepository.findAll(request);
    }
}
