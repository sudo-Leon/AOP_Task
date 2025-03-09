package org.example.logging.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.logging.annotation.LogHttp;
import org.example.logging.config.HttpLoggingProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * AOP-аспект для логирования HTTP-запросов и их выполнения.
 * Логируются только методы, аннотированные @LogHttp.
 */
@Aspect
@Component
public class LoggingAspect {
    private final HttpLoggingProperties properties;
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    public LoggingAspect(HttpLoggingProperties properties) {
        this.properties = properties;
    }

    @Around("@annotation(logHttp)")
    public Object logExecution(ProceedingJoinPoint joinPoint, LogHttp logHttp) throws Throwable {
        if (!properties.isEnabled()) {
            return joinPoint.proceed();
        }

        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long elapsedTime = System.currentTimeMillis() - start;

        switch (properties.getLevel().toLowerCase()) {
            case "debug":
                logger.debug("Метод {} выполнен за {} мс", joinPoint.getSignature().getName(), elapsedTime);
                break;
            case "warn":
                logger.warn("Метод {} выполнен за {} мс", joinPoint.getSignature().getName(), elapsedTime);
                break;
            case "error":
                logger.error("Метод {} выполнен за {} мс", joinPoint.getSignature().getName(), elapsedTime);
                break;
            default:
                logger.info("Метод {} выполнен за {} мс", joinPoint.getSignature().getName(), elapsedTime);
                break;
        }

        return result;
    }
}