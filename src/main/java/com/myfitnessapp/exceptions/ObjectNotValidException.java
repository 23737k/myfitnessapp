package com.myfitnessapp.exceptions;

import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ObjectNotValidException extends RuntimeException {
  private final Set<String> errorMessages;
}
