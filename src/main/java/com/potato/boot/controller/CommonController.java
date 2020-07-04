package com.potato.boot.controller;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class CommonController {
    @Autowired
    StringEncryptor jasyptStringEncryptor;
    @Resource
    private Environment environment;

    @GetMapping("/prop")
    public String getProp(String key) {
        return environment.getProperty(key);
    }

    @GetMapping("/pwd/encrypt")
    public String encryptPwd(String pwd) {
        return jasyptStringEncryptor.encrypt(pwd);
    }

    @GetMapping("/pwd/decrypt")
    public String decryptPwd(String pwd) {
        return jasyptStringEncryptor.decrypt(pwd);
    }
}
