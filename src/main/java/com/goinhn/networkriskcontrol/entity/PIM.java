package com.goinhn.networkriskcontrol.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class PIM implements Serializable {

    private double pim0101;//带宽利用率

    private double pim0102;//带宽利用率

    public double get(String name) {
        switch (name) {
            case "PIM-01-01":
                return this.getPim0101();
            case "PIM-01-02":
                return this.getPim0102();
            default:
                return 0;
        }
    }
}
