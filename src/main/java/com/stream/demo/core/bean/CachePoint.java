package com.stream.demo.core.bean;

import java.io.Serializable;

/**
 * @author Administrator
 * @date 2017-04-24
 * @modify
 */

public class CachePoint implements Serializable {

    /**
     * GPS时间
     */
    private long gpsDate;
    /**
     *
     */
    private long startDate;
    /**
     * 里程有效时间
     */
    private long gpsMileageDate;
    /**
     * 里程有效时间
     */
    private long mileageDate;
    /**
     * 油耗有效时间
     */
    private long fuelDate;
    /**
     * 启动开始时间
     */
    private long workHoursStart;
    /**
     * 怠速开始时间
     */
    private long idleHoursStart;
    /**
     * 标准里程开始值
     */
    private double standarMilageStart;

    /**
     * 标准里程
     */
    private double standarMilage;

    /**
     * 标准里程结束值
     */
    private double standarMilageEnd;

    /**
     * GPS里程开始值
     */
    private double meterGpsStart;
    /**
     * GPS里程结束值
     */
    private double meterGpsEnd;
    /**
     * GPS里程
     */
    private double meterGps;
    /**
     * 油耗最小值
     */
    private double fuelStart;
    /**
     * 消耗
     */
    private double fuel;
    /**
     * 油耗最大值
     */
    private double fuelEnd;
    /**
     * 超速开始时间
     */
    private long overSpeedStart;
    /**
     * 超速时间
     */
    private int overSpeedTime;
    /**
     * 发动机1000-1500转开始时间
     */
    private long ofeStart;
    /**
     * 发动机1000-1500转时间
     */
    private int ofeTime;
    /**
     * 空挡滑行开始时间
     */
    private long slideNeutralStart;
    /**
     * 空挡滑行时间
     */
    private int slideNeutralTime;
    /**
     * 空挡滑行，1:空挡滑行 0:正常
     */
    private int slideNeutral;
    /**
     * 急加速，1:急加速 0:正常
     */
    private int rapidAcceleration;
    /**
     * 急减速，1:急减速 0:正常
     */
    private int rapidDeceleration;
    /**
     * 超速报警
     */
    private int speedingAlarm;
    /**
     * 疲劳驾驶
     */
    private int tiredAlarm;

    /**
     * 含怠速速度总和
     */
    private int speedWorkSum;
    /**
     * 含怠速速度总个数
     */
    private int speedWorkNum;

    /**
     * 不含怠速速度总和
     */
    private int speedNoIdleSum;
    /**
     * 不含怠速速度总个数
     */
    private int speedNoIdleNum;

    /**
     * 剩余油量
     */
    private double oilValue;

    /**
     * 经纬度
     */
    private int lat;
    /**
     * 经纬度
     */
    private int lng;

    /**
     * 标准里程当日分段总和
     */
    private double stdMileageSeg;

    /**
     * 标准油耗当日分段总和
     */
    private double stdFuelSeg;
    /**
     * 行驶油耗最小值
     */
    private double runFuelStart;
    /**
     * 行驶消耗
     */
    private double runFuel;
    /**
     * 行驶油耗最大值
     */
    private double runFuelEnd;
    /**
     * 行驶油耗有效时间
     */
    private long runFuelDate;
    /**
     * 行驶油耗当日分段总和
     */
    private double stdRunFuelSeg;
    /**
     * 怠速油耗最小值
     */
    private double idlingFuelStart;
    /**
     * 怠速消耗
     */
    private double idlingFuel;
    /**
     * 怠速油耗最大值
     */
    private double idlingFuelEnd;
    /**
     * 怠速油耗有效时间
     */
    private long idlingFuelDate;
    /**
     * 怠速油耗当日分段总和
     */
    private double stdIdlingFuelSeg;

    /**
     * 怠速油耗标记
     */
    private int idlingFuelFlag;

    /**
     * 行驶油耗标记
     */
    private int runFuelFlag;

    /**
     * 行驶消耗缓存油耗
     */
    private double runCacheFuel;

    /**
     * 怠速消耗缓存油耗
     */
    private double idlingCacheFuel;

    public long getGpsDate() {
        return gpsDate;
    }

    public void setGpsDate(long gpsDate) {
        this.gpsDate = gpsDate;
    }

    public long getGpsMileageDate() {
        return gpsMileageDate;
    }

