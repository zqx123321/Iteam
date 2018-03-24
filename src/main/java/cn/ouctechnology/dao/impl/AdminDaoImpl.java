package cn.ouctechnology.dao.impl;

import cn.ouctechnology.dao.IAdminDao;
import cn.ouctechnology.dao.IBaseDao;
import cn.ouctechnology.domain.Admin;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdminDaoImpl implements IAdminDao {

    @Autowired
    private SessionFactory sf;
    @Autowired
    private IBaseDao<Admin> dao;

    @Override
    public void save(Admin admin) {
        dao.save(admin);
    }

    @Override
    public Admin getAdminByAccount(String account) {
        Session session = sf.getCurrentSession();
        Object o = session.createQuery("select e from Admin e where e.account='" + account + "'").uniqueResult();
        if (o == null) {
            return null;
        }
        return (Admin) o;
    }

    @Override
    public Admin get(Long id) {
        return dao.get(Admin.class, id);
    }

    @Override
    public void update(Admin admin) {
        dao.update(admin);
    }

    @Override
    public List<Admin> list() {
        return dao.list(Admin.class);
    }
}
