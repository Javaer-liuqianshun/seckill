package com.liuqs.seckill.service;

import com.liuqs.seckill.dto.Exposer;
import com.liuqs.seckill.dto.SeckillExecution;
import com.liuqs.seckill.entity.Seckill;
import com.liuqs.seckill.exception.RepeatKillException;
import com.liuqs.seckill.exception.SeckillCloseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
// 告诉junit spring的配置
@ContextConfiguration({"classpath:spring/spring-dao.xml",
                        "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {

    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> seckillList = seckillService.getSeckillList();
        System.out.println(seckillList);
    }

    @Test
    public void getById() throws Exception {
        Seckill seckill = seckillService.getById(1004L);
        System.out.println(seckill);
    }

    @Test
    public void exportSeckill() throws Exception {
        Exposer exposer = seckillService.exportSeckill(1004L);
        System.out.println(exposer);
        /*
        Exposer{exposer=true, md5='cf7cf6f72b3ae47fcb1b9645324b407c', seckillId=1004, now=0, start=0, end=0}
         */
    }

    @Test
    public void executeSeckill() throws Exception {
        long id = 1004L;
        long userPhone  = 17628095831L;
        String md5 ="cf7cf6f72b3ae47fcb1b9645324b407c";
        SeckillExecution seckillExecution = seckillService.executeSeckill(id, userPhone, md5);
        System.out.println(seckillExecution);

    }

    /**
     * 完整逻辑测试,把exportSeckill()和executeSeckill()测试方法逻辑放在一起
     */
    @Test
    public void seckillLogic() throws Exception{
        long id = 1004L;
        Exposer exposer = seckillService.exportSeckill(id);
        if(exposer.isExposer()){
            long userPhone  = 17628095830L;
            String md5 = exposer.getMd5();
            try {
                SeckillExecution seckillExecution = seckillService.executeSeckill(id, userPhone, md5);
            }catch (RepeatKillException e1){
                e1.printStackTrace();
            }catch (SeckillCloseException e2){
                e2.printStackTrace();
            }
        }else {
            System.out.println("秒杀未开启");
        }
    }
}