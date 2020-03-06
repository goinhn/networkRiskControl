package com.goinhn.networkriskcontrol.service.impl;

import com.goinhn.networkriskcontrol.entity.*;
import com.goinhn.networkriskcontrol.service.RiskComputeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RiskComputeServiceImpl implements RiskComputeService {

    @Override
    public List<Double> getRiskRun(List<Metric> metrics, List<Run> runs) {
        List<Double> riskRun = new ArrayList<>();
        String rule[] = null;
        List<Integer> classes = new ArrayList<>();
        double x;//指标的增量

        for (Metric m : metrics) {
            rule = m.getRule().split("-");//以逗号对指标的规则字段进行划分，这个数组大小为3，第1个为阈值，第2为区间跨度，第3个为区间个数，感觉第3个没必要
            for (Run r : runs) {
                //前者第一个为程序中设置的固定数据
                x = (r.get(m.getFields()) - Double.valueOf(rule[0])) / Double.valueOf(rule[0]);//进行区间划分，
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
            riskRun.add((double) 0.33 * classes.get(i) + 0.33 * classes.get(i + runs.size()) + 0.33 * classes.get(i + 2 * runs.size()));//权重可能以后会改
        }
        return riskRun;
    }

    @Override
    public List<Double> getRiskVulnerability(List<Vulnerability> vulnerabilities) {
        //漏洞指标的规则比较简单，直接转化为[1,10]之间的等级，不用取数据库的规则
        List<Double> riskVul = new ArrayList<>();
        double classes;
        for (Vulnerability v : vulnerabilities) {
            classes = 0.0;
            for (double score : v.getScore()) {
                classes += (int) score + 1;//转化为[1,10]之间的评分
            }

            classes /= v.getScore().size();//转化后取平均
            classes *= 0.5;//漏洞评分占0.5
            classes += v.getAsset() * 0.5;//资产评分占0.5
            riskVul.add(classes);
        }
        return riskVul;
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


    @Override
    public List<Double> getRiskTraffic(List<Metric> metrics, List<Traffic> trafficList) {
        List<Double> risk_traffic = new ArrayList<>();
        String rule[] = null, field[] = null, f_rule[] = null;//每个字段对应的规则
        List<Integer> m_classes = new ArrayList<>();//每个指标得到的风险评级
        List<Double> classes = new ArrayList<>();//总的风险评级
        double x;//指标的增量
        double y;//临时变量
        int s;

        for (Metric m : metrics) {
            field = m.getFields().split(",");//对字段进行划分
            rule = m.getRule().split(",");//以逗号对指标的规则字段进行划分

            for (int i = 0; i < field.length; i++) {
                f_rule = rule[i].split("-");//对每个字段的规则进行划分，以-分割的
                for (Traffic t : trafficList) {

                    s = 1;
                    x = (s - Double.valueOf(f_rule[0])) / Double.valueOf(f_rule[0]);//对当前指标中的一个字段进行风险评级
                    if (s <= Double.valueOf(f_rule[0])) {
                        m_classes.add(2);
                    } else if (x > 0.0 && x <= Double.valueOf(f_rule[1])) {
                        m_classes.add(4);
                    } else if (x > Double.valueOf(f_rule[1]) && x <= 2 * Double.valueOf(f_rule[1])) {
                        m_classes.add(6);
                    } else if (x > 2 * Double.valueOf(f_rule[1]) && x <= 3 * Double.valueOf(f_rule[1])) {
                        m_classes.add(8);
                    } else {
                        m_classes.add(10);
                    }
                }
            }

            for (int i = 0; i < trafficList.size(); i++) {//对一个指标进行不同字段之间的综合，采用加权平均
                y = 0.0;
                for (int j = 0; j < field.length; j++) {
                    y += m_classes.get(i + j * trafficList.size()) * (double) 1 / field.length;
                }
                classes.add(y);
            }
        }

        for (int i = 0; i < trafficList.size(); i++) {//对所有指标进行综合，采用加权平均，权重后面可能会修改
            y = 0.0;
            for (int j = 0; j < metrics.size(); j++) {
                y += classes.get(i + j * trafficList.size()) * (double) 1 / metrics.size();
            }
            risk_traffic.add(y);
        }

        return risk_traffic;
    }


    @Override
    public double calculateRisk(List<Double> traffics, List<Double> runs, List<Double> vuls,
                                List<Double> dcms, List<Double> dims, List<Double> mcms,
                                List<Double> pcms, List<Double> pims, List<Double> dems) {
        double risk_traffic = 0.0, risk_run = 0.0, risk_vuls = 0.0;
        double risk_dcms = 0.0;
        double risk_dims = 0.0;
        double risk_mcms = 0.0;
        double risk_pcms = 0.0;
        double risk_pims = 0.0;
        double risk_dems = 0.0;

        for (double a : traffics) {
            risk_traffic += a;
        }
        risk_traffic /= traffics.size();

        for (double a : runs) {
            risk_run += a;
        }
        risk_run /= runs.size();

        for (double a : vuls) {
            risk_vuls += a;
        }
        risk_vuls /= vuls.size();

        for (double a : dcms) {
            risk_dcms += a;
        }
        for (double a : dims) {
            risk_dims += a;
        }
        for (double a : mcms) {
            risk_mcms += a;
        }
        for (double a : pcms) {
            risk_pcms += a;
        }
        for (double a : pims) {
            risk_pims += a;
        }
        for (double a : dems) {
            risk_dems += a;
        }

        if (traffics.size() == 0) {//处理出现当前时间窗内流量为0的情况
            return (risk_run + risk_vuls + risk_dcms + risk_dims + risk_mcms + risk_pcms + risk_pims + risk_dems) / 8;
        } else {
            return 0.5 * risk_traffic + 0.5 * (risk_run + risk_vuls + risk_dcms + risk_dims + risk_mcms + risk_pcms + risk_pims + risk_dems) / 8;//返回最后的风险值，也是加权平均
        }
    }
}
