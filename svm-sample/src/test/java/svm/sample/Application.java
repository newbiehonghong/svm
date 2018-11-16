package svm.sample;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
@SpringBootApplication
@MapperScan("svm.*.dao")
@ComponentScan("svm")
public class Application {
}
