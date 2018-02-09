package com.liuqs.seckill.dao;

import com.liuqs.seckill.entity.SuccessKilled;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ Author: liuqianshun
 * @ Description:
 * @ Date: Created in 2018-02-09
 * @ Modified:
 **/
public interface SuccessKilledDao {

    /**
     * 插入秒杀成功明细,可过滤重复
     *
     * @param seckillId
     * @param userPhone
     * @return
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

    /**
     * 根据id查询秒杀明细详情(携带Seckill对象)
     *
     * @param seckillId
     * @param userPhone
     * @return
     */
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);
}
