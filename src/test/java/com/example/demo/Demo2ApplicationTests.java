package com.example.demo;

import com.example.demo.config.AlphaConfig;
import com.example.demo.controller.AlphaController;
import com.example.demo.dao.AlphaDao;
import com.example.demo.service.AlphaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
@ContextConfiguration(classes = Demo2Application.class)
class Demo2ApplicationTests implements ApplicationContextAware {// Spring容器扫描时 检测到Bean 调用set方法 把自己传进来

    private ApplicationContext applicationContext; // 记录bean

    @Test
    void contextLoads() {
    }
    //这里的applicationContext 就是Spring容器 需要实现接口
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Test
    void testContext(){
        //System.out.println(this.applicationContext);


        // 获取容器中管理的bean
        AlphaDao alphaDao1  = applicationContext.getBean(AlphaDao.class); //按类型获取bean 如果接口由多个实现方法？ 在使用的bean上增加注解@Primary
        System.out.println(alphaDao1.select());

        AlphaDao alphaDao2 = applicationContext.getBean("alphaDaoimpl", AlphaDao.class);
        System.out.println(alphaDao2.select());
    }

    @Test
    void testBeanManger(){
        AlphaService alphaService = applicationContext.getBean(AlphaService.class);
        System.out.println(alphaService);
    }

    @Test
    void testConfig(){
        SimpleDateFormat simpleDateFormat = applicationContext.getBean(SimpleDateFormat.class); //这里需要传入Bean 而不是类名
        System.out.println(simpleDateFormat.format(new Date())); //获得的是SimpleDateFormat（一个对象) simpledata需要特殊的格式

    }

    //依赖注入DI 当前bean使用alphadao
    @Autowired //希望将AlphaDao（本身是一个接口)注入这个属性
    @Qualifier("alphaDaoimpl") // 指定注入bean 需要bean的名字（就是自己取得)
    private AlphaDao alphaDao;

    @Test
    void testDI(){
        System.out.println(alphaDao); // com.example.demo.dao.AlphaMybatisimpl@19b75b2b
    }

    @Autowired
    private AlphaController alphaController;

    @Test
    void testDI1(){
        System.out.println(alphaController.getData());
    }

    @Autowired
    private AlphaService alphaService;

    @Test
    void testDI2(){
        System.out.println(alphaService.find());
    }

}
