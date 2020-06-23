package com.cq.gmall.seckill.controller;

import com.cq.gmall.util.RedisUtil;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;

/**
 * @author 彭国仁
 * @data 2019/11/6 7:41
 */
@Controller
public class SecKillController {
    @RequestMapping("handler")
    @ResponseBody
    public String test() {
        return "nihao";
    }

    @Autowired
    RedisUtil redisUtil;
    @Autowired
    RedissonClient redissonClient;

    /***
     * 先到先得式秒杀
     * @return
     */
    @RequestMapping("secKill")
    @ResponseBody
    public String secKill() {
        Jedis jedis = redisUtil.getJedis();
        int stock = Integer.parseInt(jedis.get("106"));
        System.out.println("当前库存剩余数量" + stock);
        RSemaphore semaphore = redissonClient.getSemaphore("106");
        boolean b = semaphore.tryAcquire();
        if (b) {
            System.out.println("当前库存剩余数量" + stock + ",某用户抢购成功，当前抢购人数：" +(1000-stock) );
            // 用消息队列发出订单消息
        } else {
            System.out.println("当前库存剩余数量" + stock + ",某用户抢购失败");
        }
        jedis.close();
        return "1";
    }

    /***
     * 随机拼运气式秒杀
     * @return
     */
    @RequestMapping("kill")
    @ResponseBody
    public String kill() {
        Jedis jedis = redisUtil.getJedis();
        // 开启商品的监控
        jedis.watch("106");
        int stock = Integer.parseInt(jedis.get("106"));
        if (stock > 0) {
            Transaction multi = jedis.multi();
            multi.incrBy("106", -1);
            List<Object> exec = multi.exec();
            if (exec != null && exec.size() > 0) {
                System.out.println("当前库存剩余数量" + stock + ",某用户抢购成功，当前抢购人数：" + (1000 - stock));
                // 用消息队列发出订单消息
            } else {
                System.out.println("当前库存剩余数量" + stock + ",某用户抢购失败");
            }

        }
        jedis.close();
        return "1";
    }
}
