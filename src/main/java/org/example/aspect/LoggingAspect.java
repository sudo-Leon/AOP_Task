package org.example.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* org.example.service.*.*(..))")
    public void logBeforeMethodCall(JoinPoint joinPoint) {
        logger.info("Метод вызван: {}", joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "execution(* org.example.service.*.*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("Метод {} завершился, результат: {}", joinPoint.getSignature().getName(), result);
    }

    @AfterThrowing(pointcut = "execution(* org.example.service.*.*(..))", throwing = "ex")
    public void logException(JoinPoint joinPoint, Throwable ex) {
        logger.error("Ошибка в методе {}: {}", joinPoint.getSignature().getName(), ex.getMessage());
    }

    @Around("execution(* org.example.service.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long elapsedTime = System.currentTimeMillis() - start;
            logger.info("Метод {} выполнен за {} мс", joinPoint.getSignature().getName(), elapsedTime);
            return result;
        } catch (Throwable ex) {
            logger.error("Ошибка выполнения метода {}: {}", joinPoint.getSignature().getName(), ex.getMessage(), ex);
            throw ex; // Пробрасываем исключение без измнения типа
        }
    }
}