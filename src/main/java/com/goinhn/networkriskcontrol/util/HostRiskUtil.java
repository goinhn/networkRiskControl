package com.goinhn.networkriskcontrol.util;

import com.goinhn.networkriskcontrol.entity.*;

import java.util.ArrayList;
import java.util.List;

public class HostRiskUtil {
    /**
     * 生成脆弱指数
     * @return
     */
    public static List<Vulnerability> generateVulnerabilityData() {//模拟生成一些漏洞数据
        List<Vulnerability> vulnerabilities = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            //设备的资产重要性，评估得分为[0,10]，由专家或经验确定
            Vulnerability v = new Vulnerability();
            v.setAsset(5);

            //设备中漏洞对应的CVE-ID
            List<String> cveid = new ArrayList<>();
            cveid.add("CVE-2019-0001");
            cveid.add("CVE-2019-0002");
            v.setCveid(cveid);

            //设备中漏洞对应的CVSS评分
            List<Double> cvss = new ArrayList<>();
            cvss.add(5.6);
            cvss.add(7.8);
            v.setScore(cvss);

            vulnerabilities.add(v);
        }

        return vulnerabilities;
    }


    public static List<Run> generateRunData() {//模拟生成一些运行指数数据
        List<Run> runs = new ArrayList<Run>();

        for (int i = 0; i < 5; i++) {
            Run r = new Run();
            r.setCpu(0.7);
            r.setMemory(0.8);
            r.setBandwidth(0.9);
            runs.add(r);
        }

        return runs;
    }

    //指标的带宽利用率
    public static List<DCM> generateDCMData() {//模拟生成一些运行指数数据
        List<DCM> dcms = new ArrayList<DCM>();
        for (int i = 0; i < 5; i++) {
            DCM dcm = new DCM();
            dcm.setDcm0201(0.5);
            dcm.setDcm0301(0.5);
            dcm.setDcm0401(0.5);
            dcm.setDcm0402(0.5);
            dcms.add(dcm);
        }
        return dcms;
    }

    public static List<DIM> generateDIMData() {//模拟生成一些运行指数数据
        List<DIM> dims = new ArrayList<DIM>();
        for (int i = 0; i < 5; i++) {
            DIM dim = new DIM();
            dim.setDim0101(0.5);
            dim.setDim0201(0.5);
            dim.setDim0301(0.5);
            dims.add(dim);
        }
        return dims;
    }

    public static List<MCM> generateMCMData() {//模拟生成一些运行指数数据
        List<MCM> mcms = new ArrayList<MCM>();
        for (int i = 0; i < 5; i++) {
            MCM mcm = new MCM();
            mcm.setMcm0101(0.5);
            mcm.setMcm0102(0.5);
            mcm.setMcm0202(0.5);
            mcm.setMcm0203(0.5);
            mcms.add(mcm);
        }
        return mcms;
    }

    public static List<PCM> generatePCMData() {//模拟生成一些运行指数数据
        List<PCM> pcms = new ArrayList<PCM>();
        for (int i = 0; i < 5; i++) {
            PCM pcm = new PCM();
            pcm.setPcm0101(0.5);
            pcm.setPcm0201(0.5);
            pcm.setPcm0301(0.5);
            pcm.setPcm0302(0.5);
            pcm.setPcm0401(0.5);
            pcm.setPcm0402(0.5);
            pcm.setPcm0403(0.5);
            pcm.setPcm0404(0.5);
            pcm.setPcm0501(0.5);
            pcm.setPcm0502(0.5);
            pcm.setPcm0601(0.5);
            pcm.setPcm0603(0.5);
            pcms.add(pcm);
        }
        return pcms;
    }

    public static List<PIM> generatePIMData() {//模拟生成一些运行指数数据
        List<PIM> pims = new ArrayList<PIM>();
        for (int i = 0; i < 5; i++) {
            PIM pim = new PIM();
            pim.setPim0101(0.5);
            pim.setPim0102(0.5);
            pims.add(pim);
        }
        return pims;
    }

    public static List<DEM> generateDEMData() {//模拟生成一些运行指数数据
        List<DEM> dems = new ArrayList<DEM>();
        for (int i = 0; i < 5; i++) {
            DEM dem = new DEM();
            dem.setDem0101(0.5);
            dem.setDem0201(0.5);
            dem.setDem0302(0.5);
            dems.add(dem);
        }
        return dems;
    }
}
