package com.goinhn.networkriskcontrol.controller;

import com.goinhn.networkriskcontrol.entity.Metric;
import com.goinhn.networkriskcontrol.entity.Traffic;
import com.goinhn.networkriskcontrol.service.BasicDataResolveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Api(tags = "风险演化模块模块")
@RestController
@RequestMapping("/evolution")
public class EvolutionController {

    @Autowired
    private BasicDataResolveService basicDataResolveService;


    /**
     * 初始传入约束为前端时间约束，该方法根据前台传入的时间信息，查询流量等数据，并计算风险，返回每秒的风险值等信息
     *
     * @param timeMap
     * @return
     */
    @ApiOperation("风险演化模块数据请求")
    @GetMapping("/riskEvolution")
    public List<String> queryTraffics(@RequestBody Map<String, String[]> timeMap) {

        Map<String, String[]> fileMap = basicDataResolveService.readFile("metric.txt");
        Map<String, List<Metric>> metrics = basicDataResolveService.resolveMetric(fileMap);

        Map<String, String[]> fileMapDcm = basicDataResolveService.readFileByType("metric-DCM.txt", "DCM");
        List<Metric> metricsDcm = basicDataResolveService.resolveMetricByType(fileMapDcm, "DCM");

        Map<String, String[]> fileMapDim = basicDataResolveService.readFileByType("metric-DIM.txt", "DIM");
        List<Metric> metricsDim = basicDataResolveService.resolveMetricByType(fileMapDim, "DIM");

        Map<String, String[]> fileMapMcm = basicDataResolveService.readFileByType("metric-MCM.txt", "MCM");
        List<Metric> metricMcm = basicDataResolveService.resolveMetricByType(fileMapMcm, "MCM");

        Map<String, String[]> fileMapPcm = basicDataResolveService.readFileByType("metric-PCM.txt", "PCM");
        List<Metric> metricsPcm = basicDataResolveService.resolveMetricByType(fileMapPcm, "PCM");

        Map<String, String[]> fileMapPim = basicDataResolveService.readFileByType("metric-PIM.txt", "PIM");
        List<Metric> metricsPim = basicDataResolveService.resolveMetricByType(fileMapPim, "PIM");

        Map<String, String[]> fileMapDem = basicDataResolveService.readFileByType("metric-DEM.txt", "DEM");
        List<Metric> metricsDem = basicDataResolveService.resolveMetricByType(fileMapDem, "DEM");


        List<Traffic> trafficList = basicDataResolveService.findTrafficByTime(timeMap);









//        Map：字段，列号的集合  List：每一列按照顺序对应值的集合
//        TwoTuple<Map<String, Integer>, List> traffics = basicDataResolveService.queryTraffic(sql);//返回查询出的流量


        List<Double> rundata = riskComputeService.getRiskclass_run(metrics.get("run"), MyUtil.generateRunData());//生成模拟的运行指数数据
        List<Double> vuldata = riskComputeService.getRiskclass_vul(MyUtil.generateVulData());//生成脆弱性指数数据
        List<Double> dcmdata = riskComputeService.getRiskclass_dcm(metricsDcm, MyUtil.generateDCMData());
        List<Double> dimdata = riskComputeService.getRiskclass_dim(metricsDim, MyUtil.generateDIMData());
        List<Double> mcmdata = riskComputeService.getRiskclass_mcm(metricMcm, MyUtil.generateMCMData());
        List<Double> pcmdata = riskComputeService.getRiskclass_pcm(metricsPcm, MyUtil.generatePCMData());
        List<Double> pimdata = riskComputeService.getRiskclass_pim(metricsPim, MyUtil.generatePIMData());
        List<Double> demdata = riskComputeService.getRiskclass_dem(metricsDem, MyUtil.generateDEMData());





//        //数据处理
//        int start = Integer.parseInt(time_map.get("stime")[0].split("-")[0]);//得到开始时间
//        int end = Integer.parseInt(time_map.get("stime")[0].split("-")[1]);//得到结束时间
        List<Double> traffic_data;//每个时间窗内的流量数据
        Map<String, Integer> fieldIndex = traffics.first;

        int num;//时间窗内的连接总数
        int attack_num;//时间窗内的攻击连接数
        int normal_num;//时间窗内的正常连接数
        double risk;//时间窗的风险值

        TwoTuple<Map<String, Integer>, List> sub;//每个时间窗内的流量
        List<String> ans = new ArrayList<>();//最终返回的结果数组
        String x;//每个时间窗内返回的字符串

        for (int i = start; i <= end; i++) {
            attack_num = 0;
            normal_num = 0;
            sub = MyUtil.splitByTime(traffics, i);//根据时间窗对流量进行划分
            num = sub.second.size();//所有连接数量

            for (Object t : sub.second) {
                Object[] c = (Object[]) t;
//                根据label标签的值判断
                int s1 = (int) c[sub.first.get("label")];
                if (s1 == 1) {//是攻击流量就+1
                    attack_num++;
                }
            }

            normal_num = num - attack_num;


            traffic_data = riskComputeService.getRiskclass_traffic(metrics.get(1), sub);

            //            风险的综合计算
            risk = riskComputeService.calculateRisk(traffic_data, rundata, vuldata, dcmdata, dimdata, mcmdata, pcmdata, pimdata, demdata);
            x = num + "-" + attack_num + "-" + normal_num + "-" + risk;
            ans.add(x);
        }



        return new LinkedList<>();
    }
}
