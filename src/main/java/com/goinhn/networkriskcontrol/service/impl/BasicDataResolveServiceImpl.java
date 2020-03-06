package com.goinhn.networkriskcontrol.service.impl;

import com.goinhn.networkriskcontrol.entity.Metric;
import com.goinhn.networkriskcontrol.entity.Traffic;
import com.goinhn.networkriskcontrol.repository.MetricRepository;
import com.goinhn.networkriskcontrol.repository.TrafficRepository;
import com.goinhn.networkriskcontrol.service.BasicDataResolveService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import java.io.*;
import java.util.*;

@Service
public class BasicDataResolveServiceImpl implements BasicDataResolveService {

    @Autowired
    private MetricRepository metricRepository;

    @Autowired
    private TrafficRepository trafficRepository;


    /**
     * 配置自动事务
     */
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public SessionFactory getSessionFactory() {
        return entityManagerFactory.unwrap(SessionFactory.class);
    }


    /**
     * 读取metric.txt文件信息
     *
     * @param fileName metric.txt 文件名称
     * @return {
     * "run": ["cpu", "memory", "bandwidth"],
     * "traffic: ["packet_num", "bytes"]
     * }
     */
    @Override
    public Map<String, String[]> readFile(String fileName) {
        Map<String, String[]> map = new HashMap<>();
        List<String[]> metric = new ArrayList<>();

        BufferedReader bufferedReader = null;
        try {
            File file = ResourceUtils.getFile("classpath:static/ruleFile/" + fileName);
            if (file.exists()) {
                bufferedReader = new BufferedReader(new FileReader(file));
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    metric.add(line.split("-"));
                }
                map.put("run", metric.get(0));
                map.put("traffic", metric.get(1));
            }
        } catch (FileNotFoundException e) {
            System.out.println("文件读取错误!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return map;
    }


    /**
     * 读取不同指标文件中的信息值
     *
     * @param fileName 文件名称
     * @param fileType 文件类型
     * @return {
     * "fileType": ["", "", "", ""]
     * }
     */
    @Override
    public Map<String, String[]> readFileByType(String fileName, String fileType) {
        Map<String, String[]> map = new HashMap<>();
        List<String> metric = new ArrayList<>();
        BufferedReader bufferedReader = null;
        try {
            File file = ResourceUtils.getFile("classpath:static/ruleFile/" + fileName);
            if (file.exists()) {
                bufferedReader = new BufferedReader(new FileReader(file));
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] textMetric = line.split(";");
                    metric.addAll(Arrays.asList(textMetric));
                }
                map.put(fileType, metric.toArray(new String[metric.size()]));
            }
        } catch (FileNotFoundException e) {
            System.out.println("文件读取错误!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return map;
    }

    /**
     * 根据指标文件中的指标来获取数据库中指标对应的规则
     * 计算机本省的性能指标
     *
     * @param map 实体中的指标
     * @return {
     * "run": [Metric, Metric, Metric],
     * "traffic: [Metric, Metric, Metric]
     * }
     */
    @Transactional
    @Override
    public Map<String, List<Metric>> resolveMetric(Map<String, String[]> map) {
        String[] run = map.get("run");
        String[] traffic = map.get("traffic");

        Map<String, List<Metric>> metrics = new HashMap<>();
        List<Metric> runs = new ArrayList<>();
        List<Metric> traffics = new ArrayList<>();

        for (String s : run) {
            runs.add(metricRepository.findMetricByName(s));
        }
        metrics.put("run", runs);
        for (String s : traffic) {
            traffics.add(metricRepository.findMetricByName(s));
        }
        metrics.put("traffic", traffics);

        return metrics;
    }

    /**
     * 根据指标文件中的指标来获取数据库中指标对应的规则
     * 指标文件中的指标
     *
     * @param map
     * @param Type
     * @return [Metrics]
     */
    @Override
    public List<Metric> resolveMetricByType(Map<String, String[]> map, String Type) {
        String[] types = map.get(Type);
        List<Metric> metrics = new ArrayList<>();

        for (String s : types) {
            metrics.add(metricRepository.findMetricByName(s));
        }

        return metrics;
    }

    /**
     * 返回所有在指定时间内的traffic集合
     *
     * @param timeMap
     * @return
     */
    @Transactional
    @Override
    public List<Traffic> findTrafficByTime(String timeMap) {
        String timeBetween = timeMap;

        List<Traffic> trafficList = new ArrayList<>();
        try {
            int beginTime = Integer.parseInt(timeBetween.split("-")[0]);
            int endTime = Integer.parseInt(timeBetween.split("-")[1]);
            trafficList = trafficRepository.findTrafficBySTimeBetween(beginTime, endTime);
            return trafficList;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return trafficList;
    }


}
