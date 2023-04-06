package ru.pfr.contracts2.entity.annotations.valid;

import javax.validation.constraints.Pattern;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//@Constraint(validatedBy = ContactNumberValidator.class)
@Pattern(regexp = "^(Действующий)|(Исполнен)|(Расторгнут)|(Без статуса)|(^$)$")
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StatusGKValid {
    String message() default "Invalid StatusGK";
}
