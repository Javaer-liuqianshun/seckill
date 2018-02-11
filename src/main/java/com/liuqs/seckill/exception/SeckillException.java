package com.liuqs.seckill.exception;

/**
 * @ Author: liuqianshun
 * @ Description:
 *
 * 秒杀相关的所有异常
 *
 * @ Date: Created in 2018-02-10
 * @ Modified:
 **/
public class SeckillException extends RuntimeException {

    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
