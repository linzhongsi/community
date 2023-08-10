package com.example.demo.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class AlphaMybatisimpl implements AlphaDao{

    @Override
    public String select() {
        return "Mybatis";
    }
}
