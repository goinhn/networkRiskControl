package com.goinhn.networkriskcontrol.service;

import com.goinhn.networkriskcontrol.entity.Metric;
import com.goinhn.networkriskcontrol.entity.Traffic;

import java.util.List;
import java.util.Map;

public interface BasicDataResolveService {

    Map<String, String[]> readFile(String fileName);

    Map<String, String[]> readFileByType(String fileName, String fileType);

    Map<String, List<Metric>> resolveMetric(Map<String, String[]> map);

    List<Metric> resolveMetricByType(Map<String, String[]> map, String Type);

    List<Traffic> findTrafficByTime(String timeMap);
}
