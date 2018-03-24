package cn.ouctechnology.dao.impl;

import cn.ouctechnology.dao.IBaseDao;
import cn.ouctechnology.dao.IUploadDao;
import cn.ouctechnology.domain.Upload;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UploadDaoImpl implements IUploadDao {
    @Autowired
    private SessionFactory sf;

    @Autowired
    private IBaseDao<Upload> dao;

    @Override
    public void save(Upload upload) {
        dao.save(upload);
    }

    @Override
    public List<Upload> getByToken(Long token) {
        Session session = sf.getCurrentSession();
        List<Upload> list = session.createQuery("select u from Upload u where u.token=" + token + "").list();
        return list;
    }
}
