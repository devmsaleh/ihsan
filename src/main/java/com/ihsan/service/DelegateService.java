package com.ihsan.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ihsan.dao.DelegateRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class DelegateService {

    private static final Logger logger = LoggerFactory.getLogger(DelegateService.class);

    @Autowired
    private DelegateRepository delegateRepository;

}
