package com.dashboard.saas.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class LoggingAspect {

    @Around("execution(* com.dashboard.saas.service.authentication.*.*(..))")
    public Object logExecutionTime(
            ProceedingJoinPoint joinPoint
    ) throws Throwable {

        String methodName = joinPoint.getSignature().getName();

        String className = joinPoint.getTarget()
                .getClass()
                .getSimpleName();

        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long end = System.currentTimeMillis();

        long executionTime = end - start;

        System.out.println("=================================");
        System.out.println("Method Name : " + methodName);
        System.out.println("Class Name  : " + className);
        System.out.println("Execution Time : " + executionTime + " ms");
        System.out.println("=================================");
        return  result;

    }
}
