package com.goinhn.networkriskcontrol.util;

import com.goinhn.networkriskcontrol.entity.Countermeasure;
import com.goinhn.networkriskcontrol.entity.Device;

import java.util.ArrayList;
import java.util.List;

public class StrategyUtil {
    public static List<Countermeasure> generateCountermeasureListByName(String countermeasureNameStr, Device device) {
        String[] countermeasureNameArr = countermeasureNameStr.split(",");
        List<Countermeasure> countermeasures = new ArrayList<>();

        for (String countermeasureName : countermeasureNameArr) {
            Countermeasure newCountermeasure = new Countermeasure();
            newCountermeasure.setName(countermeasureName);
            newCountermeasure.setDevice(device);
            countermeasures.add(newCountermeasure);
        }
        return countermeasures;
    }
}
