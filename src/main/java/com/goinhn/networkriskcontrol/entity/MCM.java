package com.goinhn.networkriskcontrol.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class MCM implements Serializable {

    private double mcm0101;//带宽利用率

    private double mcm0102;//带宽利用率

    private double mcm0202;//带宽利用率

    private double mcm0203;//带宽利用率
}
