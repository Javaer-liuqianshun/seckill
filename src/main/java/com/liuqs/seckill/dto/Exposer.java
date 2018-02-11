package com.liuqs.seckill.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @ Author: liuqianshun
 * @ Description:
 *
 * 暴露秒杀信息 dto
 *
 * @ Date: Created in 2018-02-10
 * @ Modified:
 **/
@Getter
@Setter
public class Exposer {

    // 是否开启秒杀
    private boolean exposer;

    // 加密措施
    private String md5;

    private long seckillId;

    // 系统当前时间
    private long now;

    // 秒杀的开启时间
    private long start;

    // 秒杀的结束时间
    private long end;

    public Exposer(boolean exposer, long seckillId) {
        this.exposer = exposer;
        this.seckillId = seckillId;
    }

    public Exposer(boolean exposer, String md5, long seckillId) {
        this.exposer = exposer;
        this.md5 = md5;
        this.seckillId = seckillId;
    }

    public Exposer(boolean exposer, long seckillId, long now, long start, long end) {
        this.exposer = exposer;
        this.seckillId = seckillId;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return "Exposer{" +
                "exposer=" + exposer +
                ", md5='" + md5 + '\'' +
                ", seckillId=" + seckillId +
                ", now=" + now +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
