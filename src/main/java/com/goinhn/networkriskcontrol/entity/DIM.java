package com.goinhn.networkriskcontrol.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class DIM implements Serializable {

    private double dim0101;//带宽利用率

    private double dim0201;//带宽利用率

    private double dim0301;//带宽利用率
}
