package com.proyectorentacar.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.proyectorentacar.app.entity.Administrador;
import com.proyectorentacar.app.exception.NotFoundException;
import com.proyectorentacar.app.repository.AdministradorRepository;
import com.proyectorentacar.app.utilities.ListarAdministradoresPdf;


@Controller // Asegúrate de agregar la anotación @Controller
@RequestMapping("/administradores")
public class AdministradorTemplateController {
	@Autowired
	private AdministradorRepository administradorRepository;
	
	@Autowired
	private ListarAdministradoresPdf listarAdministradoresPdf;

	// metodos para trabajador

	@GetMapping("/")
	public String AdministradoresListByTrabajador(Model model) {
		model.addAttribute("administradores", administradorRepository.findAll());
		return "administradores-list-trabajador";
	}

	@GetMapping("/new")
	public String AdministradorNewByTrabajador(Model model) {
		model.addAttribute("administrador", new Administrador());
		return "administradores-form-trabajador";
	}

	@PostMapping("/save")
	public String AdministradorSaveByTrabajador(@ModelAttribute("administrador") Administrador administrador) {
		if (administrador.getId().isEmpty()) {
			administrador.setId(null);
		}

		if (administrador.getEstado().isEmpty()) {
			administrador.setEstado("Activo");
		}

		administradorRepository.save(administrador);
		return "redirect:/administradores/";
	}

	@GetMapping("/edit/{id}")
	public String AdministradorEditByTrabajador(@PathVariable("id") String id, Model model) {
		model.addAttribute("administrador", administradorRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Administrador no encontrado")));
		return "administradores-form-trabajador";
	}

	@GetMapping("/delete/{id}")
	public String AdministradorDeleteByTrabajador(@PathVariable("id") String id) {
		administradorRepository.deleteById(id);
		return "redirect:/administradores/";
	}

	// metodos para administrador

	@GetMapping("/lista")
	public String AdministradoresListByAdministrador(Model model) {
		model.addAttribute("administradores", administradorRepository.findAll());
		return "administradores-list-administrador";
	}

	@GetMapping("/nuevo")
	public String AdministradorNewByAdministrador(Model model) {
		model.addAttribute("administrador", new Administrador());
		return "administradores-form-administrador";
	}

	@PostMapping("/salvar")
	public String AdministradorSaveByAdministrador(@ModelAttribute("administrador") Administrador administrador) {
		if (administrador.getId().isEmpty()) {
			administrador.setId(null);
		}

		if (administrador.getEstado().isEmpty()) {
			administrador.setEstado("Activo");
		}

		administradorRepository.save(administrador);
		return "redirect:/administradores/lista";
	}

	@GetMapping("/editar/{id}")
	public String AdministradorEditByAdministrador(@PathVariable("id") String id, Model model) {
		model.addAttribute("administrador", administradorRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Administrador no encontrado")));
		return "administradores-form-administrador";
	}

	@GetMapping("/eliminar/{id}")
	public String AdministradorDeleteByAdministrador(@PathVariable("id") String id) {
		administradorRepository.deleteById(id);
		return "redirect:/administradores/lista";
	}
	
	// Metodo para generar Pdf
		@GetMapping("/pdf")
		public ModelAndView generarPdf() {
			ArrayList<Administrador> listadoAdministradores = (ArrayList<Administrador>) administradorRepository.findAll();

			// Crea el modelo con los datos que deseas pasar a la vista PDF
			Map<String, Object> model = new HashMap<>();
			model.put("administradores", listadoAdministradores);

			return new ModelAndView(listarAdministradoresPdf, model);
		}

}
