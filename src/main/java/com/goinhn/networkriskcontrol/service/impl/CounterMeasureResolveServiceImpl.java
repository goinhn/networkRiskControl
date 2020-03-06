package com.goinhn.networkriskcontrol.service.impl;

import com.goinhn.networkriskcontrol.entity.Countermeasure;
import com.goinhn.networkriskcontrol.entity.Device;
import com.goinhn.networkriskcontrol.service.CounterMeasureResolveService;
import com.goinhn.networkriskcontrol.util.StrategyUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CounterMeasureResolveServiceImpl implements CounterMeasureResolveService {

    /**
     * @param * @param map json解析出的map格式的Device的信息  Map： String:设备的信息
     * @return java.util.List<com.iiecas.riskdemo.entity.Device>
     * @description 解析输入的设备信息
     * @author wangxiao
     * @date 2019/3/25 14:36
     */
    @Override
    public List<Device> resolveDevice(Map<String, Map<String, String>> map) {
        //设备集合，作为返回 Device的信息
        List<Device> devices = new ArrayList<>();
        //遍历设备信息
        for (Map.Entry<String, Map<String, String>> entryDevice : map.entrySet()) {
            //设置设备集合中每个设备的属性
            Device device = new Device();

            device.setName(entryDevice.getKey());
            Map<String, String> entryDeviceInfo = entryDevice.getValue();
            device.setType(entryDeviceInfo.get("type"));
            device.setIp(entryDeviceInfo.get("ip"));
            device.setServices(entryDeviceInfo.get("services"));
            device.setCms(StrategyUtil.generateCountermeasureListByName(entryDeviceInfo.get("countermeasures"), device));
            devices.add(device);
        }
        return devices;
    }

    /**
     * @param * @param devices 拥有初始countermeasure的设备集合
     * @return java.util.List<com.goinhn.riskdemo.entity.Device>
     * @description
     * @author wangxiao
     * @date 2019/3/25 16:29
     */
    @Override
    public List<Device> enumCountermeasures(List<Device> devices) {
        List<Device> devicesWithEnumCountermeasures = new ArrayList<>();
        for (Device device : devices) {//对每个设备分别操作
            List<Countermeasure> countermeasures = new ArrayList<>();
            for (Countermeasure countermeasure : device.getCms()) {//每个设备的每个countermeasure分别操作
                //使用countermeasure的枚举方法，set所有的解决措施
                countermeasure.setCountermeasureEnum(countermeasure.enumByType());
//                添加所有设备的所有解决措施
                countermeasures.add(countermeasure);
            }
//            设置设备的默认解决措施
            device.setCms(countermeasures);

//           返回所有的添加过措施设备的集合
            devicesWithEnumCountermeasures.add(device);
        }
        //返回的countermeasure已经枚举过后的device集合
        return devicesWithEnumCountermeasures;
    }

    /**
     * 所有措施的集合
     *
     * @return
     */
    @Override
    public List<Countermeasure[]> enumCoutermeasureCom() {
        return null;
    }

    @Override
    public Map<String, Map<String, List<Map<String, String[]>>>> buildAllDeviceControlRestrictions(List<Device> devices) {
        Map<String, Map<String, List<Map<String, String[]>>>> countermeasureRestrictions = new HashMap<>();
        for (Device device : devices) {
            countermeasureRestrictions.put(device.getName(), buildOneDeviceControlRestrictions(device));
        }
        return countermeasureRestrictions;
    }

    /**
     * @param * @param cms 某个设备的或者所有设备的countermeasure的集合
     * @return java.util.Map<java.lang.String, java.lang.String [ ]>
     * @description 生成能用于生成SQL语句的约束，即返回值可以
     * 当做BasicDataResolveService.generateSQLQuery方法的controlRestriction参数
     * @author wangxiao
     * @date 2019/3/26 11:05
     */
    @Override
    public Map<String, List<Map<String, String[]>>> buildOneDeviceControlRestrictions(Device device) {

        //得到设备中的所有的countermeasure
        List<Countermeasure> cms = device.getCms();

        Map<String, List<Map<String, String[]>>> countermeasureRestrictions = new HashMap<>();
        for (Countermeasure countermeasure : cms) {
            countermeasureRestrictions.put(countermeasure.getName(), buildOneCountermeasureControlRestrictions(countermeasure));
        }
        return countermeasureRestrictions;
    }

    /**
     * 返回所有最优策略的集合
     *
     * @param countermeasure
     * @return
     */
    @Override
    public List<Map<String, String[]>> buildOneCountermeasureControlRestrictions(Countermeasure countermeasure) {

        List<Map<String, String[]>> countermeasureRestrictions = new ArrayList<>();
        Map<String, List<String>> countermeasureEnum = countermeasure.getCountermeasureEnum();
        String countermeasureType = countermeasureEnum.keySet().iterator().next();
        List<String> cmVauleEnum = countermeasureEnum.get(countermeasureType);

        switch (countermeasureType) {
            case "block":
                for (String cmValue : cmVauleEnum) {
                    //System.out.println("CMValue:" + cmValue);
                    Map<String, String[]> countermeasureRestriction = new HashMap<>();
                    countermeasureRestriction.put("srcip", new String[]{cmValue, "<>"});
                    countermeasureRestriction.put("dstip", new String[]{cmValue, "<>"});
                    countermeasureRestrictions.add(countermeasureRestriction);
                }
                break;
            case "blockin":
                for (String cmValue : cmVauleEnum) {
                    Map<String, String[]> countermeasureRestriction = new HashMap<>();
                    countermeasureRestriction.put("dstip", new String[]{cmValue, "<>"});
                    countermeasureRestrictions.add(countermeasureRestriction);
                }
                break;
            case "blockout":
                for (String cmValue : cmVauleEnum) {
                    Map<String, String[]> countermeasureRestriction = new HashMap<>();
                    countermeasureRestriction.put("srcip", new String[]{cmValue, "<>"});
                    countermeasureRestrictions.add(countermeasureRestriction);
                }
                break;
            case "restart":
                //此处需要讨论
                break;
            case "shutdown":
                for (String cmValue : cmVauleEnum) {

                    Map<String, String[]> countermeasureRestriction = new HashMap<>();
                    if (cmValue.equals("host")) {
                        String deviceIp = countermeasure.getDevice().getIp();
                        countermeasureRestriction.put("srcip", new String[]{deviceIp, "<>"});
                        countermeasureRestriction.put("dstip", new String[]{deviceIp, "<>"});
                    } else {
                        //暂时先用端口代替服务
                        countermeasureRestriction.put("srcport", new String[]{cmValue, "<>"});
                        countermeasureRestriction.put("dstport", new String[]{cmValue, "<>"});
                    }
                    countermeasureRestrictions.add(countermeasureRestriction);
                }
                break;
        }
        return countermeasureRestrictions;
    }

    @Override
    public Map<String, String[]> bulidcomRestrictions(List<Countermeasure[]> cms) {
        return null;
    }

}
