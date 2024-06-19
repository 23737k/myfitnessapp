package com.myfitnessapp.repositories;

import com.myfitnessapp.dominio.rutina.ItemRutina;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRutinaRepo extends JpaRepository<ItemRutina, Integer> {
}
