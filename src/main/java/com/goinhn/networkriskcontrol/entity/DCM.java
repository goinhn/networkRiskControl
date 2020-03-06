package com.goinhn.networkriskcontrol.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class DCM implements Serializable {

    private double dcm0201;//带宽利用率

    private double dcm0301;//带宽利用率

    private double dcm0401;//带宽利用率

    private double dcm0402;//带宽利用率

    public double get(String name) {
        switch (name) {
            case "DCM-02-01":
                return this.getDcm0201();
            case "DCM-03-01":
                return this.getDcm0301();
            case "DCM-04-01":
                return this.getDcm0401();
            case "DCM-04-02":
                return this.getDcm0402();
            default:
                return 0;
        }
    }
}
