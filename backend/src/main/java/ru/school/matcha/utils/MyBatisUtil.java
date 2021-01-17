package ru.school.matcha.utils;

import java.io.IOException;
import java.io.Reader;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

@Slf4j
public class MyBatisUtil {
    private static final SqlSessionFactory factory;

    private MyBatisUtil() {
    }

    static {
        Reader reader;
        try {
            reader = Resources.getResourceAsReader("db/mybatis-config.xml");
            factory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            log.error("Failed to read properties for MyBatisUtil");
            throw new RuntimeException(e.getMessage());
        }
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        return factory;
    }
}