package com.liuqs.seckill.dao;

import com.liuqs.seckill.entity.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;
/**
 * 配置spring和junit整合,这样junit在启动时就会加载spring容器
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 告诉junit spring的配置
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {
    @Autowired
    private SuccessKilledDao successKilledDao;

    @Test
    public void insertSuccessKilled() throws Exception {
        /**
         * 第一次输出 1
         * 第二次输出 0
         *
         * 说明主键冲突.重复秒杀
         */
        int row = successKilledDao.insertSuccessKilled(1004L, 17628976543L);
        System.out.println(row);
    }

    @Test
    public void queryByIdWithSeckill() throws Exception {
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(1004L, 17628976543L);
        System.out.println(successKilled);
        System.out.println(successKilled.getSecKill());
    }

}