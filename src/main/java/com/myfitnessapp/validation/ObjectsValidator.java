package com.myfitnessapp.validation;

import com.myfitnessapp.exceptions.ObjectNotValidException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ObjectsValidator<T> {
  private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
  private final Validator validator = validatorFactory.getValidator();

  public void validate(T object){
    //Recibo object y lo valido. Esto me retorna una lista de violaciones de constraints (@NotNull, @Max, @Size, etc)
    Set<ConstraintViolation<T>> violations = validator.validate(object);
    if(!violations.isEmpty()){
      //en caso de que no este vacia (haya violaciones), obtendré de ella una lista de cada uno de sus mensajes.
      var errorMessages = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());
      //lanzo una exception con esta lista de msjs como param. Esta, será capturada por GlobalExceptionHandler
      throw new ObjectNotValidException(errorMessages);
    }
  }
}