    public void setGpsMileageDate(long gpsMileageDate) {
        this.gpsMileageDate = gpsMileageDate;
    }

    public long getMileageDate() {
        return mileageDate;
    }

    public void setMileageDate(long mileageDate) {
        this.mileageDate = mileageDate;
    }

    public long getFuelDate() {
        return fuelDate;
    }

    public void setFuelDate(long fuelDate) {
        this.fuelDate = fuelDate;
    }

    public long getWorkHoursStart() {
        return workHoursStart;
    }

    public void setWorkHoursStart(long workHoursStart) {
        this.workHoursStart = workHoursStart;
    }

    public long getIdleHoursStart() {
        return idleHoursStart;
    }

    public void setIdleHoursStart(long idleHoursStart) {
        this.idleHoursStart = idleHoursStart;
    }

    public double getStandarMilageStart() {
        return standarMilageStart;
    }

    public void setStandarMilageStart(double standarMilageStart) {
        this.standarMilageStart = standarMilageStart;
    }

    public double getStandarMilage() {
        return standarMilage;
    }

    public void setStandarMilage(double standarMilage) {
        this.standarMilage = standarMilage;
    }

    public double getStandarMilageEnd() {
        return standarMilageEnd;
    }

    public void setStandarMilageEnd(double standarMilageEnd) {
        this.standarMilageEnd = standarMilageEnd;
    }

    public double getMeterGpsStart() {
        return meterGpsStart;
    }

    public void setMeterGpsStart(double meterGpsStart) {
        this.meterGpsStart = meterGpsStart;
    }

    public double getMeterGpsEnd() {
        return meterGpsEnd;
    }

    public void setMeterGpsEnd(double meterGpsEnd) {
        this.meterGpsEnd = meterGpsEnd;
    }

    public double getMeterGps() {
        return meterGps;
    }

    public void setMeterGps(double meterGps) {
        this.meterGps = meterGps;
    }

    public double getFuelStart() {
        return fuelStart;
    }

    public void setFuelStart(double fuelStart) {
        this.fuelStart = fuelStart;
    }

    public double getFuel() {
        return fuel;
    }

    public void setFuel(double fuel) {
        this.fuel = fuel;
    }

    public double getFuelEnd() {
        return fuelEnd;
    }

    public void setFuelEnd(double fuelEnd) {
        this.fuelEnd = fuelEnd;
    }

    public long getOverSpeedStart() {
        return overSpeedStart;
    }

    public void setOverSpeedStart(long overSpeedStart) {
        this.overSpeedStart = overSpeedStart;
    }

    public int getOverSpeedTime() {
        return overSpeedTime;
    }

    public void setOverSpeedTime(int overSpeedTime) {
        this.overSpeedTime = overSpeedTime;
    }

    public long getOfeStart() {
        return ofeStart;
    }

    public void setOfeStart(long ofeStart) {
        this.ofeStart = ofeStart;
    }

    public int getOfeTime() {
        return ofeTime;
    }

    public void setOfeTime(int ofeTime) {
        this.ofeTime = ofeTime;
    }

    public long getSlideNeutralStart() {
        return slideNeutralStart;
    }

    public void setSlideNeutralStart(long slideNeutralStart) {
        this.slideNeutralStart = slideNeutralStart;
    }

    public int getSlideNeutralTime() {
        return slideNeutralTime;
    }

    public void setSlideNeutralTime(int slideNeutralTime) {
        this.slideNeutralTime = slideNeutralTime;
    }

    public int getSlideNeutral() {
        return slideNeutral;
    }

    public void setSlideNeutral(int slideNeutral) {
        this.slideNeutral = slideNeutral;
    }

    public int getRapidAcceleration() {
        return rapidAcceleration;
    }

    public void setRapidAcceleration(int rapidAcceleration) {
        this.rapidAcceleration = rapidAcceleration;
    }

    public int getRapidDeceleration() {
        return rapidDeceleration;
    }

    public void setRapidDeceleration(int rapidDeceleration) {
        this.rapidDeceleration = rapidDeceleration;
    }

    public int getSpeedingAlarm() {
        return speedingAlarm;
    }

    public void setSpeedingAlarm(int speedingAlarm) {
        this.speedingAlarm = speedingAlarm;
    }

    public int getTiredAlarm() {
        return tiredAlarm;
    }

    public void setTiredAlarm(int tiredAlarm) {
        this.tiredAlarm = tiredAlarm;
    }

