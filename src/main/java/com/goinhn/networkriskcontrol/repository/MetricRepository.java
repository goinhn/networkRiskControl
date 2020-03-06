package com.goinhn.networkriskcontrol.repository;

import com.goinhn.networkriskcontrol.entity.Metric;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MetricRepository extends JpaRepository<Metric, Integer>, JpaSpecificationExecutor<Metric> {

//    @Query("from metric where name = :name")
    Metric findMetricByName(String name);
}
