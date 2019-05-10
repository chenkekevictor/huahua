package com.huahua.base;

import huahua.common.utils.IdWorker;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @ClassName: BaseApplication
 * @Author: zhangyuyang
 * @Time : 2019/4/11 -上午11:28
 * @Version:1.0
 */
@SpringBootApplication
@MapperScan("com.huahua.base.mapper")
public class BaseApplication {


    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class,args);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1,1);
    }



}
