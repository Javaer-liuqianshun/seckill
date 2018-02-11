package com.liuqs.seckill.exception;

/**
 * @ Author: liuqianshun
 * @ Description:
 *
 * 秒杀关闭异常
 * 当秒杀结束时用户还要进行秒杀就会出现这个异常
 *
 * @ Date: Created in 2018-02-10
 * @ Modified:
 **/
public class SeckillCloseException extends SeckillException {
    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
