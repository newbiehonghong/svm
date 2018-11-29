package svm.sample.common;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "svm.sample.dao", sqlSessionTemplateRef  = "sampleSqlSessionTemplate")
public class SampleDataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "mybatis.configuration")
    public org.apache.ibatis.session.Configuration sampleConfiguration() {
        return new org.apache.ibatis.session.Configuration();
    }

    @Bean(name = "sampleDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.sample")
    public DataSource sampleDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "sampleSqlSessionFactory")
    public SqlSessionFactory sampleSqlSessionFactory(@Qualifier("sampleDataSource") DataSource dataSource, org.apache.ibatis.session.Configuration sampleConfiguration) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //由于使用了多数据源，只加载配置文件的一部分，导致mybatis的配置没有被自动加载，此处用程序加载
        bean.setConfiguration(sampleConfiguration);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/sample/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "sampleTransactionManager")
    public DataSourceTransactionManager sampleTransactionManager(@Qualifier("sampleDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "sampleSqlSessionTemplate")
    public SqlSessionTemplate sampleSqlSessionTemplate(@Qualifier("sampleSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
