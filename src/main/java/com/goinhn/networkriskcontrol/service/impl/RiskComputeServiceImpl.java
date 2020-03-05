package com.goinhn.networkriskcontrol.service.impl;

import com.goinhn.networkriskcontrol.entity.*;
import com.goinhn.networkriskcontrol.service.RiskComputeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RiskComputeImpl implements RiskComputeService {

    @Override
    public List<Double> getRiskRun(List<Metric> metrics, List<Run> runs) {
        List<Double> risk_run = new ArrayList<Double>();
        String rule[] = null;
        List<Integer> classes = new ArrayList<Integer>();
        double x;//指标的增量
        for (Metric m : metrics) {
//            规则的组成
            rule = m.getRule().split("-");//以逗号对指标的规则字段进行划分，这个数组大小为3，第1个为阈值，第2为区间跨度，第3个为区间个数，感觉第3个没必要
            for (Run r : runs) {
                //前者第一个为程序中设置的固定数据
                x = (r.get(m.getFields()) - Double.valueOf(rule[0])) / Double.valueOf(rule[0]);//进行区间划分，
//              以阀值为基准，超过阀值的部分，每增加一个区间跨度，权重上升相应的倍数
                if (r.get(m.getFields()) <= Double.valueOf(rule[0])) {
                    classes.add(2);
                } else if (x > 0.0 && x <= Double.valueOf(rule[1])) {
                    classes.add(4);
                } else if (x > Double.valueOf(rule[1]) && x <= 2 * Double.valueOf(rule[1])) {
                    classes.add(6);
                } else if (x > 2 * Double.valueOf(rule[1]) && x <= 3 * Double.valueOf(rule[1])) {
                    classes.add(8);
                } else {
                    classes.add(10);
                }
            }
        }

        for (int i = 0; i < runs.size(); i++) {//对三个指标进行加权求和
//            cpu、memory、bandwith三者的权重简单的求平均数
            risk_run.add((double) 0.33 * classes.get(i) + 0.33 * classes.get(i + runs.size()) + 0.33 * classes.get(i + 2 * runs.size()));//权重可能以后会改
        }
        return risk_run;
    }

    @Override
    public List<Double> getRiskVulnerability(List<Vulnerability> vulnerabilities) {
        //漏洞指标的规则比较简单，直接转化为[1,10]之间的等级，不用取数据库的规则
        List<Double> risk_vul = new ArrayList<Double>();
        double classes;
        for (Vulnerability v : vulnerabilities) {
            classes = 0.0;
            for (double score : v.getScore()) {
                classes += (int) score + 1;//转化为[1,10]之间的评分
            }

//            漏洞和资产进行简单加权求和
            classes /= v.getScore().size();//转化后取平均
            classes *= 0.5;//漏洞评分占0.5
            classes += v.getAsset() * 0.5;//资产评分占0.5
            risk_vul.add(classes);
        }
        return risk_vul;
    }


    @Override
    public List<Double> getRiskDcm(List<Metric> metricsDcm, List<DCM> dcms) {
        List<Double> risk_run = new ArrayList<Double>();
        String[] rule;
        List<Integer> classes = new ArrayList<Integer>();
        double x;//指标的增量
        for (Metric m : metricsDcm) {
            rule = m.getRule().split("-");//以逗号对指标的规则字段进行划分，这个数组大小为3，第1个为阈值，第2为区间跨度，第3个为区间个数，感觉第3个没必要
            for (DCM dcm : dcms) {
                x = (dcm.get(m.getFields()) - Double.valueOf(rule[0])) / Double.valueOf(rule[0]);//进行区间划分，
                if (dcm.get(m.getFields()) <= Double.valueOf(rule[0])) {
                    classes.add(2);
                } else if (x > 0.0 && x <= Double.valueOf(rule[1])) {
                    classes.add(4);
                } else if (x > Double.valueOf(rule[1]) && x <= 2 * Double.valueOf(rule[1])) {
                    classes.add(6);
                } else if (x > 2 * Double.valueOf(rule[1]) && x <= 3 * Double.valueOf(rule[1])) {
                    classes.add(8);
                } else {
                    classes.add(10);
                }
            }
        }

        for (int i = 0; i < dcms.size(); i++) {//对三个指标进行加权求和
            risk_run.add((double) 0.33 * classes.get(i) + 0.33 * classes.get(i + dcms.size()) + 0.33 * (0.5 * classes.get(i + 2 * dcms.size()) + 0.5 * (classes.get(i + 3 * dcms.size()))));//权重可能以后会改
        }
        return risk_run;
    }


    @Override
    public List<Double> getRiskDim(List<Metric> metricsDim, List<DIM> dims) {
        List<Double> risk_run = new ArrayList<Double>();
        String[] rule;
        List<Integer> classes = new ArrayList<Integer>();
        double x;//指标的增量
        for (Metric m : metricsDim) {
            rule = m.getRule().split("-");//以逗号对指标的规则字段进行划分，这个数组大小为3，第1个为阈值，第2为区间跨度，第3个为区间个数，感觉第3个没必要
            for (DIM dim : dims) {
                x = (dim.get(m.getFields()) - Double.valueOf(rule[0])) / Double.valueOf(rule[0]);//进行区间划分，
                if (dim.get(m.getFields()) <= Double.valueOf(rule[0])) {
                    classes.add(2);
                } else if (x > 0.0 && x <= Double.valueOf(rule[1])) {
                    classes.add(4);
                } else if (x > Double.valueOf(rule[1]) && x <= 2 * Double.valueOf(rule[1])) {
                    classes.add(6);
                } else if (x > 2 * Double.valueOf(rule[1]) && x <= 3 * Double.valueOf(rule[1])) {
                    classes.add(8);
                } else {
                    classes.add(10);
                }
            }
        }
        for (int i = 0; i < dims.size(); i++) {//对三个指标进行加权求和
            risk_run.add((double) 0.33 * classes.get(i) + 0.33 * classes.get(i + dims.size()) + 0.33 * classes.get(i + 2 * dims.size()));//权重可能以后会改
        }
        return risk_run;
    }


    public List<Double> getRiskMcm(List<Metric> metricsMcm, List<MCM> mcms) {
        List<Double> risk_mcm = new ArrayList<Double>();
        String[] rule;
        List<Integer> classes = new ArrayList<Integer>();
        double x;//指标的增量
        for (Metric m : metricsMcm) {
            rule = m.getRule().split("-");//以逗号对指标的规则字段进行划分，这个数组大小为3，第1个为阈值，第2为区间跨度，第3个为区间个数，感觉第3个没必要
            for (MCM mcm : mcms) {
                x = (mcm.get(m.getFields()) - Double.valueOf(rule[0])) / Double.valueOf(rule[0]);//进行区间划分，
                if (mcm.get(m.getFields()) <= Double.valueOf(rule[0])) {
                    classes.add(2);
                } else if (x > 0.0 && x <= Double.valueOf(rule[1])) {
                    classes.add(4);
                } else if (x > Double.valueOf(rule[1]) && x <= 2 * Double.valueOf(rule[1])) {
                    classes.add(6);
                } else if (x > 2 * Double.valueOf(rule[1]) && x <= 3 * Double.valueOf(rule[1])) {
                    classes.add(8);
                } else {
                    classes.add(10);
                }
            }
        }
        for (int i = 0; i < mcms.size(); i++) {//对三个指标进行加权求和
            risk_mcm.add((double) 0.25 * (classes.get(i) + classes.get(i + mcms.size())) + 0.25 * (classes.get(i + 2 * mcms.size()) + classes.get(i + 2 * mcms.size())));//权重可能以后会改
        }
        return risk_mcm;
    }


    @Override
    public List<Double> getRiskPcm(List<Metric> metricsPcm, List<PCM> pcms) {
        List<Double> risk_pcm = new ArrayList<Double>();
        String[] rule;
        List<Integer> classes = new ArrayList<Integer>();
        double x;//指标的增量
        for (Metric m : metricsPcm) {
            rule = m.getRule().split("-");//以逗号对指标的规则字段进行划分，这个数组大小为3，第1个为阈值，第2为区间跨度，第3个为区间个数，感觉第3个没必要
            for (PCM pcm : pcms) {
                x = (pcm.get(m.getFields()) - Double.valueOf(rule[0])) / Double.valueOf(rule[0]);//进行区间划分，
                if (pcm.get(m.getFields()) <= Double.valueOf(rule[0])) {
                    classes.add(2);
                } else if (x > 0.0 && x <= Double.valueOf(rule[1])) {
                    classes.add(4);
                } else if (x > Double.valueOf(rule[1]) && x <= 2 * Double.valueOf(rule[1])) {
                    classes.add(6);
                } else if (x > 2 * Double.valueOf(rule[1]) && x <= 3 * Double.valueOf(rule[1])) {
                    classes.add(8);
                } else {
                    classes.add(10);
                }
            }
        }
        for (int i = 0; i < pcms.size(); i++) {//对三个指标进行加权求和
            risk_pcm.add((double) 1 / 6 * (classes.get(i)
                    + classes.get(i + pcms.size())
                    + 0.5 * (classes.get(i + 2 * pcms.size()) + classes.get(i + 3 * pcms.size()))
                    + 0.25 * (classes.get(i + 4 * pcms.size()) + classes.get(i + 5 * pcms.size()) + classes.get(i + 6 * pcms.size()) + classes.get(i + 7 * pcms.size()))
                    + 0.5 * (classes.get(i + 8 * pcms.size()) + classes.get(i + 9 * pcms.size()) + classes.get(i + 10 * pcms.size()) + classes.get(i + 11 * pcms.size()))
            ));//权重可能以后会改
        }
        return risk_pcm;
    }

    @Override
    public List<Double> getRiskPim(List<Metric> metricsPim, List<PIM> pims) {
        List<Double> risk_pim = new ArrayList<Double>();
        String[] rule;
        List<Integer> classes = new ArrayList<Integer>();
        double x;//指标的增量
        for (Metric m : metricsPim) {
            rule = m.getRule().split("-");//以逗号对指标的规则字段进行划分，这个数组大小为3，第1个为阈值，第2为区间跨度，第3个为区间个数，感觉第3个没必要
            for (PIM pim : pims) {
                x = (pim.get(m.getFields()) - Double.valueOf(rule[0])) / Double.valueOf(rule[0]);//进行区间划分，
                if (pim.get(m.getFields()) <= Double.valueOf(rule[0])) {
                    classes.add(2);
                } else if (x > 0.0 && x <= Double.valueOf(rule[1])) {
                    classes.add(4);
                } else if (x > Double.valueOf(rule[1]) && x <= 2 * Double.valueOf(rule[1])) {
                    classes.add(6);
                } else if (x > 2 * Double.valueOf(rule[1]) && x <= 3 * Double.valueOf(rule[1])) {
                    classes.add(8);
                } else {
                    classes.add(10);
                }
            }
        }
        for (int i = 0; i < pims.size(); i++) {//对三个指标进行加权求和
            risk_pim.add((double) 1 / 2 * (classes.get(i)
                    + classes.get(i + pims.size())));//权重可能以后会改
        }
        return risk_pim;
    }

    @Override
    public List<Double> getRiskDem(List<Metric> metricsDem, List<DEM> dems) {
        List<Double> risk_dem = new ArrayList<Double>();
        String[] rule;
        List<Integer> classes = new ArrayList<Integer>();
        double x;//指标的增量
        for (Metric m : metricsDem) {
            rule = m.getRule().split("-");//以逗号对指标的规则字段进行划分，这个数组大小为3，第1个为阈值，第2为区间跨度，第3个为区间个数，感觉第3个没必要
            for (DEM dem : dems) {
                x = (dem.get(m.getFields()) - Double.valueOf(rule[0])) / Double.valueOf(rule[0]);//进行区间划分，
                if (dem.get(m.getFields()) <= Double.valueOf(rule[0])) {
                    classes.add(2);
                } else if (x > 0.0 && x <= Double.valueOf(rule[1])) {
                    classes.add(4);
                } else if (x > Double.valueOf(rule[1]) && x <= 2 * Double.valueOf(rule[1])) {
                    classes.add(6);
                } else if (x > 2 * Double.valueOf(rule[1]) && x <= 3 * Double.valueOf(rule[1])) {
                    classes.add(8);
                } else {
                    classes.add(10);
                }
            }
        }
        for (int i = 0; i < dems.size(); i++) {//对三个指标进行加权求和
            risk_dem.add((double) 1 / 3 * (classes.get(i) + classes.get(i + dems.size())
                    + classes.get(i + 2 * dems.size())));//权重可能以后会改
        }
        return risk_dem;
    }

}
