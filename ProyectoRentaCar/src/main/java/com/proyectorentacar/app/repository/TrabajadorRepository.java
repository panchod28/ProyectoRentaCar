package com.proyectorentacar.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.proyectorentacar.app.entity.Trabajador;

public interface TrabajadorRepository extends MongoRepository<Trabajador, String>{

	Trabajador findByUsuario(String usuario);
	Trabajador findByContrasena(String contrasena);
	
}
