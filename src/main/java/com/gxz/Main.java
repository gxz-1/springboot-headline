package com.gxz;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("com.gxz.mapper")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class,args);
//        System.out.println("Hello world!");
    }

    //将mybatis plus插件加入ioc
    @Bean
    public MybatisPlusInterceptor interceptor(){
        //mybatis plus插件集合（将所需的插件：分页插件、乐观锁插件等加入即可）
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        //1.导入分页插件，传入数据库类型：mysql
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        //2.导入乐观锁插件
        mybatisPlusInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        //3.防止全表删除插件
        mybatisPlusInterceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());

        return mybatisPlusInterceptor;
    }
}