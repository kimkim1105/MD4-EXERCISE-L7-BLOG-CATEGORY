package com.codegym.service;

import com.codegym.model.Blog;
import com.codegym.model.Category;
import com.codegym.repository.IBlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogService implements IBlogService{
    @Autowired
    private IBlogRepository blogRepository;


    @Override
    public Iterable<Blog> findAll() {
        return blogRepository.findAll();
    }

    @Override
    public void save(Blog blog) {
        blogRepository.save(blog);
    }

    @Override
    public Optional<Blog> findById(Long id) {
        return blogRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        blogRepository.deleteById(id);
    }

    @Override
    public Iterable<Blog> findAllByCategory(Category category) {
        return blogRepository.findAllByCategory(category);
    }


    @Override
    public Page<Blog> findAll(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> findAllByNameContaining(String name, Pageable pageable) {
        return blogRepository.findAllByNameContaining(name,pageable);
    }

    @Override
    public Page<Blog> findAllByOrderByTime(Pageable pageable) {
        return blogRepository.findAllByOrderByTime(pageable);
    }

    @Override
    public Page<Blog> findAllByOrderByName(Pageable pageable) {
        return blogRepository.findAllByOrderByName(pageable);
    }

    @Override
    public Page<Blog> findAllByOrderById(Pageable pageable) {
        return blogRepository.findAllByOrderById(pageable);
    }
}
