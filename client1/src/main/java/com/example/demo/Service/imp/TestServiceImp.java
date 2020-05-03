package com.example.demo.Service.imp;

import com.example.demo.Service.TestService;
import com.example.demo.dao.mayubin.MayubinDao;
import com.example.demo.dao.orcl.CourseDao;
import com.example.demo.domain.Course;
import com.example.demo.domain.Mayubin;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
//import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class TestServiceImp implements TestService {
    @Autowired
    MayubinDao mayubinDao;

    @Autowired
    CourseDao courseDao;

    @Override
    public String test() {
        return null;
    }


    @Override
    @Transactional(rollbackFor={Exception.class})
    public String testMayubin() {
        String name = UUID.randomUUID().toString();
//        List<Mayubin> mayubins =  mayubinDao.queryMayubin(null);
        Mayubin mayubin = new Mayubin();
        mayubin.setId(1);
        mayubin.setName(name);

        mayubinDao.updateMayubin(mayubin);
        System.out.println("更新mayubin表（ID=" + 1 + "）：name=" + name);

        mayubin.setId(2);
        mayubin.setName(name);

        mayubinDao.updateMayubin(mayubin);
        System.out.println("更新mayubin表（ID=" + 2 + "）：name=" + name);

        int a = 1/0;
        return "成功";
    }


    @Override
    @Transactional(value = "data2TransactionManager",rollbackFor={Exception.class})
    public String testSource() {
        String goal = UUID.randomUUID().toString();

        Course course = new Course();
        course.setId(1);
        course.setGoal(goal);
        System.out.println("更新Course表（ID=" + 1 + "）：goal=" + goal);
        courseDao.updateCourse(course);

        course.setId(2);
        course.setGoal(goal);
        System.out.println("更新Course表（ID=" + 2 + "）：goal=" + goal);
        courseDao.updateCourse(course);

        int a = 1/0;
        return "成功";
    }

//    @GlobalTransactional(rollbackFor={Exception.class})
    @Override
    @Transactional(rollbackFor={Exception.class})
    public String testTransactional(){
        String name = UUID.randomUUID().toString();
        
        Mayubin mayubin = new Mayubin();
        mayubin.setId(1);
        mayubin.setName(name);
        System.out.println("更新mayubin表（ID=" + 1 + "）：name=" + name);
        mayubinDao.updateMayubin(mayubin);

        Course course = new Course();
        course.setId(1);
        course.setGoal(name);
        System.out.println("更新Course表（ID=" + 1 + "）：goal=" + name);
        courseDao.updateCourse(course);

        int a = 1/0;
        return "aa";
    }

    public String tttt(Throwable e){
        System.out.println(e.getMessage());
        return "报错失败";
    }
}
