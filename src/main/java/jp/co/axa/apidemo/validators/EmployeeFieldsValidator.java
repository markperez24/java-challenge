package jp.co.axa.apidemo.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import jp.co.axa.apidemo.annotations.EmployeeFields;

public class EmployeeFieldsValidator implements ConstraintValidator<EmployeeFields, Object> {

	private String message;

    @Override
    public void initialize(EmployeeFields constraintAnnotation) {
        message = constraintAnnotation.message();
    }
    
	 /**
     * Checks if one of the not null value is provided in the DTO
     */
    @Override   
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        boolean hasExistingField = false;
        for (java.lang.reflect.Field field : value.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if (field.get(value) != null) {
                    hasExistingField = true;
                    break;
                }
            } catch (IllegalAccessException e) {
            }
        }

        if (!hasExistingField) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(this.message)
                    .addConstraintViolation();
        }

        return hasExistingField;
    }
}