    public int getSpeedWorkSum() {
        return speedWorkSum;
    }

    public void setSpeedWorkSum(int speedWorkSum) {
        this.speedWorkSum = speedWorkSum;
    }

    public int getSpeedWorkNum() {
        return speedWorkNum;
    }

    public void setSpeedWorkNum(int speedWorkNum) {
        this.speedWorkNum = speedWorkNum;
    }

    public int getSpeedNoIdleSum() {
        return speedNoIdleSum;
    }

    public void setSpeedNoIdleSum(int speedNoIdleSum) {
        this.speedNoIdleSum = speedNoIdleSum;
    }

    public int getSpeedNoIdleNum() {
        return speedNoIdleNum;
    }

    public void setSpeedNoIdleNum(int speedNoIdleNum) {
        this.speedNoIdleNum = speedNoIdleNum;
    }

    public double getOilValue() {
        return oilValue;
    }

    public void setOilValue(double oilValue) {
        this.oilValue = oilValue;
    }

    public int getLat() {
        return lat;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }

    public int getLng() {
        return lng;
    }

    public void setLng(int lng) {
        this.lng = lng;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public double getStdMileageSeg() {
        return stdMileageSeg;
    }

    public void setStdMileageSeg(double stdMileageSeg) {
        this.stdMileageSeg = stdMileageSeg;
    }

    public double getStdFuelSeg() {
        return stdFuelSeg;
    }

    public void setStdFuelSeg(double stdFuelSeg) {
        this.stdFuelSeg = stdFuelSeg;
    }

    public double getRunFuelStart() {
        return runFuelStart;
    }

    public void setRunFuelStart(double runFuelStart) {
        this.runFuelStart = runFuelStart;
    }

    public double getRunFuel() {
        return runFuel;
    }

    public void setRunFuel(double runFuel) {
        this.runFuel = runFuel;
    }

    public double getRunFuelEnd() {
        return runFuelEnd;
    }

    public void setRunFuelEnd(double runFuelEnd) {
        this.runFuelEnd = runFuelEnd;
    }

    public long getRunFuelDate() {
        return runFuelDate;
    }

    public void setRunFuelDate(long runFuelDate) {
        this.runFuelDate = runFuelDate;
    }

    public double getIdlingFuelStart() {
        return idlingFuelStart;
    }

    public void setIdlingFuelStart(double idlingFuelStart) {
        this.idlingFuelStart = idlingFuelStart;
    }

    public double getIdlingFuel() {
        return idlingFuel;
    }

    public void setIdlingFuel(double idlingFuel) {
        this.idlingFuel = idlingFuel;
    }

    public double getIdlingFuelEnd() {
        return idlingFuelEnd;
    }

    public void setIdlingFuelEnd(double idlingFuelEnd) {
        this.idlingFuelEnd = idlingFuelEnd;
    }

    public long getIdlingFuelDate() {
        return idlingFuelDate;
    }

    public void setIdlingFuelDate(long idlingFuelDate) {
        this.idlingFuelDate = idlingFuelDate;
    }

    public double getStdRunFuelSeg() {
        return stdRunFuelSeg;
    }

    public void setStdRunFuelSeg(double stdRunFuelSeg) {
        this.stdRunFuelSeg = stdRunFuelSeg;
    }

    public double getStdIdlingFuelSeg() {
        return stdIdlingFuelSeg;
    }

    public void setStdIdlingFuelSeg(double stdIdlingFuelSeg) {
        this.stdIdlingFuelSeg = stdIdlingFuelSeg;
    }

    public int getIdlingFuelFlag() {
        return idlingFuelFlag;
    }

    public void setIdlingFuelFlag(int idlingFuelFlag) {
        this.idlingFuelFlag = idlingFuelFlag;
    }

    public int getRunFuelFlag() {
        return runFuelFlag;
    }

    public void setRunFuelFlag(int runFuelFlag) {
        this.runFuelFlag = runFuelFlag;
    }

    public double getRunCacheFuel() {
        return runCacheFuel;
    }

    public void setRunCacheFuel(double runCacheFuel) {
        this.runCacheFuel = runCacheFuel;
    }

    public double getIdlingCacheFuel() {
        return idlingCacheFuel;
    }

    public void setIdlingCacheFuel(double idlingCacheFuel) {
        this.idlingCacheFuel = idlingCacheFuel;
    }
}
