package com.goinhn.networkriskcontrol.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@Data
@Entity
@Table(name = "metric", schema = "riskcontrol")
public class Metric implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;                 //编号

    @Column(name = "name")
    private String name;            //指标的名称

    @Column(name = "description")
    private String description;     //指标的描述

    @Column(name = "type")
    private String type;            //指标类型

    @Column(name = "fields")
    private String fields;          //相关字段

    @Column(name = "rule")
    private String rule;            //风险定义规则
}
