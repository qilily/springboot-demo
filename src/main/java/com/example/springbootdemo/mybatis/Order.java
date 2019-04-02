package com.example.springbootdemo.mybatis;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单表
 *
 * @author HP-01
 * @create 2019/4/2 13:25
 */
@Data
public class Order {
    private Integer id;

    /**
     * 用户表的用户ID
     */
    private Integer userId;

    /**
     * @Description: 订单关联的分销码（用于计算佣金）
     */
    private String code;

    /**
     * @Description: 订单编号
     */
    private String orderSn;

    /**
     * @Description: 订单状态
     */
    private Short orderStatus;

    /**
     * @Description: 收货人名称
     */
    private String consignee;

    /**
     * @Description: 收货人手机号
     */
    private String mobile;

    /**
     * @Description: 收货具体地址
     */
    private String address;

    /**
     * @Description: 用户订单留言
     */
    private String message;

    /**
     * @Description: 商品总费用
     */
    private BigDecimal goodsPrice;

    /**
     * @Description: 配送费用
     */
    private BigDecimal freightPrice;

    /**
     * @Description: 优惠券减免
     */
    private BigDecimal couponPrice;

    /**
     * @Description: 用户积分减免
     */
    private BigDecimal integralPrice;

    /**
     * @Description: 团购优惠价减免
     */
    private BigDecimal grouponPrice;

    /**
     * @Description: 订单费用， = goods_price + freight_price - coupon_price
     */
    private BigDecimal orderPrice;

    /**
     * @Description: 实付费用， = order_price - integral_price
     */
    private BigDecimal actualPrice;

    /**
     * @Description: 微信付款编号
     */
    private String payId;

    /**
     * @Description: 微信付款时间
     */
    private LocalDateTime payTime;

    /**
     * @Description: 发货编号
     */
    private String shipSn;

    /**
     * @Description: 发货快递公司
     */
    private String shipChannel;

    /**
     * @Description: 发货开始时间
     */
    private LocalDateTime shipTime;

    /**
     * @Description: 用户确认收货时间
     */
    private LocalDateTime confirmTime;

    /**
     * @Description: 待评价订单商品数量
     */
    private Short comments;

    /**
     * @Description: 订单关闭时间
     */
    private LocalDateTime endTime;

    /**
     * @Description: 创建时间
     */
    private LocalDateTime addTime;

    /**
     * @Description: 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * @Description: 逻辑删除
     */
    private Boolean deleted;

    /** 订单商品 */
    private List<OrderGoods> goodsList;
}
