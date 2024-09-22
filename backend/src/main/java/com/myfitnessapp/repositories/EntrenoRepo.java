package com.myfitnessapp.repositories;

import com.myfitnessapp.model.entreno.Entreno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntrenoRepo extends JpaRepository<Entreno, Integer>{
}
