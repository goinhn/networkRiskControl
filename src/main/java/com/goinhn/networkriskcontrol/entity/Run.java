package com.goinhn.networkriskcontrol.entity;


import lombok.Data;

import java.io.Serializable;

@Data
public class Run implements Serializable {

    private double bandwidth;//带宽利用率

    private double cpu;//cpu利用率

    private double memory;//内存利用率
}
