package commons;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SpecialCharacterValidator implements ConstraintValidator<SpecialCharacter, String> {

	private Pattern specialCP;
	
	public SpecialCharacterValidator(){}
	
	@Override
	public void initialize(SpecialCharacter arg0){
		specialCP=Pattern.compile("[~#^$@.¡£%&!*]+");
	}
	
	@Override
	public boolean isValid(String str,ConstraintValidatorContext arg1){
		if (str==null||"".equals(str)){
			return false;
		}
		return !specialCP.matcher(str).matches();
	}


}
