package electrodynamics.api.configuration;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ByteValue {
	String comment() default "";

	byte def();
}
