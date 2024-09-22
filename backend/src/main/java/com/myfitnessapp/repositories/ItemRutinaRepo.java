package com.myfitnessapp.repositories;

import com.myfitnessapp.model.rutina.ItemRutina;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRutinaRepo extends JpaRepository<ItemRutina, Integer> {
    @Transactional
    @Query(value = "SELECT * FROM item_rutina i WHERE i.rutina_id = :rutinaId ORDER BY i.item_order", nativeQuery = true)
    List<ItemRutina> findAllByRutina(@Param("rutinaId")Integer rutinaId);
}
