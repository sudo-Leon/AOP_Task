package org.example.logging.config;

import org.example.logging.aspect.LoggingAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Авто-конфигурация для включения AOP-логирования HTTP-запросов.
 */
@Configuration
@EnableAspectJAutoProxy
@EnableConfigurationProperties(HttpLoggingProperties.class)
public class HttpLoggingAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "http.logging.enabled", havingValue = "true", matchIfMissing = true)
    public LoggingAspect loggingAspect(HttpLoggingProperties properties) {
        return new LoggingAspect(properties);
    }
}