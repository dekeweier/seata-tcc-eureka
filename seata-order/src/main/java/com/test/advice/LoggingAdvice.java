package com.test.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Create by 李传伟 on 2022/1/15 17:22
 */
@Aspect
@Component
@Slf4j
public class LoggingAdvice {

    //切入点 execution(* com.test.controller.*.*.*(..))

    /**
     * 第一个: 代表该源文件下
     * 第二个：com.test.orderController包下任意包
     * 第三个：任意类
     * 第四个：类中任意方法
     * （..）任意参数
     */
    @Pointcut(value = "execution(* com.test.orderController.*.*(..))")
    public void myPointCut(){

    }

    //这个around增强添加后，idea左侧有一个跳转的标志，可以点击看到被该增强作用到的方法
   @Around("myPointCut()")
    public Object applicationLogger(ProceedingJoinPoint pjp) throws Throwable {

        ObjectMapper objectMapper = new ObjectMapper();

        String methodName = pjp.getSignature().getName();

        String className = pjp.getTarget().getClass().toString();

        Object[] args = pjp.getArgs();

        log.info("method invoke "+ className + " : "+ methodName +"()"+"arguments: "+ objectMapper.writeValueAsString(args));

        Object object = pjp.proceed();

        log.info("method invoke "+ className + " : "+ methodName +"()"+"response: "+ objectMapper.writeValueAsString(object));

        return object;
    }
}
