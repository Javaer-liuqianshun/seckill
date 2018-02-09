package com.liuqs.seckill.dao;

import com.liuqs.seckill.entity.Seckill;

import java.util.Date;
import java.util.List;

/**
 * @ Author: liuqianshun
 * @ Description:
 * @ Date: Created in 2018-02-09
 * @ Modified:
 **/
public interface SeckillDao {

    /**
     * 减库存
     * @param seckillId 商品库存id
     * @param killTime 创建时间
     * @return
     */
    int reduceNumber(long seckillId,Date killTime);

    /**
     * 根据id查询秒杀商品信息
     * @param seckillId
     * @return
     */
    Seckill queryById(long seckillId);

    /**
     * 分页查询商品
     * @param offset
     * @param limit
     * @return
     */
    List<Seckill> queryAll(int offset, int limit);
}
