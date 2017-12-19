package com.itself.test;

import redis.clients.jedis.Jedis;

public class RedisJava {

	
	public static void main(String[] args) {
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("192.168.139.150",6379);
        
        System.out.println("连接成功");
        System.out.println("查看key-cgy值"+jedis.get("cgy"));
    }
}
