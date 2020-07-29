package com.example.demo.Service;

import com.example.demo.model.Product;

import java.util.List;

/**
 * @author shkstart
 * @create 2020-05-25 10:41
 */
public interface ProductService {

    Product selectByProductId(Integer id);

    void downProduct(Integer id);

    List<Product> listAll();

}
