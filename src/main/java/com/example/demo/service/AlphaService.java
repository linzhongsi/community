package com.example.demo.service;

import com.example.demo.dao.AlphaDao;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
//@Scope("prototype") // 允许多个实例
@Scope("singleton") // 就是默认的 单例
public class AlphaService {
    @Autowired // Service调用dao层（上调用下层) Service依赖dao
    private AlphaDao alphaDao;

    public AlphaService(){
        System.out.println("实例化Service");
    }

    @PostConstruct //在构造器之后调用
    public void init(){
        System.out.println("初始化Service");
    }

    //销毁 也是通过注解 注解可以看作标签，不同的标签说明不同的时机与功能
    @PreDestroy //对象销毁之前
    public void destroy(){
        System.out.println("销毁");
    }

    public String find(){
        return alphaDao.select();
    }

}
