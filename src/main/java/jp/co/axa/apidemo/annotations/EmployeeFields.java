package jp.co.axa.apidemo.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;
import jp.co.axa.apidemo.validators.EmployeeFieldsValidator;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmployeeFieldsValidator.class)
public @interface EmployeeFields {
    String message() default "Either one of the fields (name, department or salary) must be provided, and must have a value";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}