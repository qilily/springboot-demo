package com.example.springbootdemo.mybatis;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author HP-01
 * @create 2019/4/2 13:28
 */
@Data
public class OrderGoods {

    private Integer id;

    private Integer orderId;

    private Integer goodsId;

    private String goodsName;

    private String goodsSn;

    private Integer productId;

    private Short number;

    private BigDecimal price;

    private String[] specifications;

    private String picUrl;

    private Integer comment;

    private LocalDateTime addTime;

    private LocalDateTime updateTime;

    private Boolean deleted;
}
