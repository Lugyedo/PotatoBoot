package com.potato.boot.manager;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;

/**
 * @author dehuab
 */
@Aspect
@Order(1)
public class HelloAspect2 {
    @Pointcut("execution(* com.potato.boot.manager.BussinessPerson.service(..))")
    private void pointCut() {

    }

    @Before("pointCut()")
    private void before() {
        System.out.println("HelloAspect2 before ......");
    }

    @After("pointCut()")
    private void after() {
        System.out.println("HelloAspect2 after ......");
    }
}
