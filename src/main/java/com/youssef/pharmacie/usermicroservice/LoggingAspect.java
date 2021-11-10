package com.youssef.pharmacie.usermicroservice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Executable;

@Aspect
@Component
public class LoggingAspect {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Around("execution(* com.youssef.pharmacie..*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        long begin = System.currentTimeMillis();
        var result = joinPoint.proceed();
        long duration = System.currentTimeMillis()-begin;
        logger.info("took "+duration+" milliseconds to execute "+joinPoint.getSignature());
        return result;
    }

}
