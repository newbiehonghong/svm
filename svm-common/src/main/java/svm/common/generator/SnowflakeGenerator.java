package svm.common.generator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service(value = "iGenerator")
public class SnowflakeGenerator implements IGenerator, InitializingBean {

    private static final String WORKER_ID = "svm.worker.id";

    protected SnowflakeIdWorker idWorker;

    @Override
    public Long generateLong(String... parameters)
    {
        return idWorker.nextId();
    }

    @Override
    public String generateString(String... parameters)
    {
        return String.valueOf(generateLong(parameters));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //集群环境需要在启动脚本里添加-Dsvm.worker.id=?//(0-30)
        String workerId = System.getProperty(WORKER_ID);
        int iWorkerId = StringUtils.isEmpty(workerId) ? 1 : Integer.parseInt(workerId);
        idWorker = new SnowflakeIdWorker(iWorkerId);
    }
}
