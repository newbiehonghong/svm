package svm.app.service.dashboard.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import svm.app.dao.dashboard.ConfigMapper;
import svm.app.service.dashboard.ConfigService;

@Service("DashboardConfigService")
public class ConfigServiceImpl implements ConfigService {
    @Autowired
    private ConfigMapper configMapper;

    @Override
    @Transactional
    public void updateLayout(long[] ids) {
        for(int i = 0; i < ids.length; i++) {
            configMapper.updateLayout(i + 1, ids[i]);
        }
    }
}
