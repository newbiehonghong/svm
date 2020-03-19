package svm.app.dao.dashboard;

import org.apache.ibatis.annotations.Param;
import svm.app.entity.dashboard.Config;

import java.util.List;

public interface ConfigMapper {
    int save(Config record);
    int update(Config record);
    int delete(long id);
	List<Config> queryAll();
    int updateLayout(@Param("index") int index, @Param("id") long id);
}