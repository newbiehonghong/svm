package svm.common.jdbc;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(-1) // 保证该AOP在@Transactional之前执行
@Component
public class DynamicDataSourceAspect {
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    @Before("@annotation(ds)")
    public void changeDataSource(JoinPoint point, TargetDataSource ds) {
        if (!DynamicDataSourceContextHolder.contains(ds.name())) {
            logger.error("数据源[{}]不存在，使用默认数据源--[{}]", ds.name(), point.getSignature());
        } else {
            logger.debug("使用数据源[{}]--[{}]", ds.name(), point.getSignature());
            DynamicDataSourceContextHolder.setDataSource(ds.name());
        }
    }

    @After("@annotation(ds)")
    public void restoreDataSource(JoinPoint point, TargetDataSource ds) {
        logger.debug("恢复数据源[{}]--[{}]", ds.name(), point.getSignature());
        DynamicDataSourceContextHolder.clear();
    }
}
