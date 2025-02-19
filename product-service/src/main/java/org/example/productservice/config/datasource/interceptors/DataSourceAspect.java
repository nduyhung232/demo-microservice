package org.example.productservice.config.datasource.interceptors;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.example.productservice.config.datasource.enums.DataSourceType;
import org.example.productservice.config.datasource.DatabaseContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataSourceAspect {
    @Pointcut("@annotation(org.example.productservice.config.datasource.UseMaster)")
    public void masterPointcut() {}

    @Before("masterPointcut()")
    public void useMaster() {
        DatabaseContextHolder.set(DataSourceType.MASTER);
    }

    @Before("execution(* org.example.productservice.repository..*.find*(..))")
    public void useSlaveForReadMethods() {
        DatabaseContextHolder.set(DataSourceType.SLAVE);
    }

    @After("masterPointcut() || execution(* org.example.productservice.repository..*.find*(..))")
    public void clearContext() {
        DatabaseContextHolder.clearDataSource();
    }
}
