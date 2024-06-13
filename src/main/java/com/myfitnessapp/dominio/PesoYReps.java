package com.myfitnessapp.dominio;

public class PesoYReps implements TipoDeEjercicio{
  @Override
  public Serie crearSerie() {
    return new SeriePesoYReps();
  }
}
