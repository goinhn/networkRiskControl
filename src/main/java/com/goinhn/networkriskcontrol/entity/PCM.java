package com.goinhn.networkriskcontrol.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class PCM implements Serializable {

    private double pcm0101;

    private double pcm0201;

    private double pcm0301;

    private double pcm0302;

    private double pcm0401;

    private double pcm0402;

    private double pcm0403;

    private double pcm0404;

    private double pcm0501;

    private double pcm0502;

    private double pcm0601;

    private double pcm0603;

    public double get(String name) {
        switch (name) {
            case "PCM-01-01":
                return this.getPcm0101();
            case "PCM-02-01":
                return this.getPcm0201();
            case "MCM-03-01":
                return this.getPcm0301();
            case "MCM-03-02":
                return this.getPcm0302();
            case "PCM-04-01":
                return this.getPcm0401();
            case "PCM-04-02":
                return this.getPcm0402();
            case "PCM-04-03":
                return this.getPcm0403();
            case "PCM-04-04":
                return this.getPcm0404();
            case "PCM-05-01":
                return this.getPcm0501();
            case "PCM-05-02":
                return this.getPcm0502();
            case "PCM-06-01":
                return this.getPcm0601();
            case "PCM-06-03":
                return this.getPcm0603();
            default:
                return 0;
        }
    }
}
