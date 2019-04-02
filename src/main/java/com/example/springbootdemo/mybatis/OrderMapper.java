package com.example.springbootdemo.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author HP-01
 * @create 2019/4/2 13:30
 */
@Mapper
public interface OrderMapper {

    @Select("SELECT * FROM litemall_order WHERE order_sn = #{orderSn}")
    List<Order> listByOrderSn(@Param("orderSn") String orderSn);
}
