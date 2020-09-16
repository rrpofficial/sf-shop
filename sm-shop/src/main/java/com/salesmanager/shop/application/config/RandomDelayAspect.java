package com.salesmanager.shop.application.config;

import java.util.Arrays;
import java.util.Random;

import com.salesmanager.core.business.configuration.AddRandomDelay;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Aspect
public class RandomDelayAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(@com.salesmanager.core.business.configuration.AddRandomDelay * *.*(..))")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object addDelay(ProceedingJoinPoint joinPoint) throws Throwable {

        if (log.isDebugEnabled()) {
            log.debug("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
        }
        try {
            Object result = joinPoint.proceed();
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            AddRandomDelay addRandomDelay = signature.getMethod().getAnnotation(AddRandomDelay.class);
            if (addRandomDelay != null) {
                Random random = new Random();
                if (log.isDebugEnabled()) {
                    log.debug("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
                            joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
                }
                long sleepingFor = (random.nextInt(10) + 5) * 1000;
                log.error("Thread going to sleep for {} seconds for the method {}.{}", sleepingFor, joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
                Thread.sleep(sleepingFor);
                log.error("Thread exited from sleep {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            }
            if (log.isDebugEnabled()) {
                log.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
                        joinPoint.getSignature().getName(), result);
            }
            return result;
        } catch (IllegalArgumentException e) {
            log.info("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());

            throw e;
        }
    }
}