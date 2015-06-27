package commons;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Implemented validator of an annotation of special character checking
 * 
 * @author Barry
 * @version 1.0
 * @since 29/05/2015
 */
public class SpecialCharacterValidator implements ConstraintValidator<SpecialCharacter, String> {

	/**
	 * regular expression in this validator
	 */
	private Pattern specialCP;
	
	public SpecialCharacterValidator(){}
	
	/**
	 * initialization of this validator
	 */
	@Override
	public void initialize(SpecialCharacter arg0){
		specialCP=Pattern.compile("\\s*[~#^$@.¡£%&!*]+\\s*");
	}
	
	/**
	 * checking procedure of this validator
	 * @param str - a string to be checked
	 * @param arg1
	 * @return boolean - return true if valid
	 */
	@Override
	public boolean isValid(String str,ConstraintValidatorContext arg1){
		if (str==null||"".equals(str)){
			return true;
		}
		return !specialCP.matcher(str).matches();
	}


}
