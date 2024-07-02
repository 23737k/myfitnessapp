package com.myfitnessapp.repositories;

import com.myfitnessapp.dominio.entreno.Entreno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntrenoRepo extends JpaRepository<Entreno, Integer>{
}
