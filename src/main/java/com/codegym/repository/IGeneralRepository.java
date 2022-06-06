package com.codegym.repository;

import java.util.List;

public interface IGeneralRepository<T> {
    List<T> findAll();
    void save(T t);
    T findById(Long id);
    void remove(Long id);
}
