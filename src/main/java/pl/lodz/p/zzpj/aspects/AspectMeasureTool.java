package pl.lodz.p.zzpj.aspects;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class AspectMeasureTool {
    Logger logger = Logger.getLogger(AspectMeasureTool.class);
    StopWatch stopWatch;

    @Pointcut("execution(* pl.lodz.p.zzpj.managers.ConverterNBP.convert(..))")
    public void convert() { }

    @Before("convert()")
    public void logBeforeConvert(JoinPoint joinPoint) {
        logger.info("Called method: " + joinPoint.getSignature().getName() + "...");
        stopWatch = new StopWatch();
        stopWatch.start();
    }

    @After("convert()")
    public void logAfterConvert(JoinPoint joinPoint) {
        stopWatch.stop();
        logger.info("Finished method: " + joinPoint.getSignature().getName() +
                ". It takes " + stopWatch.getLastTaskTimeMillis() + " ms.");
    }
}
