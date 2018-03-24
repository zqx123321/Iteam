package cn.ouctechnology.dao;

import cn.ouctechnology.domain.Dept;

import java.util.List;

public interface IDeptDao {

    void save(Dept dept);

    List<Dept> list();

    Dept get(Long id);
}
