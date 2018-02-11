package com.liuqs.seckill.enums;

import lombok.Getter;

/**
 * @ Author: liuqianshun
 * @ Description:
 * @ Date: Created in 2018-02-10
 * @ Modified:
 **/
@Getter
public enum SeckillStateEnum {

    SUCCESS(1, "秒杀成功"),
    END(0, "秒杀结束"),
    REPEAT_KILL(-1, "重复秒杀"),
    INNER_ERROR(-2, "系统异常"),
    DATE_REWRITE(-3, "数据篡改"),

    ;
    private Integer state;
    private String info;

    SeckillStateEnum(Integer state, String info) {
        this.state = state;
        this.info = info;
    }

}
