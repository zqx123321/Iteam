package cn.ouctechnology.dao.impl;

import cn.ouctechnology.dao.IApplicationDao;
import cn.ouctechnology.dao.IBaseDao;
import cn.ouctechnology.domain.Application;
import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ApplicationDaoImpl implements IApplicationDao {
    @Autowired
    private IBaseDao<Application> dao;

    @Autowired
    private SessionFactory sf;


    @Override
    public void save(Application application) {
        dao.save(application);
    }

    @Override
    public Long count(String type, Long deptId) {
        Session session = sf.getCurrentSession();
        Criteria criteria = session.createCriteria(Application.class);
        if (!"全部".equals(type)) {
            criteria.add(Restrictions.eq("type", type));
        }
        if (deptId != 0) {
            criteria.add(Restrictions.eq("deptId", deptId));
        }
        return (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
    }

    @Override
    public List<Application> list(String type, int page, int limit, Long deptId) {
        Session session;
        try {
            session = sf.getCurrentSession();
        } catch (HibernateException e) {
            session = sf.openSession();
        }
        List<Application> list = null;
        Criteria criteria = session.createCriteria(Application.class);

        if (!"全部".equals(type)) {
            criteria.add(Restrictions.eq("type", type));
        }
        if (deptId != 0) {
            criteria.add(Restrictions.eq("deptId", deptId));
        }
        criteria.addOrder(Order.desc("submit"));
        criteria.setFirstResult((page - 1) * limit).setMaxResults(limit);
        list = criteria.list();
        session.clear();
        return list;
    }

    @Override
    public Application get(Long id) {
        Session session;
        try {
            session = sf.getCurrentSession();
        } catch (HibernateException e) {
            session = sf.openSession();
        }
        Object obj = session.get(Application.class, id);
        if (obj == null) return null;
        return (Application) obj;
    }

    @Override
    public void update(Application application) {
        dao.update(application);
    }

    @Override
    public void delete(Application application) {
        dao.delete(application);
    }

    @Override
    public Long getMaxAccount() {
        Session currentSession = sf.getCurrentSession();
        Object o = currentSession.createQuery("select max (a.agreeAccount) from Application a ").uniqueResult();
        if (o == null) return Long.valueOf(1000001);
        else return (Long) o;
    }
}
