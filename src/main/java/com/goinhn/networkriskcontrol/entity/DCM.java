package com.goinhn.networkriskcontrol.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class DCM implements Serializable {

    private double dcm0201;//带宽利用率

    private double dcm0301;//带宽利用率

    private double dcm0401;//带宽利用率

    private double dcm0402;//带宽利用率
}
