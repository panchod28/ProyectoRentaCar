package com.proyectorentacar.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.proyectorentacar.app.entity.Alquiler;
import com.proyectorentacar.app.entity.Cliente;
import com.proyectorentacar.app.entity.Vehiculo;

public interface AlquilerRepository extends MongoRepository<Alquiler, String>{
	List<Alquiler> findByClienteAndEstado(Cliente cliente, String estado);
	Alquiler findByCliente(Cliente cliente);
	Alquiler findByVehiculo(Vehiculo vehiculo);
}
