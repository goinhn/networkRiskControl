package com.goinhn.networkriskcontrol.repository;

import com.goinhn.networkriskcontrol.entity.Traffic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TrafficRepository extends JpaRepository<Traffic, Long>, JpaSpecificationExecutor<Traffic> {

    Traffic findTrafficById(Long id);
}
