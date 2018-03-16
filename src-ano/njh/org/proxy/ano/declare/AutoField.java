package ma.org.proxy.ano.declare;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoField {
	
	public static final String Default_Mark = "...";
	
	public String name() default Default_Mark; 
	
}
