package com.example.demo.Service.impl;

import com.example.demo.Service.OrderService;
import com.example.demo.Service.ProductService;
import com.example.demo.dao.OrderMapper;
import com.example.demo.model.Order;
import com.example.demo.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author shkstart
 * @create 2020-05-25 14:04
 */
@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderMapper orderMapper;

    @Transactional
    @Override
    public int makeOrder(Integer id) {
        Product product = productService.selectByProductId(id);

        if(product.getStock() <= 0){
            throw new RuntimeException("库存不足");
        }

        productService.downProduct(id);
        Order order = new Order();
        order.setAmount(1L);
        order.setProductId(id);

         return orderMapper.insertSelective(order);

    }
}
