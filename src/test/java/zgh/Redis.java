package zgh;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.Jedis;

public class Redis {
    private static final String URL = "47.107.157.244";

    @Test
    public void fn0() {
        Jedis jedis = null;
        try {
            jedis = new Jedis(URL);
            System.out.println("服务正在运行: " + jedis.ping());
        } finally {
            jedis.close();
        }
    }

    @Test
    public void fn1() {
        Jedis jedis = null;
        try {
            jedis = new Jedis(URL);
            // 设置 redis 字符串数据
            jedis.set("key1", "hello world 阿斯蒂芬");
            System.out.println("ok");
        } finally {
            jedis.close();
        }
    }
    
    @Test
    public void fn2() {
        Jedis jedis = null;
        try {
            jedis = new Jedis(URL);
            System.out.println("key1: " + jedis.get("key1"));
        } finally {
            jedis.close();
        }
    }
    
    @Test
    public void fn3() {
        Jedis jedis = null;
        try {
            jedis = new Jedis(URL);

            jedis.del("list1");
            
            //存储数据到列表中
            jedis.rpush("list1", "a1");
            jedis.rpush("list1", "b1");
            jedis.rpush("list1", "c1");
            jedis.rpush("list1", "d1");
            System.out.println(jedis.lrange("list1", 0, 10));
            jedis.lpush("list1", "begin");
            System.out.println(jedis.lrange("list1", 0, 10));
            jedis.lpop("list1");
            jedis.lpop("list1");
            System.out.println(jedis.lrange("list1", 0, 10));
            jedis.ltrim("list1", 0, 1);
            System.out.println(jedis.lrange("list1", 0, 10));

        } finally {
            jedis.close();
        }
    }
    
    @Test
    public void fn4() {

        Jedis jedis = null;
        try {
            jedis = new Jedis(URL);

            jedis.del("set1");
            
            jedis.sadd("set1", "a", "b", "c");
            jedis.sadd("set1", "d");
            jedis.sadd("set1", "a");
            
            Set<String> set = jedis.smembers("set1");
            System.out.println(set);
            
        } finally {
            jedis.close();
        }
    }


    @Test
    public void fn5() {
        Jedis jedis = null;
        try {
            jedis = new Jedis(URL);

            jedis.del("hash1");
            
            jedis.hset("hash1", "a", "a1");
            jedis.hset("hash1", "b", "a2");
            System.out.println(jedis.hget("hash1", "a"));
            System.out.println(jedis.hget("hash1", "b"));
            System.out.println(jedis.hgetAll("hash1"));
            
            Map<String, String> map = new HashMap();
            map.put("k1", "val1");
            map.put("k2", "val2");
            jedis.hmset("hash1", map);
            System.out.println(jedis.hget("hash1", "a"));
            System.out.println(jedis.hget("hash1", "b"));
            System.out.println(jedis.hgetAll("hash1"));
            
        } finally {
            jedis.close();
        }
    }
    
}
