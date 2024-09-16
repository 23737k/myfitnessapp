package com.myfitnessapp.repositories;

import com.myfitnessapp.dominio.ejercicio.Ejercicio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EjercicioRepo extends JpaRepository<Ejercicio, Integer> {
}
