package com.example.springbootdemo;

import com.example.springbootdemo.mybatis.Order;
import com.example.springbootdemo.mybatis.OrderMapper;
import org.aspectj.weaver.ast.Or;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootDemoApplicationTests {

    @Resource
    private OrderMapper orderMapper;

    @Test
    public void orderTest() {
        String orderSn = "20190323032441";
        List<Order> orders = orderMapper.listByOrderSn(orderSn);
        for (Order o : orders) {
            System.out.println(o);
        }
    }

}
