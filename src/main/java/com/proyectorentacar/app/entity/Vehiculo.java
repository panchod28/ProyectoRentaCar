package com.proyectorentacar.app.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotEmpty;

@Document(collection = "vehiculo")
public class Vehiculo {
	
	@Id
	private String id;
	
	@NotEmpty
	private String identificador;
	
	@NotEmpty
	private String tipo;
	
	@NotEmpty
	private String modelo;
	
	@NotEmpty
	private String  marca;
	
	@NotEmpty
	private String ano;
	
	@NotEmpty
	private String descripcion;
	
	@NotEmpty
	private String valorDia;
	
	@NotEmpty
	private String estado;
	
	@NotEmpty
	private String imagen;
	
	
	public Vehiculo(){
		
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getIdentificador() {
		return identificador;
	}


	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public String getModelo() {
		return modelo;
	}


	public void setModelo(String modelo) {
		this.modelo = modelo;
	}


	public String getMarca() {
		return marca;
	}


	public void setMarca(String marca) {
		this.marca = marca;
	}


	public String getAno() {
		return ano;
	}


	public void setAno(String ano) {
		this.ano = ano;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public String getValorDia() {
		return valorDia;
	}


	public void setValorDia(String valorDia) {
		this.valorDia = valorDia;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public String getImagen() {
		return imagen;
	}


	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	
	
	
}
