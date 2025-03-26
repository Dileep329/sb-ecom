package com.ecommerce.project.controller;

import com.ecommerce.project.config.AppConstants;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

@RestController
//@RequestMapping("/api") Instead /api every method we can mention it over here.
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/api/public/categories")
//    @RequestMapping(value="/api/public/categories",method=RequestMethod.GET)
    public ResponseEntity<CategoryResponse> getAllCategories(@RequestParam (name = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
                                                             @RequestParam (name = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
                                                             @RequestParam (name = "sortBy",defaultValue = AppConstants.SORT_CATEGORIES_BY,required = false)String sortBy,
                                                             @RequestParam (name = "sortOrder",defaultValue = AppConstants.SORT_DIR,required = false) String sortOrder) {
        CategoryResponse categoryResponse=categoryService.getAllCategories(pageNumber,pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }


    @PostMapping("/api/admin/createCategory")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        CategoryDTO savedCategoryDTO=categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(savedCategoryDTO,HttpStatus.CREATED);
    }

    @DeleteMapping("/api/admin/category/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId){
            CategoryDTO deleteCategory =categoryService.deleteCategory(categoryId);
            return new ResponseEntity<>(deleteCategory,HttpStatus.OK);
//        }
//        catch (ResponseStatusException e)
//        {
//            return new ResponseEntity<>(e.getReason(),e.getStatusCode());
//        }
    }
      @PutMapping("/api/admin/updateCategory/{categoryId}")
        public ResponseEntity<CategoryDTO> UpdateCategory(@Valid @RequestBody CategoryDTO categoryDTO,@PathVariable Long categoryId)
        {
//            try{
                CategoryDTO savedCategory=categoryService.updateCategory(categoryDTO,categoryId);
                return new ResponseEntity<>(savedCategory,HttpStatus.OK);
//            }
//            catch (ResponseStatusException e){
//                return new ResponseEntity<>(e.getReason(),e.getStatusCode());
//            }
        }
}
