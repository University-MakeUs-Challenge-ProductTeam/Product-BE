package umc.product.global.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeAspect {

    @Around("execution(* umc.product.domain.member.controller.member.MemberAuthController.signUp(..))")  // 패키지 및 메서드를 지정
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();  // 실행 시작 시간 기록

        Object proceed = joinPoint.proceed();  // 메서드 실행

        long endTime = System.currentTimeMillis();  // 실행 종료 시간 기록
        long executionTime = endTime - startTime;  // 실행 시간 계산

        // 로그에 실행 시간 출력
        System.out.println(joinPoint.getSignature() + " executed in " + executionTime + "ms");

        return proceed;
    }
}