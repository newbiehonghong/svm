package svm.app.controller.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import svm.app.dao.dashboard.ConfigMapper;
import svm.app.entity.dashboard.Config;
import svm.app.service.dashboard.ConfigService;
import svm.common.generator.IGenerator;

import java.util.List;

@RestController
@RequestMapping(value = "/dashboard")
public class DashboardController {
    @Autowired
    private ConfigMapper configMapper;

    @Autowired
    private ConfigService configService;

    @Autowired
    private IGenerator iGenerator;

    @RequestMapping("/config/query")
    @ResponseBody
    public List<Config> queryAll() {
        return configMapper.queryAll();
    }

    @PostMapping("/config/save")
    @ResponseBody
    public long save(@RequestBody Config config) {
        long id = iGenerator.generateLong();
        config.setId(id);
        config.setIndex(1000);
        configMapper.save(config);
        return id;
    }

    @PostMapping("/config/update")
    public void update(@RequestBody Config config) {
        configMapper.update(config);
    }

    @PostMapping("/config/delete/{id}")
    public void deleteRule(@PathVariable long id) {
        configMapper.delete(id);
    }

    @PostMapping("/layout/save")
    public void saveLayout(@RequestBody long[] ids) {
        configService.updateLayout(ids);
    }
}
