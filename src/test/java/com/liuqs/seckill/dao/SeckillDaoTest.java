package com.liuqs.seckill.dao;

import com.liuqs.seckill.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

/**
 * 配置spring和junit整合,这样junit在启动时就会加载spring容器
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 告诉junit spring的配置
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

    @Autowired
    private SeckillDao seckillDao;

    @Test
    public void reduceNumber() throws Exception {
        Date killTime = new Date();
        int row = seckillDao.reduceNumber(1004L, killTime);
        System.out.println(row);
    }

    @Test
    public void queryById() throws Exception {
        long seckill_id = 1004L;
        Seckill seckill = seckillDao.queryById(seckill_id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
    }

    @Test
    public void queryAll() throws Exception {
        List<Seckill> seckills = seckillDao.queryAll(0, 100);
        for (Seckill seckill : seckills) {
            System.out.println(seckill);
        }
    }

}