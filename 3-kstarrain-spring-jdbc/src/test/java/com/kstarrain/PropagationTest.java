package com.kstarrain;

import com.kstarrain.dao.StudentDao;
import com.kstarrain.pojo.Student;
import com.kstarrain.service.IStudentService;
import com.kstarrain.utils.TestDataUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

/**
 * @author: DongYu
 * @create: 2019-01-23 11:32
 * @description: 事务传播行为测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:config/application.xml"})
@Slf4j
public class PropagationTest extends AbstractJUnit4SpringContextTests {

    private static String ID = "d7fbbfd87a08408ba994dea6be435ada";

    @Autowired
    StudentDao studentDao;

    @Autowired
    PlatformTransactionManager transactionManager;

    /** 扫除一个盲区，readonly并不能影响数据库隔离级别，
     *  只是配置之后，不允许在事务中对数据库进行新增/修改操作，仅此而已。 */
    @Test
    public void propagation_readonly(){
        System.out.println("===============================================================================");

        //定义事务
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        //设置事务隔离等级
        def.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
        //设置事务传播方式
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        /** 设置是否只读 等同于jdbc中conn.setReadOnly(true);*/
        def.setReadOnly(true);
        //获取事务对象
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            List<Student> students1 = studentDao.findAllStudent();
            System.out.println("查询结果:"+ students1.size()+ "条");

            int num = studentDao.insertStudent(TestDataUtils.createStudent1());
            System.out.println("插入结果:"+ students1.size()+ "条");

            List<Student> students2 = studentDao.findAllStudent();
            System.out.println("查询结果:"+ students2.size()+ "条");

            transactionManager.commit(status);
        } catch (Exception e) {
            //如果事务提交失败 ，回滚事务
            transactionManager.rollback(status);
            log.error(e.getMessage(),e);
        }
        System.out.println("===============================================================================");
    }


    /** 事务传播方式测试01 -- 编程式 */
    @Test
    public void propagation_programming(){
        System.out.println("===============================================================================");

        try {
            IStudentService studentService = super.applicationContext.getBean("studentServiceImpl", IStudentService.class);
            studentService.propagation_programming01();
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

        System.out.println("===============================================================================");
    }

}
