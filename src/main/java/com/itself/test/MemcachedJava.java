package com.itself.test;

import java.net.InetSocketAddress;
import java.util.concurrent.Future;

import net.spy.memcached.MemcachedClient;


/**
 * memcached测试
 * @author lenovo
 *
 */
public class MemcachedJava {
	
	
	 public static void main(String[] args) {
	      try{
	         // 本地连接 Memcached 服务
	         MemcachedClient mcc = new MemcachedClient(new InetSocketAddress("192.168.139.152", 11211));
	         System.out.println("链接memcached服务成功.");
	         System.out.println("查看刚才是否存储成功"+mcc.get("cgy"));
	      // 存储数据
	         Future fo = mcc.set("cgy", 0, "这是替换前的值");
	         mcc.prepend(0,"cgy","-----追加");
	         // 查看存储状态
	         System.out.println("set status:" + fo.get());
	         // 输出值
	         System.out.println("cgy value in cache - " + mcc.get("cgy"));
	         
	         fo = mcc.replace("cgy", 0,"这是替换之后的值");
	         System.out.println("替换后的值--->"+mcc.get("cgy"));
	         // 关闭连接
	         mcc.shutdown();
	         
	      }catch(Exception ex){
	         System.out.println( ex.getMessage() );
	      }
	   }
}
