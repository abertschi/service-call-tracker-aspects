package ch.abertschi.sct.aspect;

import java.util.concurrent.Callable;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.abertschi.sct.SctInterceptor;
import ch.abertschi.sct.api.SctConfiguration;
import ch.abertschi.sct.api.SctConfigurator;
import ch.abertschi.sct.invocation.AspectjInvocationContext;
import ch.abertschi.sct.invocation.InvocationContext;

/**
 * Base class to simplify call of sct.
 *
 * @author Andrin Bertschi
 *
 */
public abstract class SctBaseIntercepting {

    private static final Logger LOG = LoggerFactory.getLogger(SctBaseIntercepting.class);

    public Object aroundInvoke(final ProceedingJoinPoint method) throws Throwable {
        LOG.info(String.format("AspectJ Around advice is intercepting call for %s", method.getSignature().toString()));

        SctInterceptor interc = createInterceptor(method);
        InvocationContext ctx = createContext(method, interc);
        return invoke(interc, ctx);
    }

    protected SctInterceptor createInterceptor(ProceedingJoinPoint method) {
        return new SctInterceptor(getSctConfiguration());
    }

    protected InvocationContext createContext(final ProceedingJoinPoint method, final SctInterceptor interceptor) {
        Callable<Object> proceed = new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                try {
                    return method.proceed();
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            }
        };

        AspectjInvocationContext ctx = new AspectjInvocationContext(proceed);
        ctx.setParameters(method.getArgs());
        ctx.setProxy(method.getThis());
        ctx.setTarget(method);
        ctx.setMethod(null);
        return ctx;
    }

    protected Object invoke(SctInterceptor interc, InvocationContext ctx) {
        return interc.invoke(ctx);
    }

    protected SctConfiguration getSctConfiguration() {
        return SctConfigurator.getInstance().getConfiguration();
    }
}
