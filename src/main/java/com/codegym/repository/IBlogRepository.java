package com.codegym.repository;

import com.codegym.model.Blog;
import com.codegym.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBlogRepository extends PagingAndSortingRepository<Blog,Long> {
    Iterable<Blog> findAllByCategory(Category category);
    Page<Blog> findAllByNameContaining(String name, Pageable pageable);
    Page<Blog> findAllByOrderByTime(Pageable pageable);
    Page<Blog> findAllByOrderByName(Pageable pageable);
    Page<Blog> findAllByOrderById(Pageable pageable);
}
