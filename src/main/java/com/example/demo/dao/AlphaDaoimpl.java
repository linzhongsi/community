package com.example.demo.dao;

import org.springframework.stereotype.Repository;

@Repository("alphaDaoimpl") // 为了能让bean被管理 需要加上注解
public class AlphaDaoimpl implements AlphaDao {
    public String select(){
        return "aaaaa";
    }

}
