package com.ihs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartUpInit {

    private static final Logger logger = LoggerFactory.getLogger(StartUpInit.class);

    @EventListener
    public void onApplicationReady(ApplicationReadyEvent ready) {
        // logger.info("######### StartUpInit onApplicationReady #########");
        // cacheService.refreshAllCaches();
    }

}