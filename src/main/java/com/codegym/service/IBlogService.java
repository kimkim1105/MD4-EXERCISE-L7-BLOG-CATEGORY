package com.codegym.service;

import com.codegym.model.Blog;
import com.codegym.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface IBlogService extends IGeneralService<Blog>{
    Iterable<Blog> findAllByCategory(Category category);
    Page<Blog> findAll(Pageable pageable);
    Page<Blog> findAllByNameContaining(String name, Pageable pageable);
    Page<Blog> findAllByOrderByTime(Pageable pageable);
    Page<Blog> findAllByOrderByName(Pageable pageable);
    Page<Blog> findAllByOrderById(Pageable pageable);
}
