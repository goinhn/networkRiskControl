package com.goinhn.networkriskcontrol.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class DEM implements Serializable {

    private double dem0101;//带宽利用率

    private double dem0201;//带宽利用率

    private double dem0302;//带宽利用率
}
