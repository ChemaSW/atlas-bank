package com.atlas.bank.atlas_bank.transaction.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

    //VALIDACIONES DE CONSISTENCIA
@Target(ElementType.TYPE) // TYPE - Se aplica a nivel de clase y no a nivel de campo individual o de un metodo
@Retention(RetentionPolicy.RUNTIME) // Hasta cuando vive la notación mientras la app corre
@Constraint(validatedBy = DifferentsAccountsValidator.class)     //Constraint ejecuta la lógica de validación // La clase contiene la lógica real que va a apoyar este nuevo decorador
public @interface DifferentsAccounts {

    String message() default "La cuenta de origen y destino, no puede ser la misma"; //texto que aparece cuando la validació falla
    Class<?>[] groups() default {}; //permite agrupar validaciones para ejecutar solo algunas segun el contexto
    Class<? extends Payload>[] payload() default {}; //Permite clasificar la severidad del error, advertencia, critico....


}
