package com.example.demo.config;

import brave.ScopedSpan;
import brave.Span;
import brave.Tracer;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class SleuthAspect {


    @Pointcut("execution (public * com.example.demo.service.*.*(..))")
    public void allServices() {}


    @Pointcut("execution (public * com.example.demo.integration.*.*(..))")
    public void allIntegrations() {}


    @Autowired
    Tracer tracer;

    @Around(" allIntegrations()||allServices()")
    public Object traceThing(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("yoyoyoyo");
        ScopedSpan span = tracer.startScopedSpan(pjp.getSignature().getName());
        try {
            span.annotate(pjp.getKind());
            span.annotate(pjp.getSignature().toShortString());
            return pjp.proceed();
        } catch (RuntimeException | Error e) {
            span.error(e);
            throw e;
        } finally {
            span.finish();
        }
    }
}
