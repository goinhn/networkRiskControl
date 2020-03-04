package com.goinhn.networkriskcontrol.repository;

import com.goinhn.networkriskcontrol.NetworkriskcontrolApplication;
import com.goinhn.networkriskcontrol.entity.Metric;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = NetworkriskcontrolApplication.class)
public class MetricRepositoryTest {

    @Autowired
    private MetricRepository metricRepository;

    @Test
    public void testFindMetricByName(){
        Metric metric = metricRepository.findMetricByName("cpu");
        System.out.println(metric);
    }
}
