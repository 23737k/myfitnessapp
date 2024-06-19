package com.myfitnessapp.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SerieNoValidaException extends RuntimeException{
  private String errorMessage;
}
