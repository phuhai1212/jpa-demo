package com.example.jpademo.controller;

import com.example.jpademo.model.Category;
import com.example.jpademo.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("categories")
public class CategoryController {
  @Autowired
  private CategoryRepo categoryRepo;

  @GetMapping
  public String list(ModelMap model, @RequestParam Optional<String> message){
    Iterable<Category> list = categoryRepo.findAll();

    if(message.isPresent()){
      model.addAttribute("message",message.get());
    }
    model.addAttribute("categories", list);
    return "categories/list";
  }

  @GetMapping("sort")
  public String sort(ModelMap model, @RequestParam Optional<String> message,
                     @SortDefault(sort = "name", direction = Sort.Direction.ASC) Sort sort){
    Iterable<Category> list = categoryRepo.findAll(sort);

    if(message.isPresent()){
      model.addAttribute("message",message.get());
    }
    model.addAttribute("categories", list);
    return "categories/sort";
  }

  @GetMapping("newOrEdit")
  public String newOrEdit(ModelMap model){
    Category category = new Category();
    model.addAttribute("category",category);

    return "categories/newOrEdit";
  }
  @PostMapping("saveOrUpdate")
  public String saveOrUpdate(RedirectAttributes redirectAttributes, Category item){
    categoryRepo.save(item);
    redirectAttributes.addAttribute("message","New category is saved!");
    return "redirect:/categories";
  }

}
