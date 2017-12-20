package com.itself.test;

import redis.clients.jedis.Jedis;

public class RedisJava {

	
	public static void main(String[] args) {
		final Jedis jedis = new Jedis("192.168.139.150",6379);
        
        System.out.println("连接成功");
        System.out.println("查看key-cgy值"+jedis.get("cgy"));
        
        jedis.set("test","testval");
        jedis.expire("test",10);
        
        UserTest user = new UserTest();
        user.setPassword("123456");
        user.setUsername("testuser");
        
        //连接本地的 Redis 服务
        Thread t = new Thread(new Runnable() {
        	
			public void run() {
			
	            
				while(true){
					// TODO Auto-generated method stub
					try {
						if("".equals(jedis.get("test"))||jedis.get("test")==null){
							System.out.println("数据销毁了"+jedis.get("test"));
							continue;
						}
						System.out.println("查看数据"+jedis.get("test"));
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		
		});
        t.start();
        
    }
}
