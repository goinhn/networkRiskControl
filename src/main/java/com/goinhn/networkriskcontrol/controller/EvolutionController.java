package com.goinhn.networkriskcontrol.controller;

import com.goinhn.networkriskcontrol.entity.Metric;
import com.goinhn.networkriskcontrol.entity.Traffic;
import com.goinhn.networkriskcontrol.service.BasicDataResolveService;
import com.goinhn.networkriskcontrol.service.RiskComputeService;
import com.goinhn.networkriskcontrol.util.HostRiskUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Api(tags = "风险演化模块模块")
@RestController
@RequestMapping("/evolution")
public class EvolutionController {

    @Autowired
    private BasicDataResolveService basicDataResolveService;

    @Autowired
    private RiskComputeService riskComputeService;


    /**
     * 初始传入约束为前端时间约束，该方法根据前台传入的时间信息，查询流量等数据，并计算风险，返回每秒的风险值等信息
     *
     * @param timeMap 时间区间
     * @return
     */
    @ApiOperation("风险演化模块数据请求")
    @GetMapping("/riskEvolution/{timeMap}")
    public List<String> queryTraffics(@PathVariable("timeMap") String timeMap) {

        //        读取指标文件中的信息
        Map<String, String[]> fileMap = basicDataResolveService.readFile("metric.txt");
        Map<String, List<Metric>> metricHostAndTraffic = basicDataResolveService.resolveMetric(fileMap);
        Map<String, String[]> fileMapDcm = basicDataResolveService.readFileByType("metric-DCM.txt", "DCM");
        List<Metric> metricsDcm = basicDataResolveService.resolveMetricByType(fileMapDcm, "DCM");
        Map<String, String[]> fileMapDim = basicDataResolveService.readFileByType("metric-DIM.txt", "DIM");
        List<Metric> metricsDim = basicDataResolveService.resolveMetricByType(fileMapDim, "DIM");
        Map<String, String[]> fileMapMcm = basicDataResolveService.readFileByType("metric-MCM.txt", "MCM");
        List<Metric> metricsMCM = basicDataResolveService.resolveMetricByType(fileMapMcm, "MCM");
        Map<String, String[]> fileMapPcm = basicDataResolveService.readFileByType("metric-PCM.txt", "PCM");
        List<Metric> metricsPcm = basicDataResolveService.resolveMetricByType(fileMapPcm, "PCM");
        Map<String, String[]> fileMapPim = basicDataResolveService.readFileByType("metric-PIM.txt", "PIM");
        List<Metric> metricsPim = basicDataResolveService.resolveMetricByType(fileMapPim, "PIM");
        Map<String, String[]> fileMapDem = basicDataResolveService.readFileByType("metric-DEM.txt", "DEM");
        List<Metric> metricsDem = basicDataResolveService.resolveMetricByType(fileMapDem, "DEM");


        //        计算生成风险值
        List<Double> runData = riskComputeService.getRiskRun(metricHostAndTraffic.get("run"), HostRiskUtil.generateRunData());//生成模拟的运行指数数据
        List<Double> vulData = riskComputeService.getRiskVulnerability(HostRiskUtil.generateVulnerabilityData());//生成脆弱性指数数据
        List<Double> dcmData = riskComputeService.getRiskDcm(metricsDcm, HostRiskUtil.generateDCMData());
        List<Double> dimData = riskComputeService.getRiskDim(metricsDim, HostRiskUtil.generateDIMData());
        List<Double> mcmData = riskComputeService.getRiskMcm(metricsMCM, HostRiskUtil.generateMCMData());
        List<Double> pcmData = riskComputeService.getRiskPcm(metricsPcm, HostRiskUtil.generatePCMData());
        List<Double> pimData = riskComputeService.getRiskPim(metricsPim, HostRiskUtil.generatePIMData());
        List<Double> demData = riskComputeService.getRiskDem(metricsDem, HostRiskUtil.generateDEMData());


        //        查询在该时间段内的流量
        List<Traffic> trafficList = basicDataResolveService.findTrafficByTime(timeMap);


        int start = Integer.parseInt(timeMap.split("-")[0]);//得到开始时间
        int end = Integer.parseInt(timeMap.split("-")[1]);//得到结束时间
        List<Double> trafficData;//每个时间窗内的流量数据

        int num;//时间窗内的连接总数
        int attackNum;//时间窗内的攻击连接数
        int normalNum;//时间窗内的正常连接数
        double risk;//时间窗的风险值

        List<Traffic> sub = new ArrayList<>();//每个时间窗内的流量
        List<String> result = new ArrayList<>();//最终返回的结果数组
        String x;

        for (int i = start; i <= end; i++) {
            attackNum = 0;
            normalNum = 0;
            for (Traffic traffic : trafficList) {
                if (i == traffic.getSTime()) {
                    sub.add(traffic);
                }
            }
            num = sub.size();
            for (Traffic traffic : sub) {
                int tag = traffic.getLabel();
                if (tag == 1) {
                    attackNum++;
                }
            }
            normalNum = num - attackNum;

            trafficData = riskComputeService.getRiskTraffic(metricHostAndTraffic.get("traffic"), sub);
            risk = riskComputeService.calculateRisk(trafficData, runData, vulData, dcmData, dimData, mcmData, pcmData, pimData, demData);
            x = num + "-" + attackNum + "-" + normalNum + "-" + risk;
            result.add(x);
        }

        return result;
    }
}