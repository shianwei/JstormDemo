package com.stream.demo.core.common;

/**
 * redis key前缀常量类,命名规则为redis_vk_业务相关_prefix
 * redis_vk_imei_prefix
 *
 * @author Administrator
 * @modify
 */

public class KeyPrefix {
    /**
     * key头
     */
    public static final String REDIS_VK_PREFIX = "STREAM";


    /**
     * 统计结果缓存redis业务编码
     */
    public static final String COUNT = "COUNT";

    /**
     * 上一个点数据缓存redis业务编码
     */
    public static final String PRE = "PRE";

    /**
     * 统计结果缓存redis业务编码
     */
    public static final String DRAVEL_COUNT = "DRAVEL_COUNT";

    /**
     * 上一个点数据缓存redis业务编码
     */
    public static final String DRAVEL_PRE = "DRAVEL_PRE";

    /**
     * 统计结果缓存redis业务编码
     */
    public static final String MILEAGE_COUNT = "MILEAGE_COUNT";

    /**
     * 上一个点数据缓存redis业务编码
     */
    public static final String MILEAGE_PRE = "MILEAGE_PRE";

    /**
     * 平台所有终端id
     */
    public static final String STREAM_TERMINAL_ID = "STREAM_TERMINAL_ID";

    /**
     * 车辆信息
     */
    public static final String CAR_SYNC = "car_sync";

    /**
     * 车辆进入区域时间
     */
    public static final String STREAM_RETENTION_STATUS = "STREAM_RETENTION_STATUS";

    /**
     * 车辆滞留超时统计结果
     */
    public static final String STREAM_RETENTION_RESULT = "STREAM_RETENTION_RESULT";

    /**
     * 服务站位置信息
     */
    public static final String HY_SERVICE_STATION_GEO = "hy_service_station_geo";
    /**
     * 服务站服务半径
     */
    public static final String HY_SERVICE_STATION_RADIUS = "hy_service_station_radius";


    /**
     * 构建key
     *
     * @param groupKey 组
     * @param key      key
     * @return String 返回key
     */
    public static String buildKey(String groupKey, String key) {
        return REDIS_VK_PREFIX + "_" + groupKey + "_" + key;
    }

    /**
     * 构建key
     *
     * @param groupKey 组
     * @param key      key
     * @return String 返回key
     */
    public static String buildKey(String groupKey, Long key) {
        return REDIS_VK_PREFIX + "_" + groupKey + "_" + String.valueOf(key);
    }

}
