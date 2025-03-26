package com.ecommerce.project.service;


import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface ProductService {
    ProductDTO addProduct( Long categoryId,ProductDTO productDTO);

    ProductResponse getAllProducts(Integer pageNumber,Integer pageSize,String sortBy,String sortOrder);

    ProductResponse searchByCategory(Integer pageNumber,Integer pageSize,String sortBy,String sortOrder,Long categoryId);

    ProductResponse searchProductByKeyword(Integer pageNumber,Integer pageSize,String sortBy,String sortOrder,String keyword);

    ProductDTO updateProduct(ProductDTO productDTO, Long productId);

    ProductDTO deleteProductByProductId(Long productId);

    ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException;
}
