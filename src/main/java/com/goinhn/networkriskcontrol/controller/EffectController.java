package com.goinhn.networkriskcontrol.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.goinhn.networkriskcontrol.entity.Device;
import com.goinhn.networkriskcontrol.entity.Metric;
import com.goinhn.networkriskcontrol.entity.Traffic;
import com.goinhn.networkriskcontrol.service.BasicDataResolveService;
import com.goinhn.networkriskcontrol.service.CounterMeasureResolveService;
import com.goinhn.networkriskcontrol.service.RiskComputeService;
import com.goinhn.networkriskcontrol.util.HostRiskUtil;
import com.goinhn.networkriskcontrol.util.NetworkIP;
import com.goinhn.networkriskcontrol.util.StrategyUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "评估效果模块")
@RestController
@RequestMapping("/effect")
public class EffectController {

    @Autowired
    private BasicDataResolveService basicDataResolveService;

    @Autowired
    private RiskComputeService riskComputeService;

    @Autowired
    private CounterMeasureResolveService counterMeasureResolveService;


    @GetMapping("/queryCountermeasures")
    public List<String> queryCountermeasureNames() {//展示右侧最优控制机制列表，该方法根据前台传入的时间信息，查询流量等数据，并计算风险，返回每秒的风险值等信息

        Map<String, Map<String, List<Map<String, String[]>>>> allDevicesControlRestrictions;
        List<String> result = new ArrayList<>();

        result.add("Normal" + "-None-0");
        allDevicesControlRestrictions = queryCountermeasures();

        for (Map.Entry<String, Map<String, List<Map<String, String[]>>>> oneDeviceControlRestrictions :
                allDevicesControlRestrictions.entrySet()) {
            String deviceName = oneDeviceControlRestrictions.getKey();
            for (Map.Entry<String, List<Map<String, String[]>>> oneCountermeasureClassRestrictions :
                    oneDeviceControlRestrictions.getValue().entrySet()) {
                String countermeasureClassName = oneCountermeasureClassRestrictions.getKey();
                List<Map<String, String[]>> countermeasures = oneCountermeasureClassRestrictions.getValue();
                for (Map<String, String[]> countermeasure :
                        countermeasures) {
                    String filterValue = "";
                    for (Map.Entry<String, String[]> countermeasureEntry :
                            countermeasure.entrySet()) {
                        filterValue = countermeasureEntry.getValue()[0];
                    }
                    result.add(deviceName + "-" + countermeasureClassName.split("-")[0] + "-" + filterValue);
                }
            }
        }
        return result;
    }

//    @RequestMapping("/riskEvolution/{timeMap}")
//    public Map<String, List<String>> queryTraffics(@PathVariable String timeMap) {//该方法根据前台传入的时间信息，查询流量等数据，并计算风险，返回每秒的风险值等信息
//        Map<String, String[]> file_map = basicDataResolveService.readFile("metric.txt");//读文件取指标
//        Map<String, List<Metric>> metrics = basicDataResolveService.resolveMetric(file_map);//取数据库中的指标信息
//        Map<String, String[]> file_map_dcm = basicDataResolveService.readFileByType("metric-DCM.txt", "DCM");//读文件取指标
//        List<Metric> metrics_dcm = basicDataResolveService.resolveMetricByType(file_map_dcm, "DCM");//取数据库中的指标信息
//        Map<String, String[]> file_map_dim = basicDataResolveService.readFileByType("metric-DIM.txt", "DIM");//读文件取指标
//        List<Metric> metrics_dim = basicDataResolveService.resolveMetricByType(file_map_dim, "DIM");//取数据库中的指标信息
//        Map<String, String[]> file_map_mcm = basicDataResolveService.readFileByType("metric-MCM.txt", "MCM");//读文件取指标
//        List<Metric> metrics_mcm = basicDataResolveService.resolveMetricByType(file_map_mcm, "MCM");//取数据库中的指标信息
//        Map<String, String[]> file_map_pcm = basicDataResolveService.readFileByType("metric-PCM.txt", "PCM");//读文件取指标
//        List<Metric> metrics_pcm = basicDataResolveService.resolveMetricByType(file_map_pcm, "PCM");//取数据库中的指标信息
//        Map<String, String[]> file_map_pim = basicDataResolveService.readFileByType("metric-PIM.txt", "PIM");//读文件取指标
//        List<Metric> metrics_pim = basicDataResolveService.resolveMetricByType(file_map_pim, "PIM");//取数据库中的指标信息
//        Map<String, String[]> file_map_dem = basicDataResolveService.readFileByType("metric-DEM.txt", "DEM");//读文件取指标
//        List<Metric> metrics_dem = basicDataResolveService.resolveMetricByType(file_map_dem, "DEM");//取数据库中的指标信息
//
//
//        List<Double> rundata = riskComputeService.getRiskRun(metrics.get("run"), HostRiskUtil.generateRunData());//生成模拟的运行指数数据
//        List<Double> vuldata = riskComputeService.getRiskVulnerability(HostRiskUtil.generateVulnerabilityData());//生成脆弱性指数数据
//        List<Double> dcmdata = riskComputeService.getRiskDcm(metrics_dcm, HostRiskUtil.generateDCMData());
//        List<Double> dimdata = riskComputeService.getRiskDim(metrics_dim, HostRiskUtil.generateDIMData());
//        List<Double> mcmdata = riskComputeService.getRiskMcm(metrics_mcm, HostRiskUtil.generateMCMData());
//        List<Double> pcmdata = riskComputeService.getRiskPcm(metrics_pcm, HostRiskUtil.generatePCMData());
//        List<Double> pimdata = riskComputeService.getRiskPim(metrics_pim, HostRiskUtil.generatePIMData());
//        List<Double> demdata = riskComputeService.getRiskDem(metrics_dem, HostRiskUtil.generateDEMData());
//
//        List<Traffic> trafficList = basicDataResolveService.findTrafficByTime(timeMap);
//
//        Map<String, Map<String, List<Map<String, String[]>>>> allDevicesControlRestrictions = queryCountermeasures();
//
//
//
//        int start = Integer.parseInt(timeMap.split("-")[0]);//得到开始时间
//        int end = Integer.parseInt(timeMap.split("-")[1]);//得到结束时间
//        List<Double> trafficData;//每个时间窗内的流量数据
//
//        int num;//时间窗内的连接总数
//        int attackNum;//时间窗内的攻击连接数
//        int normalNum;//时间窗内的正常连接数
//        double risk;//时间窗的风险值
//
//        Map<String, List<String>> result = new HashMap<>();
//
//        List<Traffic> sub = new ArrayList<>();//每个时间窗内的流量
//        List<String> ans = new ArrayList<>();//最终返回的结果数组
//        String x;
//
//        for (int i = start; i <= end; i++) {
//            attackNum = 0;
//            normalNum = 0;
//            for (Traffic traffic : trafficList) {
//                if (i == traffic.getSTime()) {
//                    sub.add(traffic);
//                }
//            }
//            num = sub.size();
//            for (Traffic traffic : sub) {
//                int tag = traffic.getLabel();
//                if (tag == 1) {
//                    attackNum++;
//                }
//            }
//            normalNum = num - attackNum;
//
//            trafficData = riskComputeService.getRiskTraffic(metrics.get("traffic"), sub);
//            risk = riskComputeService.calculateRisk(trafficData, rundata, vuldata, dcmdata, dimdata, mcmdata, pcmdata, pimdata, demdata);
//
//            x = num + "-" + attackNum + "-" + normalNum + "-" + risk;
//            ans.add(x);
//            result.put("Normal" + "-None-0", ans);
//
//
//            //对多个设备的countermeasure进行遍历
//            for (Map.Entry<String, Map<String, List<Map<String, String[]>>>> oneDeviceControlRestrictions :
//                    allDevicesControlRestrictions.entrySet()) {
//                String deviceName = oneDeviceControlRestrictions.getKey();
//                for (Map.Entry<String, List<Map<String, String[]>>> oneCountermeasureClassRestrictions :
//                        oneDeviceControlRestrictions.getValue().entrySet()) {
//                    String countermeasureClassName = oneCountermeasureClassRestrictions.getKey();
//                    List<Map<String, String[]>> countermeasures = oneCountermeasureClassRestrictions.getValue();
//                    attackNum = 0;
//                    normalNum = 0;
//
//                    for (Map<String, String[]> countermeasure : countermeasures) {
//                        String filterValue = "";
//
//                        List oldSubSecond = sub.second;
//                        int oldNum = oldSubSecond.size();
//                        for (Map.Entry<String, String[]> countermeasureEntry : countermeasure.entrySet()) {
//                            String fieldName = countermeasureEntry.getKey();
//                            String[] fieldfilter = countermeasureEntry.getValue();
//                            filterValue = fieldfilter[0];
//                            String filterCondition = fieldfilter[1];
//
//                            List newSub = new ArrayList();
//                            num = sub.second.size();//所有连接数量
//                            for (Object t : sub.second) {
//                                Object[] c = (Object[]) t;
//                                if (intField.contains(fieldName)) {
//
//                                    int s1 = (int) c[sub.first.get(fieldName)];
//                                    int s2 = Integer.parseInt(filterValue);
//                                    if (filterCondition.equals("<>")) {
//                                        if (s1 != s2) {
//                                            newSub.add(t);
//                                        }
//                                    }
//                                } else if (doubleField.contains(fieldName)) {
//                                    double s1 = (double) c[sub.first.get(fieldName)];
//                                    double s2 = Double.parseDouble(filterValue);
//                                    if (filterCondition.equals("<>")) {
//                                        if (s1 != s2) {
//                                            newSub.add(t);
//                                        }
//                                    }
//                                } else {
//                                    String s1 = (String) c[sub.first.get(fieldName)];
//                                    if (filterCondition.equals("<>")) {
//                                        if (!s1.equals(filterValue)) {
//                                            newSub.add(t);
//                                        }
//                                    }
//                                }
//                            }
//                            sub.second = newSub;
//                        }
//
//                        attackNum = 0;
//                        normalNum = 0;
//                        num = sub.second.size();//所有连接数量
//                        for (Object t : sub.second) {
//                            Object[] c = (Object[]) t;
//                            int s1 = (int) c[sub.first.get("label")];
//                            if (s1 == 1) {//是攻击流量就+1
//                                attackNum++;
//                            }
//                        }
//                        normalNum = num - attackNum;
//
//
////                        在每个设备添加上不同的策略之后计算风险值
//                        trafficData = riskComputeService.getRiskTraffic(metrics.get("traffic"), sub);
//                        risk = riskComputeService.calculateRisk(trafficData, rundata, vuldata, dcmdata, dimdata, mcmdata, pcmdata, pimdata, demdata);
//
//
//                        x = num + "-" + attackNum + "-" + normalNum + "-" + risk;
//                        List<String> oldAns = new ArrayList<>();
//                        if (ans.containsKey(deviceName + "-" + countermeasureClassName.split("-")[0] + "-" + filterValue)) {
//                            oldAns = ans.get(deviceName + "-" + countermeasureClassName.split("-")[0] + "-" + filterValue);
//                        }
//                        oldAns.add(x);
//                        ans.put(deviceName + "-" + countermeasureClassName.split("-")[0] + "-" + filterValue, oldAns);
////                        System.out.println(i);
//                        sub.second = oldSubSecond;
//                    }
//
//                }
//            }
//
//        }
//
//
//        return ans;
//    }

