package cn.ouctechnology.dao;

import cn.ouctechnology.domain.Application;

import java.util.List;

public interface IApplicationDao {
    void save(Application application);

    Long count(String type, Long deptId);

    List list(String type, int page, int limit, Long deptId);

    Application get(Long id);

    void update(Application application);

    void delete(Application application);

    Long getMaxAccount();
}
