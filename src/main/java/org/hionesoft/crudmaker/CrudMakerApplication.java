package org.hionesoft.crudmaker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(
        exclude={DataSourceAutoConfiguration.class} // DB 사용 안함
)
//@EnableBatchProcessing // Spring Batch 활성화
@EnableScheduling       // 스케줄러 활성화
@EnableAsync            // 비동기화 기능 활성화 (스케줄러에 사용)
@PropertySource(value = { /*"classpath:db.properties", */ "classpath:admin.properties" })
// @ConfigurationProperties를 Bean 으로 등록하는 방법
// 1. @EnableConfigurationProperties 등록
// 2. 설정 프로퍼티 클래스에 @Configuration, @Bean 등으로 직접 등록하기
public class CrudMakerApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(CrudMakerApplication.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(CrudMakerApplication.class, args);
    }

}
