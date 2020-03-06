package com.goinhn.networkriskcontrol.entity;

import com.goinhn.networkriskcontrol.util.NetworkIP;
import lombok.Data;

import java.io.Serializable;
import java.util.*;

@Data
public class Countermeasure implements Serializable {//分为几类，用继承的思想

    private int id;         //编号

    private String name;    //控制措施的名称

    private Device device;  //表示该措施的实施设备

    private Map<String, List<String>> countermeasureEnum;  //枚举处所有控制措施集合


    /**
     * @param * @param  无参数
     * @return java.util.Map<java.lang.String, java.util.List < java.lang.String>>
     * @description 根据类型对该countermeasure所有可能的值进行枚举
     * @author wangxiao
     * @date 2019/3/25 19:38
     */
    public Map<String, List<String>> enumByType() {

        switch (this.name.split("-")[0]) {
            case "block":
                enumBlock("block");
                break;
            case "blockin":
                enumBlock("blockin");
                break;
            case "blockout":
                enumBlock("blockout");
                break;
            case "restart":
                enumRestartOrShutdown();
                break;
            case "shutdown":
                enumRestartOrShutdown();
                break;
        }
        return this.countermeasureEnum;
    }

    private void enumBlock(String blockStr) {//枚举block类型的countermeasure
        List<String> ipList;
        if (this.name.split("-")[1].equals("all")) {//此处为预留接口，all表示没有用户约束，直接枚举全部的
            EnumSet<NetworkIP> networkIPS = EnumSet.allOf(NetworkIP.class);//得到枚举类中所有成员的集合
            ipList = new ArrayList<>();
            for (NetworkIP device :
                    networkIPS) {
                ipList.add(device.getIp());
            }
        } else {
            ipList = new ArrayList<>(Arrays.asList(this.name.split("-")[1].split("&")));
        }
        this.countermeasureEnum.put(blockStr, ipList);
    }

    private void enumRestartOrShutdown() {//shutdown和restart的处理流程基本相同，因此直接采用相同的枚举方法
        List<String> serviceList;
        if (this.name.split("-")[1].equals("all")) {//此处为预留接口，all表示没有用户约束，直接枚举全部的
            serviceList = new ArrayList<>(Arrays.asList(this.device.getServices().split(",")));
            serviceList.add("host");
        } else {
            serviceList = new ArrayList<>(Arrays.asList(this.name.split("-")[1].split("&")));
        }
        this.countermeasureEnum.put(this.name.split("-")[0], serviceList);
    }

}
