package com.goinhn.networkriskcontrol.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class DEM implements Serializable {

    private double dem0101;//带宽利用率

    private double dem0201;//带宽利用率

    private double dem0302;//带宽利用率

    public double get(String name) {
        switch (name) {
            case "DEM-01-01":
                return this.getDem0101();
            case "DEM-02-01":
                return this.getDem0201();
            case "DEM-03-02":
                return this.getDem0302();
            default:
                return 0;
        }
    }
}
