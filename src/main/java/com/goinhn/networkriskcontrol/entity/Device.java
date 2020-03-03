package com.goinhn.networkriskcontrol.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Device implements Serializable {

    private int id;//暂时没用到

    private String name;//设备的名字

    private String type;//设备的类型

    private List<Countermeasure> cms;//设备上可实施的控制策略，控制措施的集合

    private String ip;//在设备类中需要添加网络拓扑信息的属性，比如IP等，设备的Ip地址

    private String services; //有关的服务

}
