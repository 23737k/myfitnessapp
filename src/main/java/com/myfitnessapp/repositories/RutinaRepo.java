package com.myfitnessapp.repositories;

import com.myfitnessapp.dominio.rutina.Rutina;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RutinaRepo extends JpaRepository<Rutina, Integer> {
}
