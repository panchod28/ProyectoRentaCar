package com.proyectorentacar.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyectorentacar.app.repository.VehiculoRepository;

@Controller
@RequestMapping("/catalogo")
public class CatalogoTemplateController {

	@Autowired
	private VehiculoRepository vehiculoRepository;

	@GetMapping("/administrador")
	public String CatalogoByAdministrador(Model model) {
		model.addAttribute("vehiculos", vehiculoRepository.findAll());
		return "catalogo-administrador";
	}

	@GetMapping("/trabajador")
	public String CatalogoByTrabajador(Model model) {
		model.addAttribute("vehiculos", vehiculoRepository.findAll());
		return "catalogo-trabajador";
	}

	@GetMapping("/cliente")
	public String CatalogoByCliente(Model model) {
		model.addAttribute("vehiculos", vehiculoRepository.findAll());
		return "catalogo-cliente";
	}
}