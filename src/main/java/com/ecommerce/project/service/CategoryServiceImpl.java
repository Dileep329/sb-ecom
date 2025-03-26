package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.repositories.CategoryRepository;
import io.micrometer.common.KeyValues;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.parser.Part;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService{

//     List<Category> categories= new ArrayList<>();
     @Autowired
     private CategoryRepository categoryRepository;
    @Autowired
     private ModelMapper modelMapper;
//     private Long nextId=1L;
    @Override
    public CategoryResponse getAllCategories(Integer pageNumber,Integer pageSize,String sortBy,String sortOrder) {
        Sort sortByAndOrder=sortOrder.equalsIgnoreCase("asc")
                ?Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();
        Pageable pageDetails= PageRequest.of(pageNumber,pageSize,sortByAndOrder);
        Page<Category> categoryPage=categoryRepository.findAll(pageDetails);
        List<Category> savedCategory=categoryPage.getContent();
        if(savedCategory.isEmpty())
            throw new APIException("No categories created till now");
        List<CategoryDTO> categoryDTOS=savedCategory.stream().map(category -> modelMapper.map(category, CategoryDTO.class))
                    .toList();
            CategoryResponse categoryResponse=new CategoryResponse();
            categoryResponse.setContent(categoryDTOS);
            categoryResponse.setPageNumber(categoryPage.getNumber());
            categoryResponse.setPageSize(categoryPage.getSize());
            categoryResponse.setTotalElements(categoryPage.getTotalElements());
            categoryResponse.setTotalPages(categoryPage.getTotalPages());
            categoryResponse.setLastPage(categoryPage.isLast());
            return categoryResponse;
    }


    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
//        category.setCategoryId(nextId++);
//        categories.add(category);
        Category category=modelMapper.map(categoryDTO,Category.class);
        Category savedCategory=categoryRepository.findByCategoryName(category.getCategoryName());
        if(savedCategory!=null){
            throw new APIException("Category with the name '"+ category.getCategoryName()+"' already exists");
        }
        else {
            Category repoCategory=categoryRepository.save(category);
//            CategoryDTO savedCategoryDTO=modelMapper.map(repoCategory,CategoryDTO.class);
            return modelMapper.map(repoCategory,CategoryDTO.class);
        }
    }


    @Override
    public CategoryDTO deleteCategory(Long categoryId) {

        Optional<Category> categories=categoryRepository.findById(categoryId);
        categories.orElseThrow(()->new ResourceNotFoundException("Category","categoryId",categoryId));
        CategoryDTO deletedCategoryDTO=modelMapper.map(categories,CategoryDTO.class);
        categoryRepository.deleteById(categoryId);
//        Category category=categories.stream().filter(c->c.getCategoryId().equals(categoryId)).
//                findFirst().orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource not found"));
////        categories.remove(category);
//        categoryRepository.delete(category);
        return deletedCategoryDTO;
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        Category category=modelMapper.map(categoryDTO,Category.class);
//        List<Category> categories=categoryRepository.findAll();
        Optional<Category> savedCategoryOptional=categoryRepository.findById(categoryId);
        Category savedCategory=savedCategoryOptional.orElseThrow(()->new ResourceNotFoundException("Category","categoryId",categoryId));
          category.setCategoryId(categoryId);
          Category RepoCategory=categoryRepository.save(category);
//          CategoryDTO categoryDTO1=modelMapper.map(RepoCategory,CategoryDTO.class);
          return modelMapper.map(RepoCategory,CategoryDTO.class);

//        Optional<Category> optionalCategory=categories.stream().filter(c->c.getCategoryId().equals(categoryId)).findFirst();
//        if(optionalCategory.isPresent()){
//            Category existingCategory=optionalCategory.get();
//            existingCategory.setCategoryName(category.getCategoryName());
//            Category savedCategory=categoryRepository.save(existingCategory);
//            return existingCategory;
//        }
//        else {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not found");
//        }
    }
}
