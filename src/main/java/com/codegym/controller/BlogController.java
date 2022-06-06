package com.codegym.controller;

import com.codegym.model.Blog;
import com.codegym.model.Category;
import com.codegym.service.IBlogService;
import com.codegym.service.category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class BlogController {
    @Autowired
    private IBlogService blogService;
    @Autowired
    private ICategoryService categoryService;

    @ModelAttribute("categories")
    public Iterable<Category> categories() {
        return categoryService.findAll();
    }

    //    @GetMapping("/blogs")
//    public ModelAndView showList(@RequestParam("search") Optional<String> search,
//                                 @RequestParam("sort") Optional<String> sort,
//                                 @PageableDefault(value = 5) Pageable pageable){
//
//        Page<Blog> blogs;
//        if (search.isPresent()){
//            blogs = blogService.findAllByNameContaining(search.get(),pageable);
//        }else {
//            if (!sort.isPresent()){
//                pageable = PageRequest.of(0,5,Sort.by("time").ascending());
//                blogs = blogService.findAll(pageable);
//            }else {
//                if (sort.get().equals("new")){
//                    pageable = PageRequest.of(0,5,Sort.by("time").descending());
//                    blogs = blogService.findAll(pageable);
//                }else{
//                    blogs = blogService.findAll(pageable);
//                }
//            }
//        }
//
//        ModelAndView modelAndView = new ModelAndView("/blog/list");
//        modelAndView.addObject("blogs",blogs);
//        return modelAndView;
//    }
    @GetMapping("/blogs")
    public ModelAndView showListSort(@RequestParam("search") Optional<String> search,
                                     @RequestParam("sort") Optional<String> sort,
                                     @RequestParam("id") Optional<String> id,
                                     @PageableDefault(value = 5) Pageable pageable) {

        Iterable<Blog> blogs = null;
        if (id.isPresent()){
            Optional<Category> categoryOptional = categoryService.findById(Long.valueOf(id.get()));
            if(!categoryOptional.isPresent()){
                return new ModelAndView("/error.404");
            }

            blogs = blogService.findAllByCategory(categoryOptional.get());

            ModelAndView modelAndView = new ModelAndView("/blog/list");
            modelAndView.addObject("category", categoryOptional.get());
            modelAndView.addObject("blogs", blogs);
            return modelAndView;
        }
        if (search.isPresent()) {
            blogs = blogService.findAllByNameContaining(search.get(), pageable);
        } else {
            if (!sort.isPresent()) {
                pageable = PageRequest.of(0, 5, Sort.by("time").ascending());
                blogs = blogService.findAll(pageable);
            } else {
                switch (sort.get()) {
                    case "old":
                        pageable = PageRequest.of(0, 5, Sort.by("time").ascending());
                        blogs = blogService.findAll(pageable);
                        break;
                    case "id":
                        blogs = blogService.findAllByOrderById(pageable);
                        break;
                    case "name":
                        blogs = blogService.findAllByOrderByName(pageable);
                        break;
                    case "new":
                        pageable = PageRequest.of(0, 5, Sort.by("time").descending());
                        blogs = blogService.findAll(pageable);
                        break;
                }
            }
        }

        ModelAndView modelAndView = new ModelAndView("/blog/list");
        modelAndView.addObject("blogs", blogs);
        return modelAndView;
    }

    @GetMapping("/create-blog")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/blog/create");
        modelAndView.addObject("blog", new Blog());
        return modelAndView;
    }

    @PostMapping("/create-blog")
    public ModelAndView saveBlog(@ModelAttribute("blog") Blog blog) {
        blog.setTime(LocalDateTime.now());
        blogService.save(blog);
        ModelAndView modelAndView = new ModelAndView("/blog/create");
        modelAndView.addObject("blog", blog);
        modelAndView.addObject("mess", "New blog was created");
        return modelAndView;
    }

    @GetMapping("/edit-blog/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Blog blog = blogService.findById(id).get();
        if (blog != null) {
            ModelAndView modelAndView = new ModelAndView("/blog/edit");
            modelAndView.addObject("blog", blog);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error");
            return modelAndView;
        }
    }

    @PostMapping("/edit-blog")
    public ModelAndView updateBlog(@ModelAttribute("blog") Blog blog) {
        blogService.save(blog);
        ModelAndView modelAndView = new ModelAndView("/blog/edit");
        modelAndView.addObject("blog", blog);
        modelAndView.addObject("mess", "Blog was updated");
        return modelAndView;
    }

    @GetMapping("/delete-blog/{id}")
    public ModelAndView deleteForm(@PathVariable Long id) {
        Blog blog = blogService.findById(id).get();
        if (blog != null) {
            ModelAndView modelAndView = new ModelAndView("/blog/delete");
            modelAndView.addObject("blog", blog);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error");
            return modelAndView;
        }
    }

    @GetMapping("/detail-blog/{id}")
    public ModelAndView showDetail(@PathVariable Long id) {
        Blog blog = blogService.findById(id).get();
        if (blog != null) {
            ModelAndView modelAndView = new ModelAndView("/blog/detail");
            modelAndView.addObject("blog", blog);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error");
            return modelAndView;
        }
    }

    @PostMapping("/delete-blog")
    public String deleteBlog(@ModelAttribute("blog") Blog blog) {
        blogService.remove(blog.getId());
        return "redirect:blogs";
    }
}
