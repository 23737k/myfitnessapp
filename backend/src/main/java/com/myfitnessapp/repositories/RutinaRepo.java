package com.myfitnessapp.repositories;

import com.myfitnessapp.model.rutina.Rutina;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RutinaRepo extends JpaRepository<Rutina, Integer> {
}
