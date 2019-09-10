package com.ihsan;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import com.ihsan.webservice.HAIService;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.config.SwaggerConfigLocator;
import io.swagger.jaxrs.config.SwaggerContextService;
import io.swagger.jaxrs.listing.ApiListingResource;

@Configuration
@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		register(HAIService.class);
		// register(SponsorShipWebService.class);

		BeanConfig swaggerConfig = new BeanConfig();
		swaggerConfig.setBasePath("/ihsan/api");
		SwaggerConfigLocator.getInstance().putConfig(SwaggerContextService.CONFIG_ID_DEFAULT, swaggerConfig);

		packages(getClass().getPackage().getName(), ApiListingResource.class.getPackage().getName());
	}

}