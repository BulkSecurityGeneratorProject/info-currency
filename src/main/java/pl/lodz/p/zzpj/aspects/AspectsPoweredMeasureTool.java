package pl.lodz.p.zzpj.aspects;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * Measures time around currency converting method. Uses aspect oriented programming.
 */
@Aspect
@Component
public class AspectsPoweredMeasureTool {
    private Logger logger = Logger.getLogger(AspectsPoweredMeasureTool.class);
    private StopWatch stopWatch;

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
