package svm.common.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertyNameAliases;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;
import svm.common.crypto.DESUtils;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 多数据源下需要增加对本package下的bean的扫描，如果项目扫描已经涵盖可忽略
 *
 * spring:
 *   datasource:
 *     username: root
 *     password: xxxxxxxxxxx
 *     url: jdbc:postgresql://10.4.57.22:5432/cloud?useUnicode=true&characterEncoding=UTF-8
 *     driver-class-name: org.postgresql.Driver
 *     others:
 *     - key: sample
 *       url: jdbc:postgresql://10.4.57.22:5432
 *       username: sample
 *       password: xxxxxxxxxxxxx
 *     - key: security
 *       url: jdbc:postgresql://10.4.57.22:5432
 *       username: security
 *       password: xxxxxxxxxxxxxx
 */
public class DynamitDataSourceRegister implements EnvironmentAware, ImportBeanDefinitionRegistrar {

    private final static ConfigurationPropertyNameAliases aliases = new ConfigurationPropertyNameAliases();

    static {
        aliases.addAliases("url", new String[]{"jdbc-url"});
        aliases.addAliases("username", new String[]{"user"});
    }

    private Environment evn;

    private Binder binder;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        Map defaultConfig = binder.bind("spring.datasource", Map.class).get(); //获取所有数据源配置
        String type = evn.getProperty("spring.datasource.type"); //默认数据源类型
        Class<? extends DataSource> clazz = getDataSourceType(type); //获取数据源类型
        DataSource defaultDatasource = bind(clazz, defaultConfig); //绑定默认数据源参数

        GenericBeanDefinition define = new GenericBeanDefinition(); //bean定义类
        define.setBeanClass(DynamicDataSource.class); //设置bean的类型，此处DynamicDataSource是继承AbstractRoutingDataSource的实现类
        MutablePropertyValues mpv = define.getPropertyValues(); //需要注入的参数，类似spring配置文件中的<property/>
        mpv.add("defaultTargetDataSource", defaultDatasource); //添加默认数据源，避免key不存在的情况没有数据源可用

        Map<String, DataSource> dataSourceMap = new HashMap<>();
        if (evn.containsProperty("spring.datasource.others")) {
            DataSource otherDatasource;
            String key;
            Map config, properties;

            List<Map> configs = binder.bind("spring.datasource.others", Bindable.listOf(Map.class)).get(); //获取其他数据源配置
            for (int i = 0; i < configs.size(); i++) { //遍历生成其他数据源
                config = configs.get(i);
                clazz = getDataSourceType((String) config.get("type"));
                if ((boolean) config.getOrDefault("extend", Boolean.TRUE)) { //获取extend字段，未定义或为true则为继承状态
                    properties = new HashMap(defaultConfig); //继承默认数据源配置
                    properties.putAll(config); //添加数据源参数
                } else {
                    properties = config; //不继承默认配置
                }
                otherDatasource = bind(clazz, properties);
                key = config.get("key").toString();
                dataSourceMap.put(key, otherDatasource); //获取数据源的key，以便通过该key可以定位到数据源
                DynamicDataSourceContextHolder.add(key);
            }
        }
        mpv.add("targetDataSources", dataSourceMap); //添加其他数据源，必须设置targetDataSources属性
        beanDefinitionRegistry.registerBeanDefinition("datasource", define); //将该bean注册为datasource，不使用springboot自动生成的datasource
    }

    private Class<? extends DataSource> getDataSourceType(String type) {
        Class<? extends DataSource> clazz;
        try {
            if (StringUtils.hasLength(type)) {
                clazz = (Class<? extends DataSource>) Class.forName(type);
            } else {
                clazz = DruidDataSource.class;
            }
            return clazz;
        } catch (Exception e) {
            throw new IllegalArgumentException("can not resolve class with type: " + type);
        }
    }

    private <T extends DataSource> T bind(Class<T> clazz, Map properties) {
        decodePassword(properties);
        ConfigurationPropertySource source = new MapConfigurationPropertySource(properties);
        Binder binder = new Binder(new ConfigurationPropertySource[]{source.withAliases(aliases)});
        return binder.bind(ConfigurationPropertyName.EMPTY, Bindable.of(clazz)).get(); //通过类型绑定参数并获得实例对象
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.evn = environment;
        binder = Binder.get(evn);
    }

    private void decodePassword(Map properties) {
        if (properties.containsKey("password")) {
            String password = String.valueOf(properties.get("password"));
            properties.put("password", DESUtils.decode(password));
        }
    }
}
