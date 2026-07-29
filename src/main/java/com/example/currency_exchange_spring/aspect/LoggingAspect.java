package com.example.currency_exchange_spring.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {
    @Around("execution(* com.example.currency_exchange_spring.controller.*.*(..))")
    public Object logController(ProceedingJoinPoint jp) throws Throwable {
        log.info("Запрос: {}", jp.getSignature().toShortString());
        long start = System.currentTimeMillis();
        Object result = jp.proceed();
        log.info("Ответ ({} мс): {}", System.currentTimeMillis() - start, jp.getSignature().toShortString());
        return result;
    }
}
