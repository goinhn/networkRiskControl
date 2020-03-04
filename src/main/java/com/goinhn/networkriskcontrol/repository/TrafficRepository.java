package com.goinhn.networkriskcontrol.repository;

import com.goinhn.networkriskcontrol.entity.Traffic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrafficRepository extends JpaRepository<Traffic, Long>, JpaSpecificationExecutor<Traffic> {

    @Query("from traffic where id = ?1")
    Traffic findTrafficById(Long id);

    @Query("from traffic where stime between ?1 and ?2")
    List<Traffic> findTrafficBySTimeBetween(int beginTime, int endTime);

}
