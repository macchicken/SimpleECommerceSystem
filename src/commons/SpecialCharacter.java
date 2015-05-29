package commons;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

/**
 * An annotation of special character checking
 * 
 * @author Barry
 * @version 1.0
 * @since 29/05/2015
 */
@Documented
@Constraint(validatedBy=SpecialCharacterValidator.class)
@ReportAsSingleViolation
@Target({ElementType.METHOD,ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface SpecialCharacter {

	String message() default "contains special character";
	
	Class<?>[] groups() default{};
	
	Class<? extends Payload>[] payload() default{}; 
	
}
