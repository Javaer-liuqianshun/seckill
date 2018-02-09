package com.liuqs.seckill.entity;

import lombok.Data;

import java.util.Date;

/**
 * @ Author: liuqianshun
 * @ Description:
 * @ Date: Created in 2018-02-09
 * @ Modified:
 **/
@Data
public class Seckill {
    private long seckillId;
    private String name;
    private Integer number;
    private Date startTime;
    private Date endTime;
    private Date createTime;
}
