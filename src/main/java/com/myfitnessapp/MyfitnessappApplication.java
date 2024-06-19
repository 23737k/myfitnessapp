package com.myfitnessapp;

import com.myfitnessapp.utils.Bootstrap;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MyfitnessappApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyfitnessappApplication.class, args);
	}

	//Bootstrap
	@Bean
	public CommandLineRunner commandLineRunner (Bootstrap bootstrap){
		return args -> {
			//Carga inicial de la bd
			bootstrap.init();
		};
	}

}
