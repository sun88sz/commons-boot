package com.sun.web.dao.mybatis;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis DAO 配置类
 *
 * @author : Sun
 * @date : 2018/7/16 16:25
 */
@Configuration
@MapperScan("com.sun")
public class MybatisDaoConfiguration {

    /**
     * 数据库方言，默认mysql
     */
    @Value("${database.dialect}:mysql")
    private String dialect;

    /**
     * mybatis-plus分页插件<br>
     * 文档：http://mp.baomidou.com<br>
     *
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();

        paginationInterceptor.setDialectType(dialect);

        return paginationInterceptor;
    }

}
