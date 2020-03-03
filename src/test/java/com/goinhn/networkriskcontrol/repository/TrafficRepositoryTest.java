package com.goinhn.networkriskcontrol.repository;

import com.goinhn.networkriskcontrol.NetworkriskcontrolApplication;
import com.goinhn.networkriskcontrol.entity.Traffic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = NetworkriskcontrolApplication.class)
public class TrafficRepositoryTest {

    @Autowired
    private TrafficRepository trafficRepository;

    @Test
    public void testFindTrafficById(){
        Traffic traffic = trafficRepository.findTrafficById(1L);
        System.out.println(traffic);
    }
}
