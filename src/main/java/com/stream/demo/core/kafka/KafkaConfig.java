package com.stream.demo.core.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 * @author machi
 * @create 2018/7/23
 */

public class KafkaConfig implements Serializable {


    /**
     * groupId String
     */
    private String groupId;
    /**
     * consumerTopic String
     */
    private String consumerTopic;
    /**
     * produceTopic String
     */
    private String produceTopic;

    private transient Map<String, Object> consumerProps;

    private transient Map<String, Object> producerProps;

    /**
     * 初始化
     *
     * @param conf Map
     */
    public void init(Map conf) {
        groupId = String.valueOf(conf.get("group.id"));
        consumerTopic = String.valueOf(conf.get("consumer.topic"));
        produceTopic = String.valueOf(conf.get("producer.topic"));
        consumerProps = consumerProps(conf);
        producerProps = producerProps(conf);
    }

    /**
     * 消费者配置
     *
     * @param conf Map
     * @return Map<String       ,               Object> 配置
     */
    private Map<String, Object> consumerProps(Map conf) {
        Map<String, Object> props = new HashMap<>(16);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, conf.get("consumer.bootstrap.servers"));
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, conf.get("session.timeout.ms"));
        props.put(ConsumerConfig.GROUP_ID_CONFIG, conf.get("group.id"));
        props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, conf.get("heartbeat.interval.ms"));
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, conf.get("auto.offset.reset"));
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, conf.get("max.poll.records"));
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, conf.get("key.deserializer"));
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, conf.get("value.deserializer"));
        return props;
    }

    /**
     * 生产者配置
     *
     * @param conf Map
     * @return Properties 配置
     */
    private Map<String, Object> producerProps(Map conf) {
        Map<String, Object> props = new HashMap<>(16);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, conf.get("producer.bootstrap.servers"));
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, conf.get("key.serializer"));
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, conf.get("value.serializer"));
        props.put(ProducerConfig.ACKS_CONFIG, conf.get("acks"));
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, conf.get("batch.size"));
        props.put(ProducerConfig.LINGER_MS_CONFIG, conf.get("linger.ms"));
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, conf.get("buffer.memory"));
        return props;
    }

    public Map<String, Object> getConsumerProps() {
        return consumerProps;
    }

    public void setConsumerProps(Map<String, Object> consumerProps) {
        this.consumerProps = consumerProps;
    }

    /**
     * 获取 groupId String
     *
     * @return String groupId
     */
    public String getGroupId() {
        return this.groupId;
    }

    /**
     * 设置 groupId String
     *
     * @param groupId String
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * 获取 consumerTopic String
     *
     * @return String consumerTopic
     */
    public String getConsumerTopic() {
        return this.consumerTopic;
    }

    /**
     * 设置 consumerTopic String
     *
     * @param consumerTopic String
     */
    public void setConsumerTopic(String consumerTopic) {
        this.consumerTopic = consumerTopic;
    }

    /**
     * 获取 produceTopic String
     *
     * @return String produceTopic
     */
    public String getProduceTopic() {
        return this.produceTopic;
    }

    /**
     * 设置 produceTopic String
     *
     * @param produceTopic StringT
     */
    public void setProduceTopic(String produceTopic) {
        this.produceTopic = produceTopic;
    }

    public Map<String, Object> producerProps() {
        return producerProps;
    }

    public void setProducerProps(Map<String, Object> producerProps) {
        this.producerProps = producerProps;
    }
}
