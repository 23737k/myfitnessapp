package com.myfitnessapp;

import com.myfitnessapp.dominio.ejercicio.GrupoMuscular;
import com.myfitnessapp.repositories.GrupoMuscularRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class MyfitnessappApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyfitnessappApplication.class, args);
	}

	//Bootstrap
	@Bean
	@Transactional
	public CommandLineRunner commandLineRunner (GrupoMuscularRepo grupoMuscularRepo){
		return args -> {
			cargarGruposMusculares(grupoMuscularRepo);
		};
	}

	public void cargarGruposMusculares(GrupoMuscularRepo grupoMuscularRepo){
		List<String> gruposMusculares = List.of("Pectoral", "Triceps", "Dorsales", "Biceps",
				"Trapecios","Deltoides","Antebrazos", "Abdominales", "Cuadriceps", "Gemelos","Isquiotibiales", "Aductores");
		gruposMusculares.forEach(gm -> grupoMuscularRepo.save(new GrupoMuscular(gm)));
	}

}
