package com.goinhn.networkriskcontrol.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class MCM implements Serializable {

    private double mcm0101;//带宽利用率

    private double mcm0102;//带宽利用率

    private double mcm0202;//带宽利用率

    private double mcm0203;//带宽利用率

    public double get(String name) {
        switch (name) {
            case "MCM-01-01":
                return this.getMcm0101();
            case "MCM-01-02":
                return this.getMcm0102();
            case "MCM-02-01":
                return this.getMcm0202();
            case "MCM-02-02":
                return this.getMcm0203();
            default:
                return 0;
        }
    }
}
