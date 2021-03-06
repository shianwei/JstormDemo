package com.stream.demo.core.bolt;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;
import com.stream.demo.core.common.Redis;
import com.stream.demo.core.kafka.KafkaCommand;
import com.stream.demo.core.topology.TopologyDef;
import com.stream.demo.core.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.Map;


/**
 * Created by Lenovo on 2019/1/2.
 */
public class FirstBolt extends BaseBasicBolt {
    /**
     * 定义LOG输出对象
     */
    private static Logger logger = LoggerFactory.getLogger(FirstBolt.class);
    /**
     * 初期化加载Redis连接池
     *
     * @param map     配置文件
     * @param context 上下文
     */
    @Override
    public void prepare(Map map, TopologyContext context) {
        try {
            Redis.buildInstance(map);
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        String msg = tuple.getStringByField(TopologyDef.COUNT_BOLT_FIRST_NAME);
        KafkaCommand kafkaCommand = JsonUtil.fromJson(msg, KafkaCommand.class);
        Jedis jedis = Redis.getInstance().getRedisTemplate();
        jedis.hset("firstBolt",kafkaCommand.getKey(),msg);
        logger.info("msg3002:"+msg);
    }
    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
