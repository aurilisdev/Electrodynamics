package electrodynamics.api.configuration;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface StringValue {
	String comment() default "";

	String def();
}
