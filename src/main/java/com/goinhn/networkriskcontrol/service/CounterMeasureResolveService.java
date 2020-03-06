package com.goinhn.networkriskcontrol.service;




import com.goinhn.networkriskcontrol.entity.Countermeasure;
import com.goinhn.networkriskcontrol.entity.Device;

import java.util.List;
import java.util.Map;

public interface CounterMeasureResolveService {//控制机制解析模块
    List<Device> resolveDevice(Map<String, Map<String, String>> map);//把设备信息的map转化为类
    List<Device> enumCountermeasures(List<Device> devices);//枚举所有控制机制
    List<Countermeasure[]> enumCoutermeasureCom();//枚举所有控制机制组合
    Map<String, Map<String, List<Map<String, String[]>>>> buildAllDeviceControlRestrictions(List<Device> devices);//通过单个控制机制生成查询语句的约束
    Map<String, List<Map<String, String[]>>> buildOneDeviceControlRestrictions(Device device);//通过单个控制机制生成查询语句的约束
    List<Map<String, String[]>> buildOneCountermeasureControlRestrictions(Countermeasure countermeasure);//通过单个控制机制生成查询语句的约束
    Map<String, String[]> bulidcomRestrictions(List<Countermeasure[]> cms);//通过组合控制机制生成查询语句的约束

}
