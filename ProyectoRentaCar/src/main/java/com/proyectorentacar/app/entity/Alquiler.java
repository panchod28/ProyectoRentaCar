package com.proyectorentacar.app.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import jakarta.validation.constraints.NotEmpty;


@Document(collection = "alquiler")
public class Alquiler {

	@Id
	private String id;
	
	@DocumentReference
	private Cliente cliente;
	
	@DocumentReference
	private Vehiculo vehiculo;
	
	@NotEmpty
	private String fechaRenta;
	
	@NotEmpty
	private String fechaDevolucion;
	
	@NotEmpty
	private String valor;
	
	@NotEmpty
	private String multa;
	
	@NotEmpty
	private String valorTotal;
	
	@NotEmpty
	private String estado;
	
	@NotEmpty
	private String observaciones;
	
	
	public Alquiler() {
		
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public Cliente getCliente() {
		return cliente;
	}


	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}


	public Vehiculo getVehiculo() {
		return vehiculo;
	}


	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}


	public String getFechaRenta() {
		return fechaRenta;
	}


	public void setFechaRenta(String fechaRenta) {
		this.fechaRenta = fechaRenta;
	}


	public String getFechaDevolucion() {
		return fechaDevolucion;
	}


	public void setFechaDevolucion(String fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
	}


	public String getValor() {
		return valor;
	}


	public void setValor(String valor) {
		this.valor = valor;
	}


	public String getMulta() {
		return multa;
	}


	public void setMulta(String multa) {
		this.multa = multa;
	}


	public String getValorTotal() {
		return valorTotal;
	}


	public void setValorTotal(String valorTotal) {
		this.valorTotal = valorTotal;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public String getObservaciones() {
		return observaciones;
	}


	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
	
	
}
