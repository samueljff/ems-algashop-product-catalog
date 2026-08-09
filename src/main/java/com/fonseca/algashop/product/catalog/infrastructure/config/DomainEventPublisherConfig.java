package com.fonseca.algashop.product.catalog.infrastructure.config;

import com.fonseca.algashop.product.catalog.domain.model.product.DomainEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainEventPublisherConfig {

    @Bean
    public DomainEventPublisher domainEventPublisher(
            ApplicationEventPublisher applicationEventPublisher
    ) {
        return applicationEventPublisher::publishEvent;
    }

}