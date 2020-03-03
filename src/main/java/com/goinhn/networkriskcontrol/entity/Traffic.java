package com.goinhn.networkriskcontrol.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "traffic", schema = "riskcontrol")
public class Traffic implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "srcip")
    private String srcIp;

    @Column(name = "srcport")
    private Integer srcPort;

    @Column(name = "dstip")
    private String dstIp;

    @Column(name = "dstport")
    private Integer dstPort;

    @Column(name = "proto")
    private String proto;

    @Column(name = "dur")
    private Double dur;

    @Column(name = "sbytes")
    private Integer sBytes;

    @Column(name = "dbytes")
    private Integer dBytes;

    @Column(name = "service")
    private String service;

    @Column(name = "sload")
    private Double sLoad;

    @Column(name = "dload")
    private Double dLoad;

    @Column(name = "spkts")
    private Integer spkts;

    @Column(name = "dpkts")
    private Integer dpkts;

    @Column(name = "smeansz")
    private Integer smeansz;

    @Column(name = "dmeansz")
    private Integer dmeansz;

    @Column(name = "stime")
    private Integer sTime;

    @Column(name = "ltime")
    private Integer lTime;

    @Column(name = "attack_cat")
    private String attackCat;

    @Column(name = "label")
    private Integer label;
}