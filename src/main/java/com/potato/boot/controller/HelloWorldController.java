package com.potato.boot.controller;

import com.potato.boot.manager.BussinessPerson;
import com.potato.boot.mq.RabbitMQSender;
import com.potato.boot.pojo.Tavern;
import com.potato.boot.service.TavernService;
import com.potato.boot.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.naming.InvalidNameException;
import javax.naming.Name;
import javax.naming.directory.SearchControls;
import javax.validation.Valid;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;

/**
 * @author dehuab
 */
@RestController
public class HelloWorldController {
    @Autowired
    private BussinessPerson bussinessPerson;
    @Autowired
    private TavernService tavernService;
    @Autowired
    private RabbitMQSender rabbitMQSender;
    @Resource
    private LdapTemplate ldapTemplate;

    @RequestMapping("/hello")
    public String hello() {
        bussinessPerson.service();
        System.out.println("HelloWorld");
        System.out.println("核".getBytes().length);
        System.out.println("C".getBytes().length);

        return "hello world";
    }

    @PostMapping("/tavern")
    public Tavern getTavern(@Valid @RequestBody Tavern tavern) {
        //Tavern tavern = tavernService.getTavern();
        return tavern;
    }

    @RequestMapping("/getVerificationCode")
    public String getVerificationCode() {
        String result = "";

        String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
        String phoneNumber = "13810195356".replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");

        result = String.format("%s %s", verifyCode, phoneNumber);
        return result;
    }

    @RequestMapping("/genRandomPwd")
    public String genRandomPwd() {
        String result = PasswordUtil.genRandomPwd(6);
        return result;
    }

    @GetMapping("/rabbitSend")
    public String rabbitSend(String topic, String message) {
        return rabbitMQSender.send(topic, message);
    }

    private DistinguishedName buildDn() {
        DistinguishedName dn = new DistinguishedName();
        dn.append("ou", "People");
        return dn;
    }

    @GetMapping("/getPeopleDn")
    public Name getPeopleDn(String userId) {
        AndFilter andFilter = new AndFilter();
        andFilter.and(new EqualsFilter("objectclass", "person"));
        andFilter.and(new EqualsFilter("objectclass", "xUserObjectClass"));
        andFilter.and(new EqualsFilter("uid", userId));
        List<Name> result = ldapTemplate.search(buildDn(), andFilter.encode(),
                SearchControls.SUBTREE_SCOPE, new AbstractContextMapper() {
                    @Override
                    protected Name doMapFromContext(DirContextOperations adapter) {
                        return adapter.getDn();
                    }
                });
        if (null == result || result.size() != 1) {
            //throw new UserNotFoundException();
        } else {
            return result.get(0);
        }
        return new Name() {
            @Override
            public int compareTo(Object obj) {
                return 0;
            }

            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public Enumeration<String> getAll() {
                return null;
            }

            @Override
            public String get(int posn) {
                return null;
            }

            @Override
            public Name getPrefix(int posn) {
                return null;
            }

            @Override
            public Name getSuffix(int posn) {
                return null;
            }

            @Override
            public boolean startsWith(Name n) {
                return false;
            }

            @Override
            public boolean endsWith(Name n) {
                return false;
            }

            @Override
            public Name addAll(Name suffix) throws InvalidNameException {
                return null;
            }

            @Override
            public Name addAll(int posn, Name n) throws InvalidNameException {
                return null;
            }

            @Override
            public Name add(String comp) throws InvalidNameException {
                return null;
            }

            @Override
            public Name add(int posn, String comp) throws InvalidNameException {
                return null;
            }

            @Override
            public Object remove(int posn) throws InvalidNameException {
                return null;
            }

            @Override
            public Object clone() {
                return null;
            }
        };
    }
}
