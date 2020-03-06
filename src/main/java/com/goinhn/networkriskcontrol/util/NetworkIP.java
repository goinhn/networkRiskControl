package com.goinhn.networkriskcontrol.util;


public enum NetworkIP {
    DEVICE_1_IP("59.166.0.1"),
    DEVICE_2_IP("59.166.0.2"),
    DEVICE_3_IP("59.166.0.3"),
    DEVICE_4_IP("59.166.0.4"),
    DEVICE_5_IP("59.166.0.5"),
    DEVICE_6_IP("59.166.0.6"),
    DEVICE_7_IP("59.166.0.7"),
    DEVICE_8_IP("59.166.0.8"),
    DEVICE_9_IP("59.166.0.9"),
    DEVICE_10_IP("174.45.176.0"),
    DEVICE_11_IP("174.45.176.2"),
    DEVICE_12_IP("174.45.176.3"),
    DEVICE_13_IP("174.45.176.4"),
    DEVICE_14_IP("149.171.126.2"),
    DEVICE_15_IP("149.171.126.3"),
    DEVICE_16_IP("149.171.126.4"),
    DEVICE_17_IP("149.171.126.5"),
    DEVICE_18_IP("149.171.126.6"),
    DEVICE_19_IP("149.171.126.7"),
    DEVICE_20_IP("149.171.126.8"),
    DEVICE_21_IP("149.171.126.9");

    private final String ip;

    NetworkIP(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }
}
