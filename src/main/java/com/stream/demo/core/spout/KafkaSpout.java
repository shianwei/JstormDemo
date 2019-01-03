package com.stream.demo.core.spout;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import com.stream.demo.core.kafka.KafkaCommand;
import com.stream.demo.core.kafka.KafkaConfig;
import com.stream.demo.core.topology.TopologyDef;
import com.stream.demo.core.utils.Constant;
import com.stream.demo.core.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.config.ContainerProperties;

import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 读取kafka数据
 */
public class KafkaSpout extends BaseRichSpout {
    /**
     * 定义LOG输出对象
     */
    private static Logger logger = LoggerFactory.getLogger(KafkaSpout.class);

    /**
     * 发送队列
     */
    private transient LinkedBlockingDeque<String> sendingQueue;

    private transient SpoutOutputCollector collector;
    /**
     * spring kafka 容器
     */
    private transient KafkaMessageListenerContainer<String, String> container;

    /**
     * kafka
     */
    public KafkaSpout() {
    }

    /**
     * open
     *
     * @param conf      Map
     * @param context   TopologyContext
     * @param collector SpoutOutputCollector
     */
    @Override
    public void open(Map conf, TopologyContext context, final SpoutOutputCollector collector) {
        try {
            this.collector = collector;
            int depth = Math.toIntExact((long) conf.get("spout.queue.depth"));
            this.sendingQueue = new LinkedBlockingDeque<>(depth);
            KafkaConfig kafkaConfig = new KafkaConfig();
            kafkaConfig.init(conf);
            Map<String, Object> props = kafkaConfig.getConsumerProps();
            DefaultKafkaConsumerFactory<String, String> cf = new DefaultKafkaConsumerFactory<>(props);
            ContainerProperties containerProps = new ContainerProperties(kafkaConfig.getConsumerTopic());
            containerProps.setMessageListener((MessageListener<String, String>) message -> {
                try {
                    logger.info("数据开始消费");
                    sendingQueue.put(message.value());
                } catch (Exception e1) {
                    logger.error("", e1);
                }
            });
            containerProps.setAckMode(AbstractMessageListenerContainer.AckMode.TIME);
            containerProps.setAckTime(Long.parseLong(String.valueOf(conf.get("acktime"))));
            container = new KafkaMessageListenerContainer<>(cf, containerProps);
            container.start();
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    /**
     * next
     */
    @Override
    public void nextTuple() {
        try {
            String msg = sendingQueue.take();
            KafkaCommand kafkaCommand = JsonUtil.fromJson(msg, KafkaCommand.class);
            logger.info("数据开始消费" + kafkaCommand.getCommandId());
            if (kafkaCommand == null) {
                return;
            }
            if (Constant.COMMAND_ID.equals(kafkaCommand.getKey())){
                collector.emit(TopologyDef.KAFKA_SPOUT_FIRST_NAME, new Values(JsonUtil.toJson(kafkaCommand)));
            }else if (Constant.COMMAND_ID_0F38.equals(kafkaCommand.getKey())){
                collector.emit(TopologyDef.KAFKA_SPOUT_SECOND_NAME, new Values(JsonUtil.toJson(kafkaCommand)));
            }
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    /**
     * 激活
     */
    @Override
    public void activate() {
        if (container != null && !container.isRunning()) {
            container.start();
        }
    }

    /**
     * kill topology 时停止消费kafka
     */
    @Override
    public void deactivate() {
        if (container != null && container.isRunning()) {
            container.stop();
        }
    }

    /**
     * 输出
     *
     * @param outputFieldsDeclarer Bolt输出模式
     */
    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        //outputFieldsDeclarer.declare(new Fields(TopologyDef.KAFKA_SPOUT_OUTPUT_FIELD, TopologyDef.TERMINAL_ID));
        outputFieldsDeclarer.declareStream(TopologyDef.KAFKA_SPOUT_FIRST_NAME, new Fields(TopologyDef.COUNT_BOLT_FIRST_NAME));
        outputFieldsDeclarer.declareStream(TopologyDef.KAFKA_SPOUT_SECOND_NAME, new Fields(TopologyDef.COUNT_BOLT_SECOND_NAME));
    }
}
