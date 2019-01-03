package com.stream.demo.core.common;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 统计缓存操作类
 * Created by machi on 16-11-22.
 */
public class BICache {
    /**
     * 终端id
     */
    private Long terminalId;


    public BICache() {
    }

    /**
     * 取得
     *
     * @return
     */
    public Map<String, String> getCountCache() {
        String preKey = KeyPrefix.buildKey(KeyPrefix.PRE, terminalId);
        String countKey = KeyPrefix.buildKey(KeyPrefix.COUNT, terminalId);
        ArrayList<String> keys = new ArrayList<>();
        keys.add(preKey);
        keys.add(countKey);
        return Redis.getInstance().getValuePipeline(keys);
    }

    /**
     * 设置
     *
     * @param map map
     */
    public void setCountCache(Map<String, String> map) {
        Jedis jedis = null;
        try {
            jedis = Redis.getInstance().getRedisTemplate();
            Pipeline p = jedis.pipelined();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                p.set(entry.getKey(), entry.getValue());
            }
            p.sadd(KeyPrefix.STREAM_TERMINAL_ID, String.valueOf(terminalId));
            p.sync();
            Redis.getInstance().closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            Redis.getInstance().closeBreakJedis(jedis);
        }
    }

    public Map<String, String> getCache() {
        String preKey = KeyPrefix.buildKey(KeyPrefix.PRE, terminalId);
        String countKey = KeyPrefix.buildKey(KeyPrefix.COUNT, terminalId);
        Jedis jedis = null;
        HashMap<String, String> result = new HashMap<>(16);
        try {
            Map<String, Response<String>> responses = new HashMap<>(3);
            jedis = Redis.getInstance().getRedisTemplate();
            Pipeline p = jedis.pipelined();

            responses.put(preKey, p.get(preKey));
            responses.put(countKey, p.get(countKey));
            p.sync();
            for (String k : responses.keySet()) {
                result.put(k, responses.get(k).get());
            }
            Redis.getInstance().closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            Redis.getInstance().closeBreakJedis(jedis);
        }

        return result;
    }

    /**
     * 测试
     *
     * @param args 参数
     */
    public void main(String[] args) throws Exception {
        Map<String, Object> poolConf = new HashMap<String, Object>();
        poolConf.put("redis.redis.max_idle", 2000);
        poolConf.put("redis.isTest", false);
        poolConf.put("redis.node", "master1");
        poolConf.put("redis.server", "10.30.50.151:26366");
        poolConf.put("redis.timeout", 20000);
        Redis.buildInstance(poolConf);
//        addRapidAcceleration("11111111111111111");
//        addRapidDeceleration("11111111111111111");
    }

}
