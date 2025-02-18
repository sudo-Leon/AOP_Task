package org.example.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* org.example.service.*.*(..))")
    public void logBeforeMethodCall(JoinPoint joinPoint) {
        System.out.println("Метод вызван: " + joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "execution(* org.example.service.*.*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        System.out.println("Метод " + joinPoint.getSignature().getName() + " завершился, результат: " + result);
    }

    @AfterThrowing(pointcut = "execution(* org.example.service.*.*(..))", throwing = "ex")
    public void logException(JoinPoint joinPoint, Throwable ex) {
        System.out.println("Ошибка в методе: " + joinPoint.getSignature().getName() + ", сообщение: " + ex.getMessage());
    }

    @Around("execution(* org.example.service.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed(); // Запуск метода
        long elapsedTime = System.currentTimeMillis() - start;
        System.out.println("Метод " + joinPoint.getSignature().getName() + " выполнен за " + elapsedTime + " мс");
        return result;
    }
}