    @RequestMapping("/bestCountermeasure")
    public Map<String, Double> best(@RequestBody Map<String, String[]> map) {   //最优策略的选择
        double risk_sum;
        double min = Double.MAX_VALUE;
        String best = "";
        Map<String, Double> temp = new HashMap<>();
        for (String name : map.keySet()) {
            risk_sum = 0.0;
            for (String s : map.get(name)) {
                risk_sum += Double.valueOf(s);
            }
            temp.put(name, risk_sum);
            if (risk_sum < min) {
                min = risk_sum;
                best = name;
            }
        }
        Map<String, Double> result = new HashMap<>();//可能有多个控制机制效果相同，返回集合
        for (String name : temp.keySet()) {
            if (temp.get(name) == min) {
                result.put(name, min);
            }
        }
        return result;
    }


    private Map<String, Map<String, List<Map<String, String[]>>>> queryCountermeasures() {
        Map<String, String> deviceInfo1 = new HashMap<>();
        Map<String, String> deviceInfo2 = new HashMap<>();
        Map<String, String> deviceInfo3 = new HashMap<>();
        Map<String, Map<String, String>> devices = new HashMap<>();

        List<Device> resolvedDevices;
        List<Device> devicesWithEnumCountermeasures;
        Map<String, Map<String, List<Map<String, String[]>>>> allDevicesControlRestrictions;

        deviceInfo1.put("type", "router");
        deviceInfo1.put("countermeasures", "block-all,shutdown-all");
        deviceInfo1.put("ip", "1.1.1.1");
        deviceInfo1.put("services", "21,22,23,80,8080,443");
        devices.put("Router1", deviceInfo1);

        deviceInfo2.put("type", "router");
        deviceInfo2.put("countermeasures", "block-all,shutdown-all");
        deviceInfo2.put("ip", NetworkIP.DEVICE_2_IP.getIp());
        deviceInfo2.put("services", "21,22,23,80,8080,443");
        devices.put("Router2", deviceInfo2);

        deviceInfo3.put("type", "firewall");
        deviceInfo3.put("countermeasures", "block-all,shutdown-all");
        deviceInfo3.put("ip", "1.1.1.2");
        deviceInfo3.put("services", "21,22,23,80,8080,443");
        devices.put("Firewall", deviceInfo3);


        resolvedDevices = counterMeasureResolveService.resolveDevice(devices);
        devicesWithEnumCountermeasures = counterMeasureResolveService.enumCountermeasures(resolvedDevices);
        allDevicesControlRestrictions = counterMeasureResolveService.buildAllDeviceControlRestrictions(devicesWithEnumCountermeasures);
        String jsonString = JSON.toJSONString(allDevicesControlRestrictions, SerializerFeature.DisableCircularReferenceDetect);
        System.out.println(jsonString);
        return allDevicesControlRestrictions;
    }


}
