package com.liuqs.seckill.service;

import com.liuqs.seckill.dto.Exposer;
import com.liuqs.seckill.dto.SeckillExecution;
import com.liuqs.seckill.entity.Seckill;
import com.liuqs.seckill.exception.RepeatKillException;
import com.liuqs.seckill.exception.SeckillCloseException;
import com.liuqs.seckill.exception.SeckillException;

import java.util.List;

/**
 * @ Author: liuqianshun
 * @ Description:
 *
 * 业务接口
 *
 * @ Date: Created in 2018-02-10
 * @ Modified:
 **/
public interface SeckillService {

    /**
     * 查询全部的秒杀记录
     * @return
     */
    List<Seckill>  getSeckillList();

    /**
     * 查询单个秒杀记录
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * 暴露秒杀信息
     * @param seckillId
     */
    Exposer exportSeckill(long seckillId);

    /**
     * 执行秒杀操作,有可能失败,有可能成功,所以要抛出我们允许的异常
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     * @throws SeckillException
     * @throws RepeatKillException
     * @throws SeckillCloseException
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException,RepeatKillException,SeckillCloseException;
}
