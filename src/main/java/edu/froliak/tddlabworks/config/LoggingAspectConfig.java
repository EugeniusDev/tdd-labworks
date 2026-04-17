package edu.froliak.tddlabworks.config;

/*
  @author eugen
  @project tdd-labworks
  @class LoggingAspectConfig
  @version 1.0.0
  @since 4/17/2026 - 13.33
*/

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

@Aspect
@Component
@Slf4j
public class LoggingAspectConfig {
    private final Map<Throwable, Boolean> loggedExceptions = Collections.synchronizedMap(new WeakHashMap<>());

    @Pointcut("execution(* edu.froliak.tddlabworks..*.*(..))")
    public void methodsPointcut() {}

    @Before("methodsPointcut()")
    public void logBeforeMethod(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        log.info("Entering method: {}.{} with arguments: {}",
                className, methodName, Arrays.toString(args));

    }

    @AfterReturning(pointcut = "methodsPointcut()", returning = "result")
    public void logAfterMethod(JoinPoint joinPoint, Object result) {
    }
}