package com.example.demo.controller;

import com.example.demo.Service.OrderService;
import com.example.demo.Service.ProductService;
import com.example.demo.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shkstart
 * @create 2020-05-25 14:01
 */
@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static Map<String, Boolean> productMap = new HashMap<>();

    @PostConstruct
    public void init(){
        List<Product> products = productService.listAll();
        for (Product p : products) {
            stringRedisTemplate.opsForValue().set("product_stock_" + p.getId(), p.getStock() + "");
        }
    }

    @RequestMapping("/order/{id}")
    public void makeOrder(@PathVariable Integer id){

        if(productMap.get(String.valueOf(id)) != null){
            return;
        }

        Long stock = stringRedisTemplate.opsForValue().decrement("product_stock_" + id);
        if(stock < 0){
            productMap.put(String.valueOf(id), true);
            stringRedisTemplate.opsForValue().increment("product_stock_" + id);
            return;
        }
        try{
            orderService.makeOrder(id);
        }catch (Exception e){
            stringRedisTemplate.opsForValue().increment("product_stock_" + id);
            productMap.remove(String.valueOf(id));
            e.printStackTrace();
        }
    }


}
