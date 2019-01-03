package com.stream.demo.core.topology;

/**
 * 定义spout,bolt name常量类
 */
public class TopologyDef {
    /**
     * kafka spout 名称
     */
    public static final String KAFKA_SPOUT_NAME = "kafka_spout";

    /**
     * kafka spout 名称
     */
    public static final String KAFKA_SPOUT_FIRST_NAME = "kafka_first_spout";

    /**
     * kafka spout 名称
     */
    public static final String KAFKA_SPOUT_SECOND_NAME = "kafka_second_spout";

    /**
     * 统计bolt名称
     */
    public static final String COUNT_BOLT_NAME = "count";

    /**
     * 统计bolt名称
     */
    public static final String COUNT_BOLT_SECOND_NAME = "second_bolt";

    /**
     * 统计bolt名称
     */
    public static final String COUNT_BOLT_FIRST_NAME = "first_bolt";

    /**
     * 终端ID
     */
    public static final String TERMINAL_ID = "terminalId";

    /**
     * kafka spout filed name
     */
    public static final String KAFKA_SPOUT_OUTPUT_FIELD = "KafkaSpout_output";

    /**
     * bolt output field name
     */
    public static final String COUNT_BOLT_OUTPUT_FIELD = "count_bolt_output";


}
