package com.proyectorentacar.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.proyectorentacar.app.entity.Cliente;

public interface ClienteRepository extends MongoRepository<Cliente, String>{
	Cliente findByCorreo(String correo);
	Cliente findByContrasena(String contrasena);
}
