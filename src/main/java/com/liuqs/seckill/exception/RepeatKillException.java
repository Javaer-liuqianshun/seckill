package com.liuqs.seckill.exception;

/**
 * @ Author: liuqianshun
 * @ Description:
 *
 * 重复秒杀异常
 *
 * * @ Date: Created in 2018-02-10
 * @ Modified:
 **/
public class RepeatKillException extends SeckillException {
    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
