package com.liuqs.seckill.dto;

import com.liuqs.seckill.entity.SuccessKilled;
import com.liuqs.seckill.enums.SeckillStateEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @ Author: liuqianshun
 * @ Description:
 * <p>
 * 封装执行秒杀后的结果:是否秒杀成功 dto
 * @ Date: Created in 2018-02-10
 * @ Modified:
 **/
@Getter
@Setter
public class SeckillExecution {

    private long seckillId;

    // 秒杀执行的状态
    private int state;

    // 状态的明文标识
    private String stateInfo;

    // 当秒杀成功时,需要传递秒杀成功的对象回去
    private SuccessKilled successKilled;

    public SeckillExecution(long seckillId, SeckillStateEnum seckillStateEnum) {
        this.seckillId = seckillId;
        this.state = seckillStateEnum.getState();
        this.stateInfo = seckillStateEnum.getInfo();
    }

    public SeckillExecution(long seckillId, SeckillStateEnum seckillStateEnum, SuccessKilled successKilled) {
        this.seckillId = seckillId;
        this.state = seckillStateEnum.getState();
        this.stateInfo = seckillStateEnum.getInfo();
        this.successKilled = successKilled;
    }

    @Override
    public String toString() {
        return "SeckillExecution{" +
                "seckillId=" + seckillId +
                ", state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", successKilled=" + successKilled +
                '}';
    }
}
