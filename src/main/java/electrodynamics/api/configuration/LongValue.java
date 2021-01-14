package electrodynamics.api.configuration;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface LongValue {
	String comment() default "";

	long def();
}
