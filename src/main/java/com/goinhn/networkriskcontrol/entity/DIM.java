package com.goinhn.networkriskcontrol.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class DIM implements Serializable {

    private double dim0101;//带宽利用率

    private double dim0201;//带宽利用率

    private double dim0301;//带宽利用率

    public double get(String name) {
        switch (name) {
            case "DIM-01-01":
                return this.getDim0101();
            case "DIM-02-01":
                return this.getDim0201();
            case "DIM-03-01":
                return this.getDim0301();
            default:
                return 0;
        }
    }
}
