package cn.ouctechnology.dao;

import java.util.List;

public interface IBaseDao<T> {
    void save(T obj);

    void delete(T obj);

    void update(T obj);

    List<T> list(Class<T> classType);

    T get(Class<T> classType, Long id);
}
