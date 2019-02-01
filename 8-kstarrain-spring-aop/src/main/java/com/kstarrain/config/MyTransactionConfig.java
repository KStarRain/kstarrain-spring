package com.kstarrain.config;

import com.kstarrain.annotation.MyTransactional;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.lang.reflect.Method;


/**
 * @author: DongYu
 * @create: 2019-02-01 14:30
 * @description: 处理事务逻辑 第一种写法
 */
@Component
@EnableAspectJAutoProxy(proxyTargetClass = false) //proxyTargetClass属性为true，强制为cglib代理
@Aspect // 申明为切面
@Slf4j
public class MyTransactionConfig {

    @Autowired
    private PlatformTransactionManager transactionManager;

    private TransactionStatus transactionStatus;



    /** 环绕通知 在方法之前和之后处理事情 */
    @Around(value = "@annotation(myTransactional)")
    public void around(ProceedingJoinPoint pjp, MyTransactional myTransactional) throws Throwable {
        //如果方法没有注解，不做处理
        if (myTransactional == null){return;}
        //开启事务
        transactionStatus = begin(myTransactional);
        //调用目标代理对象方法
        pjp.proceed();
        //提交事务
        commit(transactionStatus);
    }




    /** 异常后通知 回滚事务 */
    @AfterThrowing(value = "@annotation(myTransactional)")
    public void afterThrowing(MyTransactional myTransactional) {
        rollback(transactionStatus);
    }







    // 开启事务
    private TransactionStatus begin(MyTransactional myTransactional) {
        log.info("开启事务");

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        //设置事务传播方式
        def.setPropagationBehavior(myTransactional.propagation().value());
        //设置事务隔离等级
        def.setIsolationLevel(myTransactional.isolation().value());
        //设置事务只读
        def.setReadOnly(myTransactional.readOnly());

        def.setTimeout(myTransactional.timeout());

        //获取事务对象
        return transactionManager.getTransaction(def);
    }

    // 提交事务
    private void commit(TransactionStatus transaction) {
        log.info("提交事务");
        transactionManager.commit(transaction);
    }

    // 回滚事务
    private void rollback(TransactionStatus transaction) {
        log.info("回滚事务...");
        transactionManager.rollback(transaction);
    }



}
