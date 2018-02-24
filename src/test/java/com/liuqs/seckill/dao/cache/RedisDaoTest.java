package com.liuqs.seckill.dao.cache;

import com.liuqs.seckill.dao.SeckillDao;
import com.liuqs.seckill.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisDaoTest {

    private int id = 1004;

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private SeckillDao seckillDao;

    @Test
    public void redisSeckillLogicTest() throws Exception {
        Seckill seckill = redisDao.getSeckill(id);
        if (null == seckill) {
            seckill = seckillDao.queryById(id);
            if (null != seckill) {
                String result = redisDao.putSeckill(seckill);
                System.out.println(result);
                seckill = redisDao.getSeckill(id);
                System.out.println(seckill);
            }
        }
    }
}