package svm.security.common;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "svm.security.dao", sqlSessionTemplateRef  = "securitySqlSessionTemplate")
public class SecurityDataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "mybatis.configuration")
    public org.apache.ibatis.session.Configuration securityConfiguration() {
        return new org.apache.ibatis.session.Configuration();
    }

    @Bean(name = "securityDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.security")
    @Primary
    public DataSource securityDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "securitySqlSessionFactory")
    @Primary
    public SqlSessionFactory securitySqlSessionFactory(@Qualifier("securityDataSource") DataSource dataSource, org.apache.ibatis.session.Configuration securityConfiguration) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setConfiguration(securityConfiguration);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/security/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "securityTransactionManager")
    @Primary
    public DataSourceTransactionManager securityTransactionManager(@Qualifier("securityDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "securitySqlSessionTemplate")
    @Primary
    public SqlSessionTemplate securitySqlSessionTemplate(@Qualifier("securitySqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
