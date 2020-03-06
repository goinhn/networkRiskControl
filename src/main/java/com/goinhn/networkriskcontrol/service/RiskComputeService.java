package com.goinhn.networkriskcontrol.service;

import com.goinhn.networkriskcontrol.entity.*;

import java.util.List;

public interface RiskComputeService {

    List<Double> getRiskRun(List<Metric> metrics, List<Run> runs);

    List<Double> getRiskVulnerability(List<Vulnerability> vulnerabilities);

    List<Double> getRiskDcm(List<Metric> metricsDcm, List<DCM> dcms);

    List<Double> getRiskDim(List<Metric> metricsDim, List<DIM> dims);

    List<Double> getRiskMcm(List<Metric> metricsMcm, List<MCM> mcms);

    List<Double> getRiskPcm(List<Metric> metricsPcm, List<PCM> pcms);

    List<Double> getRiskPim(List<Metric> metricsPim, List<PIM> pims);

    List<Double> getRiskDem(List<Metric> metricsDem, List<DEM> dems);

    List<Double> getRiskTraffic(List<Metric> metrics, List<Traffic> trafficList);

    double calculateRisk(List<Double> traffics, List<Double> runs, List<Double> vuls,
                  List<Double> dcms, List<Double> dims, List<Double> mcms,
                  List<Double> pcms, List<Double> pims, List<Double> dems);
}
