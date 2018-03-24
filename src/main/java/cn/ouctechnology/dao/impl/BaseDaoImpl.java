package cn.ouctechnology.dao.impl;

import cn.ouctechnology.dao.IBaseDao;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class BaseDaoImpl<T> implements IBaseDao<T> {
    @Autowired
    private SessionFactory sf;

    public void save(T obj) {
        Session session = sf.getCurrentSession();
        session.save(obj);
    }

    @Override
    public void delete(T obj) {
        Session session = sf.getCurrentSession();
        session.delete(obj);
    }

    @Override
    public void update(T obj) {
        Session session;
        try {
            session = sf.getCurrentSession();
        } catch (HibernateException e) {
            session = sf.openSession();
        }
        session.update(obj);
    }

    @Override
    public List<T> list(Class<T> classType) {
        Session session = sf.getCurrentSession();
        List<T> list = session.createCriteria(classType).list();
        return list;
    }

    @Override
    public T get(Class<T> classType, Long id) {
        Session session = sf.getCurrentSession();
        T obj = (T) session.get(classType, id);
        return obj;
    }
}
