package ch.abertschi.sct.aspect;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import ch.abertschi.sct.api.Interceptor;
import ch.abertschi.sct.api.SctConfigurator;
import ch.abertschi.sct.api.ServiceCallTrackerFactory;
import ch.abertschi.sct.api.Configuration;
import ch.abertschi.sct.api.invocation.DefaultInvocationContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.abertschi.sct.api.invocation.InvocationContext;

/**
 * Base class to simplify call of sct.
 *
 * @author Andrin Bertschi
 */
public abstract class SctBaseIntercepting
{
    private static final Logger LOG = LoggerFactory.getLogger(SctBaseIntercepting.class);

    public Object aroundInvoke(final ProceedingJoinPoint method) throws Throwable
    {
        //LOG.info(String.format("AspectJ Around advice is intercepting call for %s", method.getSignature().toString()));

        Interceptor interc = createInterceptor(method);
        InvocationContext ctx = createContext(method, interc);
        return invoke(interc, ctx);
    }

    protected Interceptor createInterceptor(ProceedingJoinPoint method)
    {
        return ServiceCallTrackerFactory.lookupInterceptor(getSctConfiguration());
    }

    protected DefaultInvocationContext createContext(final ProceedingJoinPoint joinPoint, final Interceptor interceptor)
    {
        Callable<Object> proceed = new Callable<Object>()
        {
            @Override
            public Object call() throws Exception
            {
                try
                {
                    return joinPoint.proceed();
                }
                catch (Throwable e)
                {
                    throw new RuntimeException(e);
                }
            }
        };
        DefaultInvocationContext ctx = new DefaultInvocationContext(proceed);
        ctx.setParameters(joinPoint.getArgs());
        ctx.setProxy(joinPoint.getThis());
        ctx.setTarget(joinPoint.getTarget());
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        ctx.setMethod(method);
        return ctx;
    }

    protected Object invoke(Interceptor interceptor, InvocationContext ctx)
    {
        return interceptor.invoke(ctx);
    }

    protected Configuration getSctConfiguration()
    {
        return SctConfigurator.getInstance().getConfiguration();
    }
}
