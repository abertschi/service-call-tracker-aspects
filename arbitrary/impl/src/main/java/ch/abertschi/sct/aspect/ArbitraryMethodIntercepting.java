package ch.abertschi.sct.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * Aspect to intercept arbitrary methods annotated with an annotation.
 *
 * @author Andrin Bertschi
 */
@Aspect
public class ArbitraryMethodIntercepting extends SctBaseIntercepting
{
    @Override
    @Around("call(@ch.abertschi.sct.aspect.SctInterceptForTest * *(..))")
    public Object aroundInvoke(final ProceedingJoinPoint method) throws Throwable
    {
        return super.aroundInvoke(method);
    }

    @Around("call(@ch.abertschi.sct.aspect.RecordAndReplay * *(..))")
    public Object recordAndReplay(final ProceedingJoinPoint method) throws Throwable
    {
        return super.aroundInvoke(method);
    }
}

