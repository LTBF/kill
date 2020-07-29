package com.example.demo.Service.impl;

import com.example.demo.Service.ProductService;
import com.example.demo.dao.ProductMapper;
import com.example.demo.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author shkstart
 * @create 2020-05-25 10:56
 */
@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Product selectByProductId(Integer id) {

        Product product = productMapper.selectByPrimaryKey(id);

        return product;

    }

    @Transactional
    @Override
    public void downProduct(Integer id) {
        productMapper.updateByProductId(id);
    }

    @Override
    public List<Product> listAll() {

        return productMapper.selectAll();
    }
}
