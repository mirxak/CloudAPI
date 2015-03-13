package com.main.cloudapi.service.base;

/**
 * Created by mirxak on 07.02.15.
 */
public interface BaseComplectationService<T> {
    public T getById(Long id);
    public T add(String json);
    public T edit(Long id, String json);
    public T delete(Long id);
}
