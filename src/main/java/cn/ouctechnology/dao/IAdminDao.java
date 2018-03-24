package cn.ouctechnology.dao;

import cn.ouctechnology.domain.Admin;

import java.util.List;

public interface IAdminDao {
    void save(Admin admin);

    Admin getAdminByAccount(String account);

    Admin get(Long id);

    void update(Admin admin);

    List<Admin> list();
}
