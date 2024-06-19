package com.myfitnessapp.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class InvalidReferenceException extends RuntimeException{
  private String errorMessage;
}
