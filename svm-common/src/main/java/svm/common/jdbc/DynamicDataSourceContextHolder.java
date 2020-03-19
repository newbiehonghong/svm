package svm.common.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class DynamicDataSourceContextHolder {
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceContextHolder.class);

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    public static List<String> dataSourceKeys = new ArrayList<>();

    public static void setDataSource(String dataSourceKey) {
        logger.debug("指定数据源: {}", dataSourceKey);
        contextHolder.set(dataSourceKey);
    }

    public static String getDataSource() {
        return contextHolder.get();
    }

    public static void clear() {
        contextHolder.remove();
    }

    public static void add(String dataSourceKey) {
        dataSourceKeys.add(dataSourceKey);
    }

    public static boolean contains(String dataSourceKey) {
        return dataSourceKeys.contains(dataSourceKey);
    }
}
