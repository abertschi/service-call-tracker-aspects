package ch.abertschi.sct.aspect;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.CLASS;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Intercepting methods or classes marked with this Annotation in a
 * system-under-test with the service-call-tracker.
 *
 * @author Andrin Bertschi
 * @since 2015-05
 */
@Target(
        {METHOD})
@Retention(RUNTIME)
@Documented
@Inherited
public @interface SctInterceptForTest
{
}
