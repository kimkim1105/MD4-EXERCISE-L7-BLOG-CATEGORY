package com.codegym.controller;

import com.codegym.model.Blog;
import com.codegym.model.Category;
import com.codegym.service.IBlogService;
import com.codegym.service.category.ICategoryService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private IBlogService blogService;

    @GetMapping("/categories")
    public ModelAndView showList() {
        ModelAndView modelAndView = new ModelAndView("/category/list");
        Iterable<Category> categories = categoryService.findAll();
        modelAndView.addObject("categories", categories);
        return modelAndView;
    }

    @GetMapping("/create-category")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/category/create");
        modelAndView.addObject("category", new Category());
        return modelAndView;
    }

    @PostMapping("/create-category")
    public ModelAndView createCategory(@ModelAttribute("category") Category category) {
        categoryService.save(category);
        ModelAndView modelAndView = new ModelAndView("/category/create");
        modelAndView.addObject("category", new Category());
        modelAndView.addObject("mess", "New province created successfully");
        return modelAndView;
    }
    @GetMapping("/edit-category/{id}")
    public ModelAndView showEditForm(@PathVariable Long id){
        Category category = categoryService.findById(id).get();
        if (category!=null){
            ModelAndView modelAndView = new ModelAndView("/category/edit");
            modelAndView.addObject("category",category);
            return modelAndView;
        }else {
            ModelAndView modelAndView = new ModelAndView("error");
            return modelAndView;
        }
    }
    @PostMapping("/edit-category")
    public ModelAndView editCategory(@ModelAttribute("category") Category category){
        categoryService.save(category);
        ModelAndView modelAndView = new ModelAndView("/category/edit");
        modelAndView.addObject("category",category);
        modelAndView.addObject("mess", "Province updated successfully");
        return modelAndView;
    }
    @GetMapping("/delete-category/{id}")
    public ModelAndView showdeleteForm(@PathVariable Long id){
        Category category = categoryService.findById(id).get();
        ModelAndView modelAndView;
        if (category!=null){
            modelAndView = new ModelAndView("/category/delete");
            modelAndView.addObject("category",category);
        }else {
            modelAndView= new ModelAndView("error");
        }
        return modelAndView;
    }
    @PostMapping("/delete-category")
    public String deleteCategory(@ModelAttribute("catrgory") Category category){
        categoryService.remove(category.getId());
        return "redirect:categories";
    }
    @GetMapping("/view-category/{id}")
    public ModelAndView viewCategory(@PathVariable("id") Long id){
        Optional<Category> categoryOptional = categoryService.findById(id);
        if(!categoryOptional.isPresent()){
            return new ModelAndView("/error.404");
        }

        Iterable<Blog> blogs = blogService.findAllByCategory(categoryOptional.get());

        ModelAndView modelAndView = new ModelAndView("/category/view");
        modelAndView.addObject("category", categoryOptional.get());
        modelAndView.addObject("blogs", blogs);
        return modelAndView;
    }
}
