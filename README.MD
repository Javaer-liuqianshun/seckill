# Java高并发秒杀系统API
视频学习地址：https://www.imooc.com/u/2145618/courses?sort=publish
# 学习原因及感悟
看了《Java编程思想》后，想找一个关于高并发多线程的视频看看，最终在慕课网找到《高并发秒杀系统API系统》。学习完后最深的感悟就是讲师很牛，从SSM框架的搭建到高并发秒杀系统项目的完成，一气呵成。
让自己的SSM更加熟练，也了解更多的编程的技巧以及SSM细节。


# 项目学习
Java高并发秒杀系统API主要分四个模块进行开发
* 1.Java高并发秒杀API之业务分析与DAO层
* 2.Java高并发秒杀API之Service层
* 3.Java高并发秒杀API之web层
* 4.Java高并发秒杀API之高并发优化

## 秒杀系统业务分析
![秒杀系统业务分析](https://camo.githubusercontent.com/a9c0692f158dda3474b98f673108479d06eabc2c/687474703a2f2f6f64327872663867722e626b742e636c6f7564646e2e636f6d2f254535254231253846254535254239253935254535254246254142254537253835254137253230323031362d31312d3237253230254534254238253842254535253844253838342e30372e33382e706e67)
如图所示:<br>
     其实秒杀系统本质是对库存管理的系统。<br>
    商家添加/调整库存，及确定发货/核账。<br>
    用户秒杀商品。<br>
    对于系统的操作就是 1.减库存 2.记录用户秒杀详情。<br>

## 开发步骤: dao层 -> service层 -> web层 -> 优化
### dao层
提供 减库存、分页查询商品、根据seckillId查询商品详情、插入秒杀详情及根据seckillId和userphone(联合主键)查询秒杀详情<br>
```
public interface SeckillDao {

    /**
     * 减库存
     *
     * @param seckillId 商品库存id
     * @param killTime  当前秒杀时间
     * @return
     */
    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);

    /**
     * 根据id查询秒杀商品信息
     *
     * @param seckillId
     * @return
     */
    Seckill queryById(long seckillId);

    /**
     * 分页查询商品
     *
     * @param offset
     * @param limit
     * @return
     */
    List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);
}
```
```
public interface SuccessKilledDao {

    /**
     * 插入秒杀成功明细,可过滤重复
     *
     * @param seckillId
     * @param userPhone
     * @return
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

    /**
     * 根据id查询秒杀明细详情(携带Seckill对象)
     *
     * @param seckillId
     * @param userPhone
     * @return
     */
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);
}
```
### service层
提供 查询所有秒杀商品、根据seckillId查询单个秒杀商品、暴露秒杀信息及执行秒杀操作<br>
暴露秒杀信息说明：<br>
    返回的信息包括md5和seckillId。md5的作用是在执行秒杀操作时，对md5进行验证，以免数据被篡改。seckillId的作用的确定秒杀的商品。
执行秒杀操作说明：
    执行秒杀操作的方法需要添加@Transactional进行事务管理，使减库存和插入用户秒杀详情操作要么一起成功，要么都不成功。<br>
    自定义了RepeatKillException、SeckillCloseException和SeckillException三个异常，针对不同的业务抛出不同的异常，并在Controller层针对不同的异常进行不同的处理。<br>
```
public interface SeckillService {

    /**
     * 查询全部的秒杀商品
     * @return
     */
    List<Seckill>  getSeckillList();

    /**
     * 查询单个秒杀商品
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * 暴露秒杀信息
     * @param seckillId
     */
    Exposer exportSeckill(long seckillId);

    /**
     * 执行秒杀操作,有可能失败,有可能成功,所以要抛出我们允许的异常
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     * @throws SeckillException
     * @throws RepeatKillException
     * @throws SeckillCloseException
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException,RepeatKillException,SeckillCloseException;
}
```
### web层
难点是对service层执行秒杀操作抛出异常进行捕获，并做不同的处理。<br>
```
@RequestMapping(value = "/{seckillId}/{md5}/execution",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
                                                   @PathVariable("md5") String md5,
                                                   @CookieValue(value = "userPhone", required = false) Long userPhone) {
        if (null == userPhone) {
            return new SeckillResult<SeckillExecution>(false, "未注册");
        }
        SeckillResult<SeckillExecution> result;
        try {
            SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, userPhone, md5);
            return new SeckillResult<SeckillExecution>(true, seckillExecution);
        } catch (RepeatKillException e) {
            // 重复秒杀异常捕获
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStateEnum.REPEAT_KILL);
            return new SeckillResult<SeckillExecution>(true, seckillExecution);
        } catch (SeckillCloseException e) {
            // 秒杀商品关闭（商品秒杀完毕、秒杀未开始及秒杀结束）异常捕获
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStateEnum.END);
            return new SeckillResult<SeckillExecution>(true, seckillExecution);
        } catch (Exception e) {
            // 业务异常捕获
            logger.error(e.getMessage(), e);
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStateEnum.INNER_ERROR);
            return new SeckillResult<SeckillExecution>(true, seckillExecution);
        }
    }
```
### 优化
* 1.秒杀商品详情可以把商品详情缓存在redis中。<br>
* 2.先记录秒杀信息再减库存。如果重复秒杀，那么记录秒杀信息不会成功，就不会再进行减库存操作，就减少##行级锁##等待的时间。<br>
