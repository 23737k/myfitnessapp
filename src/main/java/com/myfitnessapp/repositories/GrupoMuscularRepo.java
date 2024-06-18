package com.myfitnessapp.repositories;

import com.myfitnessapp.dominio.ejercicio.GrupoMuscular;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoMuscularRepo extends JpaRepository<GrupoMuscular,Integer> {
}
