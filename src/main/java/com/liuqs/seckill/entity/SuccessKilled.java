package com.liuqs.seckill.entity;

import lombok.Data;

import java.util.Date;

/**
 * @ Author: liuqianshun
 * @ Description:
 * @ Date: Created in 2018-02-09
 * @ Modified:
 **/
@Data
public class SuccessKilled {
    private long seckillId;
    private long userPhone;
    private short state;
    private Date createTime;

    /**
     * 秒杀明细和秒杀商品是多对一的关系
     * 在多的方添加一的实体对象
     */
    private Seckill secKill;

    @Override
    public String toString() {
        return "SuccessKilled{" +
                "seckillId=" + seckillId +
                ", userPhone=" + userPhone +
                ", state=" + state +
                ", createTime=" + createTime +
                '}';
    }
}
