package com.proyectorentacar.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.proyectorentacar.app.entity.Administrador;

public interface AdministradorRepository extends MongoRepository<Administrador, String>{
	
	Administrador findByUsuario(String usuario);
	Administrador findByContrasena(String contrasena);

}
