package com.proyectorentacar.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.proyectorentacar.app.entity.Vehiculo;

public interface VehiculoRepository extends MongoRepository<Vehiculo, String> {
	List<Vehiculo> findByEstado(String estado);
}
