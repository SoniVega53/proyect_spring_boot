package com.example.demo;

import com.example.demo.model.UserModel;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class ProyectoFinalApplication  implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(ProyectoFinalApplication.class, args);
	}

	@Autowired
	private UserRepository repository;

	@Override
	public void run(String... args) throws Exception {
		createAdmin();
	}

	private void createAdmin(){
		List<UserModel> useLis = repository.findAll();

		if (useLis.size() < 1){
			repository.save(new UserModel(
					"admin","admin","Soni Javier","Vega Muñoz",
					"37902058","3190-22-9368","Universidad Mariano Gálvez","Programación 2","admin"));
		}


		//todo: ROLES = admin y user
	}
}
