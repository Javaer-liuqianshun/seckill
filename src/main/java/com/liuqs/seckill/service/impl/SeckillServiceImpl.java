package com.liuqs.seckill.service.impl;

import com.liuqs.seckill.dao.SeckillDao;
import com.liuqs.seckill.dao.SuccessKilledDao;
import com.liuqs.seckill.dao.cache.RedisDao;
import com.liuqs.seckill.dto.Exposer;
import com.liuqs.seckill.dto.SeckillExecution;
import com.liuqs.seckill.entity.Seckill;
import com.liuqs.seckill.entity.SuccessKilled;
import com.liuqs.seckill.enums.SeckillStateEnum;
import com.liuqs.seckill.exception.RepeatKillException;
import com.liuqs.seckill.exception.SeckillCloseException;
import com.liuqs.seckill.exception.SeckillException;
import com.liuqs.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * @ Author: liuqianshun
 * @ Description:
 * @ Date: Created in 2018-02-10
 * @ Modified:
 **/
@Service
public class SeckillServiceImpl implements SeckillService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //加入盐值,盐值是一个混淆字符串,为了我避免用户猜出我们的md5值,值任意给,越复杂越好
    private final String salt = "wrafdsgf32reg45a1234";

    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private SuccessKilledDao successKilledDao;
    @Autowired
    private RedisDao redisDao;

    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 4);
    }

    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    public Exposer exportSeckill(long seckillId) {
        // 优化点: 缓存优化:超时的基础上维护一致性
        // 1.访问redis
        Seckill seckill = redisDao.getSeckill(seckillId);
        if (null == seckill) {
            // 2.访问数据库
            seckill = seckillDao.queryById(seckillId);
            if (null == seckill) {
                return new Exposer(false, seckillId);
            } else {
                // 3.放入redis
                redisDao.putSeckill(seckill);
            }
        }
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        // 系统当前时间
        Date nowTime = new Date();
        if (startTime.getTime() > nowTime.getTime() || endTime.getTime() < nowTime.getTime()) {
            return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }
        String md5 = getMD5(seckillId);
        return new Exposer(true, md5, seckillId);
    }


    @Transactional
    /**
     * 使用注解控制事务方法的优点:
     * 1.开发团队达成一致约定,明确标注事务方法的编程风格
     * 2.保证事务方法执行时间尽量短,不要穿插其他网络操作RPC/HTTP请求,或者剥离到事务方法外部
     * 3.不是所有的方法都需要事务,如只有一条修改操作,只读操作不需要事务控制
     */
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException, RepeatKillException, SeckillCloseException {
        // 1.检查md5是否被篡改
        if (md5 == null || !md5.equals(getMD5(seckillId))) {
            // 抛出秒杀数据被篡改的异常
            throw new SeckillException("seckill data rewrite");
        }
        // 2.执行秒杀逻辑: 减库存 + 记录秒杀信息
        Date nowTime = new Date();
        try {
            // 记录秒杀信息
            int insertRow = successKilledDao.insertSuccessKilled(seckillId, userPhone);
            if (insertRow <= 0) {
                // 抛出重复秒杀异常
                throw new RepeatKillException("seckill repeated");
            } else {
                // 减库存,热点商品竞争
                int updateRow = seckillDao.reduceNumber(seckillId, nowTime);
                if (updateRow <= 0) {
                    // 抛出秒杀已结束异常
                    throw new SeckillCloseException("seckill is closed");
                } else {
                    // 秒杀成功,查询秒杀信息
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
                }
            }

        } catch (RepeatKillException e1) {
            throw e1;
        } catch (SeckillCloseException e2) {
            throw e2;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            // 将编译器异常转换成运行期异常
            throw new SeckillException("seckill inner error: " + e.getMessage());
        }
    }

    private String getMD5(long seckillId) {
        String base = seckillId + "/" + salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }
}
