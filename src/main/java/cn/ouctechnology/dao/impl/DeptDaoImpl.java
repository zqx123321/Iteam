package cn.ouctechnology.dao.impl;

import cn.ouctechnology.dao.IBaseDao;
import cn.ouctechnology.dao.IDeptDao;
import cn.ouctechnology.domain.Dept;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DeptDaoImpl implements IDeptDao {

    @Autowired
    private IBaseDao<Dept> dao;

    @Override
    public void save(Dept dept) {
        dao.save(dept);
    }

    @Override
    public List<Dept> list() {
        return dao.list(Dept.class);
    }

    @Override
    public Dept get(Long id) {
        return dao.get(Dept.class, id);
    }
}
