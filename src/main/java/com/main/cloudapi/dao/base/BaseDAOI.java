package com.main.cloudapi.dao.base;

import com.main.cloudapi.entity.User;
import com.main.cloudapi.entity.base.BaseEntity;
import org.hibernate.Criteria;

import java.util.List;
import java.util.Set;

/**
 * Created by mirxak on 23.01.15.
 */
public interface BaseDAOI<T extends BaseEntity> {

    public void evict(Object entity);
    public void clear();

    //<editor-fold desc="getAll">
    public List<T> getAll(Class<T> cl);
    public List<T> getAll(Class<T> cl, Criteria criteria);
    public List<T> getAll(Class<T> cl, int offset, int limit);
    public List<T> getAll(int offset, int limit);
    public List<T> getAll(int offset, int limit, Criteria criteria);
    public List<T> getAll(Class<T> cl, int offset, int limit, Criteria criteria);
    //</editor-fold>

    public List<T> batchInsert(List<T> entities);
    public User getCurrentUser();
    public List<T> addList(List<T> entities);
    public T update(T entity);
    public void delete(T entity);

    //<editor-fold desc="getById">
    public T getById(Long id);
    public T getById(Long id, Class<T> cl);
    public T getById(Long id, boolean throwIfNotExist, Criteria criteria);
    public T getById(Long id, Class<T> cl, boolean throwIfNotExist, Criteria criteria);
    public T getById(Long id, boolean throwIfNotExist);
    public T getById(Long id, Class<T> cl, boolean throwIfNotExist);
    //</editor-fold>

    //<editor-fold desc="getByIds">
    public List<T> getByIds(Set<Long> ids, Class<T> cl);
    public List<T> getByIds(Set<Long> ids, int offset, int limit);
    public List<T> getByIds(Set<Long> ids, Class<T> cl, int offset, int limit);
    //</editor-fold>

    public T loadById(Long id, Class<T> cl);
    public void add(T entity);
    public Criteria createCriteria();

}
