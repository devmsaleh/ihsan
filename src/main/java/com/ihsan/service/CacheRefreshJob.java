package com.ihsan.service;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ihsan.dao.TokenRepository;

@Component
public class CacheRefreshJob {

	private static final Logger logger = LoggerFactory.getLogger(CacheRefreshJob.class);

	@Autowired
	private CacheService cacheService;

	@Autowired
	private TokenRepository tokenRepository;

	// each 1 hour
	// fixedRate is in milleseonds to get values in minutes 60 * 1000 * number of
	// minutes
	// one hour = 3600000
	@Scheduled(fixedRate = 3600000 * 1, initialDelay = 0)
	public void refreshCache() {
		try {
			logger.info("######## IHSAN CacheRefreshJob at: " + new Date());
			cacheService.refreshAllCaches();
			tokenRepository.deleteOldTokens(DateUtils.addWeeks(new Date(), -1));
		} catch (Exception e) {
			logger.error("Exception in IHSAN CacheRefreshJob", e);
		}
	}

}
