package com.goinhn.networkriskcontrol.service;

import com.goinhn.networkriskcontrol.NetworkriskcontrolApplication;
import com.goinhn.networkriskcontrol.entity.Metric;
import com.goinhn.networkriskcontrol.service.impl.BasicDataResolveServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = NetworkriskcontrolApplication.class)
public class BasicDataResolveServiceTest {

    private BasicDataResolveService basicDataResolveService = new BasicDataResolveServiceImpl();

    @Test
    public void testReadFile() {
        Map<String, String[]> map = null;
        map = basicDataResolveService.readFile("metric.txt");
        String[] run = map.get("run");
        String[] traffic = map.get("traffic");

        for (String s : run) {
            System.out.println(s);
        }
        for (String s : traffic) {
            System.out.println(s);
        }
    }


    @Test
    public void testReadFileByType(){
        Map<String, String[]> map = null;
        map = basicDataResolveService.readFileByType("metric-DCM.txt", "DCM");
        String[] dcm = map.get("DCM");
        for (String s : dcm) {
            System.out.println(s);
        }
    }


//    @Test
//    public void testResolveMetric(){
//        Map<String, List<Metric>> map = null;
//        map = basicDataResolveService.resolveMetric()
//    }


}
