package com.proyectorentacar.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyectorentacar.app.repository.VehiculoRepository;

@Controller
@RequestMapping("/home")
public class HomeTemplateController {

	@Autowired
	private VehiculoRepository vehiculoRepository;

	// Home para trabajador
	@GetMapping("/")
	public String homeByTrabajador(Model model) {
		model.addAttribute("vehiculos", vehiculoRepository.findAll());
		return "home-trabajador";
	}

	// Home para administrador
	@GetMapping("/principal")
	public String homeByAdministrador(Model model) {
		model.addAttribute("vehiculos", vehiculoRepository.findAll());
		return "home-administrador";
	}

	// Home para cliente
	@GetMapping("/hogar")
	public String homeByCliente(Model model) {
		model.addAttribute("vehiculos", vehiculoRepository.findAll());
		return "home-cliente";
	}

}
