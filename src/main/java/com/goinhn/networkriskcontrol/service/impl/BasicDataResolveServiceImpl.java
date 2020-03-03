package com.goinhn.networkriskcontrol.service.impl;

import com.goinhn.networkriskcontrol.repository.TrafficRepository;
import com.goinhn.networkriskcontrol.service.BasicDataResolveService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

@Service
public class BasicDataResolveServiceImpl implements BasicDataResolveService {

    @Autowired
    private TrafficRepository trafficRepository;

    /**
     * 配置自动事务
     */
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public SessionFactory getSessionFactory() {
        return entityManagerFactory.unwrap(SessionFactory.class);
    }


    @Override
    @Transactional
    public void search() {
    }
}
