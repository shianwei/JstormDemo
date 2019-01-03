
package com.stream.demo.core.kafka;

import java.io.Serializable;

/**
 * 马驰
 * kafka
 */
public class KafkaCommand implements Serializable {
    /**
     * key String
     */
    private String key;

    /**
     * topic String
     */
    private String topic;

    /**
     * message byte[]
     */
    private byte[] message;

    /**
     * serialNumber String
     */
    private long serialNumber;

    /**
     * sendTime String
     */
    private long sendTime;

    /**
     * commandId String
     */
    private String commandId;

    /**
     * 构造函数
     */
    public KafkaCommand() {
    }


    /**
     * 获取 key String
     *
     * @return String key
     */
    public String getKey() {
        return this.key;
    }

    /**
     * 设置 key String
     *
     * @param key String
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 获取 topic String
     *
     * @return String topic
     */
    public String getTopic() {
        return this.topic;
    }

    /**
     * 设置 topic String
     *
     * @param topic String
     */
    public void setTopic(String topic) {
        this.topic = topic;
    }

    /**
     * 获取 message byte[]
     *
     * @return byte[] key
     */
    public byte[] getMessage() {
        return message.clone();
    }

    /**
     * 设置 message byte[]
     *
     * @param message byte[]
     */
    public void setMessage(byte[] message) {
        this.message = message.clone();
    }

    /**
     * 获取 serialNumber long
     *
     * @return long serialNumber
     */
    public long getSerialNumber() {
        return this.serialNumber;
    }

    /**
     * 设置 serialNumber long
     *
     * @param serialNumber long
     */
    public void setSerialNumber(long serialNumber) {
        this.serialNumber = serialNumber;
    }

    /**
     * 获取 sendTime long
     *
     * @return long sendTime
     */
    public long getSendTime() {
        return this.sendTime;
    }

    /**
     * 设置 sendTime long
     *
     * @param sendTime long
     */
    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * 获取 commandId String
     *
     * @return String key
     */
    public String getCommandId() {
        return this.commandId;
    }

    /**
     * 设置 commandId String
     *
     * @param commandId String
     */
    public void setCommandId(String commandId) {
        this.commandId = commandId;
    }
}

