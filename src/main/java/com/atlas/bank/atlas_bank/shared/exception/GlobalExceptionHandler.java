package com.atlas.bank.atlas_bank.shared.exception;

import com.atlas.bank.atlas_bank.account.exception.AccountNotFoundException;
import com.atlas.bank.atlas_bank.transaction.exception.AccountNotActiveException;
import com.atlas.bank.atlas_bank.transaction.exception.InsufficientFundsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

//Clase que captura todas las excepciones que aparezcan en nuestro sistema de forma centralizada
@RestControllerAdvice
public class GlobalExceptionHandler {

    //Metodos que atrapan los diferentes tipos de Excepciones

        //LOS ERRORES DE NEGOCIO SE EXPLICAN, PERO LOS ERRORES DE SISTEMA SE OCULTAN

    @ExceptionHandler(AccountNotFoundException.class)
    public ProblemDetail handleAccountNotFound(AccountNotFoundException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND, ex.getMessage()
        );

        problem.setTitle("Cuenta no encontrada");
        return problem;
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ProblemDetail handleInsufficientFunds(InsufficientFundsException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatusCode.valueOf(422), ex.getMessage() //abstracción de codigo HTTP
        );

        problem.setTitle("Fondos insuficientes");
        return problem;
    }

    @ExceptionHandler(AccountNotActiveException.class)
    public ProblemDetail handleAccountNotActive(AccountNotActiveException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage() //o podemos usar abstracción de codigo HTTP, en este caso seria otro codigo 422
        );

        problem.setTitle("Cuenta no activa");
        return problem;
    }

    //Que pasa si tenemos un error que no es ninguo de los anteriores?
        //Lo atrapos de forma generica
    @ExceptionHandler(Exception.class)
    public ProblemDetail handlerGeneral(Exception ex){
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()
        );

        problem.setTitle("Error interno");
        return problem;
    }

        //Excepcion que gestiona las validaciones
    @ExceptionHandler(MethodArgumentNotValidException.class) //Si falla algo se dispara esta clase, donde atrapa y guardara los errores en una lista
    public ProblemDetail handlerGeneral(MethodArgumentNotValidException ex){
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Error de validación");

        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                                        //campo del error           //mesnaje de error en validaciones
                .map(error -> error.getField() + ": "+ error.getDefaultMessage()) //mapeamos el mensaje de error
                .toList();

        problem.setProperty("errors", errors); //seteo de property, para setear el listado de errores

        return problem;
    }


}
