package com.liuqs.seckill.dao;

import com.liuqs.seckill.entity.Seckill;
import org.apache.ibatis.annotations.Param;

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
     *
     * @param seckillId 商品库存id
     * @param killTime  当前秒杀时间
     * @return
     */
    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);

    /**
     * 根据id查询秒杀商品信息
     *
     * @param seckillId
     * @return
     */
    Seckill queryById(long seckillId);

    /**
     * 分页查询商品
     *
     * @param offset
     * @param limit
     * @return
     */
    List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);
}
