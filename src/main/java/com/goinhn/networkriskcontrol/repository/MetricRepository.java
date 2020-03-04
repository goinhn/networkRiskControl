package com.goinhn.networkriskcontrol.repository;

import com.goinhn.networkriskcontrol.entity.Metric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface MetricRepository extends JpaRepository<Metric, Long>, JpaSpecificationExecutor<Metric> {

    @Query("from metric where name = ?1")
    Metric findMetricByName(String name);
}
