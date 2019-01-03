package com.stream.demo.core.common;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;
import redis.clients.jedis.params.geo.GeoRadiusParam;
import redis.clients.util.Pool;

import java.io.Serializable;
import java.util.*;


/**
 * 哨兵模式连接redis集群
 *
 * @author lichao
 */
public final class Redis implements Serializable {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(Redis.class);

    /**
     * Redis实例
     */
    private static volatile Redis instance = null;

    /**
     * redis池
     */
    private Pool pool;

    /**
     * redis db索引
     */
    private int dbIndex = 0;

    private Redis() {
    }

    /**
     * @return redis实例
     */
    public static Redis getInstance() {
        if (instance == null) {
            throw new RuntimeException("Do not initialize!!!");
        }
        return instance;
    }

    /**
     * 实例化redis对象
     *
     * @param conf 配置参数
     */
    public static void buildInstance(Map conf) throws Exception {
        if (instance == null) {
            synchronized (Redis.class) {
                if (instance == null) {
                    Redis local = new Redis();
                    local.init(conf);
                    instance = local;
                }
            }
        }
    }

    public static void main(String[] args) {
        HashMap<String, String> param = new HashMap<>(16);
        //param.put("redis.server", "10.30.10.23:26369");
        //param.put("redis.node", "master1");
        param.put("redis.host", "10.30.10.13");
        param.put("redis.port", "19001");
        //param.put("redis.password", "123456");
        param.put("redis.max_idle", "2000");
        param.put("redis.maxTotal", "100");
        param.put("redis.timeout", "20000");
        param.put("redis.isTest", "false");
        param.put("redis.db.index", "0");

        try {
            Redis.buildInstance(param);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Redis instance = Redis.getInstance();
        instance.setValue("qweasdzxc", "2222222222222");
        String qweasdzxc = instance.getValue("qweasdzxc");
        System.out.println(qweasdzxc);

    }

    /**
     * 初始化
     *
     * @param conf 配置参数
     */
    private void init(Map conf) {
        logger.info("开始初始化redis连接池");
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(Integer.parseInt((String) (conf.get("redis.max_idle"))));
        config.setTestOnBorrow(Boolean.valueOf((String) (conf.get("redis.isTest"))));
        config.setMaxTotal(Integer.parseInt((String) (conf.get("redis.maxTotal"))));
        String password = (String) conf.get("redis.password");
        int timeOut = Integer.parseInt((String) (conf.get("redis.timeout")));
        dbIndex = Integer.parseInt((String) (conf.get("redis.db.index")));
        if (conf.get("redis.server") != null && conf.get("redis.node") != null) {
            String[] addressArr = ((String) (conf.get("redis.server"))).split(",");
            String node = (String) (conf.get("redis.node"));
            Set<String> sentinels = new HashSet<String>();
            for (String str : addressArr) {
                sentinels.add(str);
            }
            if (StringUtil.isEmpty(password)) {
                pool = new JedisSentinelPool(node, sentinels, config, timeOut);
            } else {
                pool = new JedisSentinelPool(node, sentinels, config, timeOut, password);
            }
        } else if (conf.get("redis.host") != null && conf.get("redis.port") != null) {
            String host = (String) conf.get("redis.host");
            int port = Integer.parseInt((String) conf.get("redis.port"));
            if (StringUtil.isEmpty(password)) {
                pool = new JedisPool(config, host, port, timeOut);
            } else {
                pool = new JedisPool(config, host, port, timeOut, password);
            }
        } else {
            logger.error("参数配置错误，如果使用哨兵连接池需要配置redis.server与redis.node");
            logger.error("如果使用普通连接池需要配置redis.host与redis.port");
        }
        logger.info("成功初始化redis连接池");
    }

    /**
     * 返回jredis对象
     *
     * @return jredis
     */
    public Jedis getRedisTemplate() {
        Jedis resource = (Jedis) pool.getResource();
        resource.select(dbIndex);
        return resource;
    }

    /**
     * 赋值
     *
     * @param key   键
     * @param value 值
     */
    public void setValue(byte[] key, byte[] value) {
        Jedis jedis = null;
        try {
            jedis = getRedisTemplate();
            jedis.set(key, value);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
    }

    /**
     * 将多条查询合并成一条
     *
     * @param keys
     * @return
     */
    public Map<String, String> getValuePipeline(List<String> keys) {
        Jedis jedis = null;
        HashMap<String, String> result = new HashMap<>(16);
        try {
            Map<String, Response<String>> responses = new HashMap<>(keys.size());
            jedis = getRedisTemplate();
            Pipeline p = jedis.pipelined();
            for (String key : keys) {
                responses.put(key, p.get(key));
            }
            p.sync();
            for (String k : responses.keySet()) {
                result.put(k, responses.get(k).get());
            }
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
        return result;
    }

    /**
     * 将多条赋值合并成一条
     *
     * @param values
     */
    public void setValuePipeline(Map<String, String> values) {
        Jedis jedis = null;
        try {
            jedis = getRedisTemplate();
            Pipeline p = jedis.pipelined();
            for (Map.Entry<String, String> entry : values.entrySet()) {
                p.set(entry.getKey(), entry.getValue());
            }
            p.sync();
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
    }

    /**
     * 将多条赋值合并成一条
     *
     * @param values
     */
    public void hsetValuePipeline(String key, Map<String, String> values) {
        Jedis jedis = null;
        try {
            jedis = getRedisTemplate();
            Pipeline p = jedis.pipelined();
            for (Map.Entry<String, String> entry : values.entrySet()) {
                p.hset(key, entry.getKey(), entry.getValue());
            }
            p.sync();
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
    }

    /**
     * 赋值
     *
     * @param key   键
     * @param value 值
     */
    public void setValue(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getRedisTemplate();
            jedis.set(key, value);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
    }

    /**
     * 保存list到redis
     *
     * @param key   键
     * @param value 字符串数组
     */
    public void addToSet(String key, String... value) {
        Jedis jedis = null;
        try {
            jedis = getRedisTemplate();
            jedis.sadd(key, value);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
    }

    /**
     * 返回redis中list
     *
     * @param key 键
     * @return list value
     */
    public Set<String> getList(String key) {
        Jedis jedis = null;
        Set<String> re = null;
        try {
            jedis = getRedisTemplate();
            re = jedis.smembers(key);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
        return re;
    }

    /**
     * 删除redis中对应key的数据
     *
     * @param key 键
     */
    public void delKey(String key) {
        Jedis jedis = null;
        try {
            jedis = getRedisTemplate();
            jedis.del(key);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
    }

    /**
     * 设置key，对应的value值，需要设置过期时间
     *
     * @param key    键
     * @param value  值
     * @param second 过期时间（秒）
     */
    public void setExpireValue(byte[] key, byte[] value, int second) {
        Jedis jedis = null;
        try {
            jedis = getRedisTemplate();
            jedis.set(key, value);
            jedis.expire(key, second);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
    }

    /**
     * 设置key，对应的value值，需要设置过期时间
     *
     * @param key    键
     * @param value  值
     * @param second 过期时间（秒）
     */
    public void setExpireValue(String key, String value, int second) {
        Jedis jedis = null;
        try {
            jedis = getRedisTemplate();
            jedis.set(key, value);
            jedis.expire(key, second);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
    }

    /**
     * 设置redis中map的指定field的value
     *
     * @param key   键
     * @param field 字段
     * @param value 值
     */
    public void setHashValue(String key, String field, String value) {
        Jedis jedis = null;
        try {
            jedis = getRedisTemplate();
            jedis.hset(key, field, value);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
    }

    /**
     * 设置redis中map的指定field的value
     *
     * @param key   键
     * @param field 字段
     * @param value 值
     */
    public void setHashValue(byte[] key, byte[] field, byte[] value) {
        Jedis jedis = null;
        try {
            jedis = getRedisTemplate();
            jedis.hset(key, field, value);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
    }

    /**
     * 将redis中map的value加1，由redis保证原子性
     *
     * @param key   键
     * @param field 字段
     */
    public void incrHashValue(String key, String field) {
        Jedis jedis = null;
        try {
            jedis = getRedisTemplate();
            jedis.hincrBy(key, field, 1);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
    }

    /**
     * 将redis中map的value加上指定值，由redis保证原子性
     *
     * @param key   键
     * @param field 字段
     * @param value 值
     */
    public void incrHashValue(String key, String field, long value) {
        Jedis jedis = null;
        try {
            jedis = getRedisTemplate();
            jedis.hincrBy(key, field, value);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
    }

    /**
     * 将redis中map的value加上指定值，由redis保证原子性
     *
     * @param key   键
     * @param field 字段
     * @param value 值
     */
    public void incrHashValue(String key, String field, double value) {
        Jedis jedis = null;
        try {
            jedis = getRedisTemplate();
            jedis.hincrByFloat(key, field, value);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
    }

    /**
     * 删除redis中map的指定field的value
     *
     * @param key   键
     * @param field map中的字段
     */
    public void delHashValue(String key, String field) {
        Jedis jedis = null;
        try {
            jedis = getRedisTemplate();
            jedis.hdel(key, field);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
    }

    /**
     * 返回key对应的value
     *
     * @param key 键
     * @return 值
     */
    public byte[] getValue(byte[] key) {
        Jedis jedis = null;
        byte[] re = null;
        try {
            jedis = getRedisTemplate();
            re = jedis.get(key);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
        return re;
    }

    /**
     * 返回key对应的value
     *
     * @param key 键
     * @return 值
     */
    public String getValue(String key) {
        Jedis jedis = null;
        String re = null;
        try {
            jedis = getRedisTemplate();
            re = jedis.get(key);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
        return re;
    }

    /**
     * 查询指定坐标，半径内的点
     *
     * @param key
     * @param longitude
     * @param latitude
     * @param radius
     * @param unit
     * @param param
     * @return
     */
    public List<GeoRadiusResponse> getGeoRadius(String key, double longitude, double latitude, double radius, GeoUnit unit, GeoRadiusParam param) {
        Jedis jedis = null;
        List<GeoRadiusResponse> members = null;
        try {
            jedis = getRedisTemplate();
            members = jedis.georadius(key, longitude, latitude, radius, unit, param);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
        return members;
    }

    /**
     * 返回map中指定的field的值
     *
     * @param key   键
     * @param field 字段
     * @return 值
     */
    public String getHashValue(String key, String field) {
        Jedis jedis = null;
        String re = null;
        try {
            jedis = getRedisTemplate();
            re = jedis.hget(key, field);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
        return re;
    }

    /**
     * 返回map中指定的field的值
     *
     * @param key   键
     * @param field 字段
     * @return 值
     */
    public byte[] getHashValue(byte[] key, byte[] field) {
        Jedis jedis = null;
        byte[] re = null;
        try {
            jedis = getRedisTemplate();
            re = jedis.hget(key, field);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
        return re;
    }

    /**
     * 获取key对应的数据list
     *
     * @param key   key
     * @param start 开始下标
     * @param end   结束下标
     * @return List<byte[]>
     * @author hanzhu
     */
    public List<byte[]> getValueList(byte[] key, int start, int end) {
        Jedis jedis = null;
        List<byte[]> list = null;
        try {
            jedis = getRedisTemplate();
            list = jedis.lrange(key, start, end);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
        return list;
    }

    /**
     * value类型为list
     *
     * @param key   key
     * @param value value
     * @author hanzhu
     */
    public void setValueListObject(byte[] key, byte[] value) {

        Jedis jedis = null;
        try {
            jedis = getRedisTemplate();
            jedis.lpush(key, value);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
    }

    /**
     * 删除value(Map)中指定的字段
     *
     * @param key    键
     * @param fields map中要删除的字段
     */
    public void delMKey(String key, String... fields) {
        Jedis jedis = null;
        try {
            jedis = getRedisTemplate();
            jedis.hdel(key, fields);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
    }

    /**
     * 返回redis中map结构
     *
     * @param key 键
     * @return value为map结构
     */
    public Map<String, String> getAllKeys(String key) {
        Jedis jedis = null;
        Map<String, String> re = null;
        try {
            jedis = getRedisTemplate();
            re = jedis.hgetAll(key);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
        return re;
    }

    /**
     * 返回map结构中指定的field对应的值
     *
     * @param key    键
     * @param fields 字段
     * @return list<value>
     */
    public List<String> getMapValue(String key, String... fields) {
        Jedis jedis = null;
        List<String> re = null;
        try {
            jedis = getRedisTemplate();
            re = jedis.hmget(key, fields);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
        return re;
    }

    /**
     * 保存map结构到redis
     *
     * @param key 键
     * @param map 要保存的map
     */
    public void setMapValue(String key, Map<String, String> map) {
        Jedis jedis = null;
        try {
            jedis = getRedisTemplate();
            jedis.hmset(key, map);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
    }

    /**
     * 正常连接池回收
     *
     * @param jedis jedis对象
     */
    public void closeJedis(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    // Jia Add-Start.

    /**
     * 异常连接池回收
     *
     * @param jedis jedis对象
     */
    public void closeBreakJedis(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    /**
     * 获取所有key，支持模糊查询
     *
     * @param keyName key
     * @return 所有匹配的keys
     */
    public Set<String> keys(String keyName) {
        Jedis jedis = null;
        Set<String> keyMatched = new HashSet<String>();
        try {
            jedis = getRedisTemplate();
            keyMatched = jedis.keys(keyName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeBreakJedis(jedis);
        }
        return keyMatched;
    }

    // Jia Add-End.

    /**
     * mget 返回 多个key
     *
     * @param keysPattern key,
     *                    每个key之间用空格间隔
     * @return 多个key对应value
     */
    public List<String> mget(String keysPattern) {
        Jedis jedis = null;
        List<String> values = new ArrayList<String>();
        try {
            jedis = getRedisTemplate();
            values.addAll(jedis.mget(keysPattern));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeBreakJedis(jedis);
        }
        return values;
    }
}
