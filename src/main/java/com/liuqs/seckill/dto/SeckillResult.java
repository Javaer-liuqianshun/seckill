package com.liuqs.seckill.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @ Author: liuqianshun
 * @ Description:
 * @ Date: Created in 2018-02-12
 * @ Modified:
 **/
@Getter
@Setter
public class SeckillResult<T> {
    private boolean success;
    private T data;
    private String error;

    public SeckillResult(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public SeckillResult(boolean success, String error) {
        this.success = success;
        this.error = error;
    }
}
