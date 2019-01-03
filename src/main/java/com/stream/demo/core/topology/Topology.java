package com.stream.demo.core.topology;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.generated.TopologyAssignException;
import backtype.storm.topology.TopologyBuilder;
import com.alibaba.jstorm.client.ConfigExtension;
import com.alibaba.jstorm.cluster.StormConfig;
import com.alibaba.jstorm.utils.JStormUtils;
import com.alibaba.jstorm.utils.LoadConf;
import com.stream.demo.core.bolt.FirstBolt;
import com.stream.demo.core.bolt.SecondBolt;
import com.stream.demo.core.spout.KafkaSpout;

import java.util.HashMap;
import java.util.Map;

/**
 * Topology
 */
public class Topology {
    /**
     * spout
     */
    public static final String TOPOLOGY_SPOUT_PARALLELISM_HINT = "topology.spout.parallel";

    /**
     * 统计
     */
    public static final String COUNT_BOLT_PARALLELISM_HINT = "topology.count.bolt.parallel";

    /**
     * conf
     */
    private static Map conf = new HashMap();

    /**
     * @return TopologyBuilder
     */
    public static TopologyBuilder setBuilder() {

        int spoutParallelismHint = JStormUtils.parseInt(
                conf.get(TOPOLOGY_SPOUT_PARALLELISM_HINT), 1);
        int countBoltParallelismHint = JStormUtils.parseInt(
                conf.get(COUNT_BOLT_PARALLELISM_HINT), 2);


        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout(TopologyDef.KAFKA_SPOUT_NAME, new KafkaSpout(), spoutParallelismHint);

        /*builder.setBolt(TopologyDef.COUNT_BOLT_NAME,
                new CountBolt(), countBoltParallelismHint)
                .fieldsGrouping(TopologyDef.KAFKA_SPOUT_NAME, new Fields(TopologyDef.TERMINAL_ID));*/


        builder.setBolt(TopologyDef.COUNT_BOLT_FIRST_NAME, new FirstBolt(), countBoltParallelismHint).
                shuffleGrouping(TopologyDef.KAFKA_SPOUT_NAME, TopologyDef.KAFKA_SPOUT_FIRST_NAME);

        builder.setBolt(TopologyDef.COUNT_BOLT_SECOND_NAME, new SecondBolt(), countBoltParallelismHint).
                shuffleGrouping(TopologyDef.KAFKA_SPOUT_NAME, TopologyDef.KAFKA_SPOUT_SECOND_NAME);

        int workerNum = JStormUtils.parseInt(conf.get(Config.TOPOLOGY_WORKERS), 1);
        conf.put(Config.TOPOLOGY_WORKERS, workerNum);
        return builder;
    }

    /**
     * @throws Exception 异常
     */
    public static void setLocalTopology() throws Exception {
        TopologyBuilder builder = setBuilder();
        String topologyName = (String) conf.get(Config.TOPOLOGY_NAME);
        if (topologyName == null) {
            topologyName = "mileage";
        }
        LocalCluster cluster = new LocalCluster();
        ConfigExtension.setUserDefinedLogbackConf(conf, "logback.xml");
        cluster.submitTopology(topologyName, conf, builder.createTopology());

    }

    /**
     * @throws AlreadyAliveException    异常
     * @throws InvalidTopologyException 异常
     * @throws TopologyAssignException  异常
     */
    public static void setRemoteTopology() throws AlreadyAliveException,
            InvalidTopologyException, TopologyAssignException {
        TopologyBuilder builder = setBuilder();
        String topologyName = (String) conf.get(Config.TOPOLOGY_NAME);
        if (topologyName == null) {
            topologyName = "mileage";
        }
        ConfigExtension.setUserDefinedLogbackConf(conf, "logback.xml");
        StormSubmitter.submitTopology(topologyName, conf, builder.createTopology());
    }

    /**
     * @param arg String
     */
    public static void loadConf(String arg) {
        if (arg.endsWith("yaml")) {
            conf.putAll(LoadConf.LoadYaml(arg));
        } else {
            conf.putAll(LoadConf.LoadProperty(arg));
        }
    }

    /**
     * @param args String[]
     * @throws Exception 异常
     */
    public static void main(String[] args) throws Exception {

        //加载配置文件
        loadConf("config.yaml");
        loadConf("redis.properties");
        loadConf("kafka.properties");

        boolean isLocal = StormConfig.local_mode(conf);
        if (isLocal) {
            setLocalTopology();
        } else {
            setRemoteTopology();
        }
    }

}
