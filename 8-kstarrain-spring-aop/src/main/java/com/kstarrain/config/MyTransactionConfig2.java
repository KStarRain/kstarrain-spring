//package com.kstarrain.config;
//
//import com.kstarrain.annotation.MyTransactional;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.AfterThrowing;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.EnableAspectJAutoProxy;
//import org.springframework.core.annotation.AnnotationUtils;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.TransactionStatus;
//import org.springframework.transaction.support.DefaultTransactionDefinition;
//import org.springframework.util.ClassUtils;
//
//import java.lang.reflect.Method;
//
//
///**
// * @author: DongYu
// * @create: 2019-02-01 14:30
// * @description: 处理事务逻辑  第二种写法
// */
//@Component
//@EnableAspectJAutoProxy
//@Aspect
//@Slf4j
//public class MyTransactionConfig2 {
//
//    @Autowired
//    private PlatformTransactionManager transactionManager;
//
//    private TransactionStatus transactionStatus;
//
//
//
//    @Pointcut("@annotation(com.kstarrain.annotation.MyTransactional)")
//    public void myTransactionPoint() {
//    }
//
//
//    /** 环绕通知 在方法之前和之后处理事情 */
//    @Around(value = "myTransactionPoint()")
//    public void around(ProceedingJoinPoint pjp) throws Throwable {
//        //获取代理方法上的事务注解
//        MyTransactional myTransactional = getMethodMyAnnotation(pjp);
//
//        //如果方法没有注解，不做处理
//        if (myTransactional != null){
//            //开启事务
//            transactionStatus = begin(myTransactional);
//        }
//
//        //调用目标代理对象方法
//        pjp.proceed();
//
//        //如果方法没有注解，不做处理
//        if (myTransactional != null){
//            //提交事务
//            commit(transactionStatus);
//        }
//
//    }
//
//
//
//
//    /** 异常通知进行 回滚事务 */
//    @AfterThrowing(value = "myTransactionPoint()")
//    public void afterThrowing() {
//        rollback(transactionStatus);
//    }
//
//
//
//    // 获取代理方法上的事务注解
//    private MyTransactional getMethodMyAnnotation(ProceedingJoinPoint pjp) throws Exception {
//        //1. 获取代理对象的方法
//        String methodName = pjp.getSignature().getName();
//        //2. 获取目标对象(接口的实现类)
//        Class<?> classTarget = pjp.getTarget().getClass();
//        //3. 获取目标对象类型
//        Class<?>[] par = ((MethodSignature) pjp.getSignature()).getParameterTypes();
//        //4. 获取目标对象方法
//        Method objMethod = classTarget.getMethod(methodName, par);
//        //5. 获取该方法上的事务注解
//        return objMethod.getDeclaredAnnotation(MyTransactional.class);
//    }
//
//
//    // 开启事务
//    private TransactionStatus begin(MyTransactional myTransactional) {
//        log.info("==========开启事务==========");
//
//        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//        //设置事务传播方式
//        def.setPropagationBehavior(myTransactional.propagation().value());
//        //设置事务隔离等级
//        def.setIsolationLevel(myTransactional.isolation().value());
//        //设置事务只读
//        def.setReadOnly(myTransactional.readOnly());
//
//        def.setTimeout(myTransactional.timeout());
//
//        //获取事务对象
//        return transactionManager.getTransaction(def);
//    }
//
//    // 提交事务
//    private void commit(TransactionStatus transaction) {
//        log.info("==========提交事务==========");
//        transactionManager.commit(transaction);
//    }
//
//    // 回滚事务
//    private void rollback(TransactionStatus transaction) {
//        log.info("==========回滚事务==========");
//        transactionManager.rollback(transaction);
//    }
//
//
//
//}
