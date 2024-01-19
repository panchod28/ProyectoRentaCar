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

import com.proyectorentacar.app.entity.Trabajador;
import com.proyectorentacar.app.exception.NotFoundException;
import com.proyectorentacar.app.repository.TrabajadorRepository;
import com.proyectorentacar.app.utilities.ListarTrabajadoresPdf;

@Controller // Asegúrate de agregar la anotación @Controller
@RequestMapping("/trabajadores")
public class TrabajadorTemplateController {
	@Autowired
	private TrabajadorRepository trabajadorRepository;
	
	@Autowired
	private ListarTrabajadoresPdf listarTrabajadoresPdf;

	// metodos para trabajador

	@GetMapping("/")
	public String TrabajadoresListByTrabajador(Model model) {
		model.addAttribute("trabajadores", trabajadorRepository.findAll());
		return "trabajadores-list-trabajador";
	}

	@GetMapping("/new")
	public String TrabajadorNewByTrabajador(Model model) {
		model.addAttribute("trabajador", new Trabajador());
		return "trabajadores-form-trabajador";
	}

	@PostMapping("/save")
	public String TrabajadorSaveByTrabajador(@ModelAttribute("trabajador") Trabajador trabajador) {
		if (trabajador.getId().isEmpty()) {
			trabajador.setId(null);
		}

		if (trabajador.getEstado().isEmpty()) {
			trabajador.setEstado("Activo");
		}

		trabajadorRepository.save(trabajador);
		return "redirect:/trabajadores/";
	}

	@GetMapping("/edit/{id}")
	public String TrabajadorEditByTrabajador(@PathVariable("id") String id, Model model) {
		model.addAttribute("trabajador",
				trabajadorRepository.findById(id).orElseThrow(() -> new NotFoundException("Trabajador no encontrado")));
		return "trabajadores-form-trabajador";
	}

	@GetMapping("/delete/{id}")
	public String TrabajadorDeleteByTrabajador(@PathVariable("id") String id) {
		trabajadorRepository.deleteById(id);
		return "redirect:/trabajadores/";
	}

	// metodos para administrador

	@GetMapping("/lista")
	public String TrabajadorListByAdministrador(Model model) {
		model.addAttribute("trabajadores", trabajadorRepository.findAll());
		return "trabajadores-list-administrador";
	}

	@GetMapping("/nuevo")
	public String TrabajadoreNewByAdministrador(Model model) {
		model.addAttribute("trabajador", new Trabajador());
		return "trabajadores-form-administrador";
	}

	@PostMapping("/salvar")
	public String TrabajadorSaveByAdministrador(@ModelAttribute("trabajador") Trabajador trabajador) {
		if (trabajador.getId().isEmpty()) {
			trabajador.setId(null);
		}

		if (trabajador.getEstado().isEmpty()) {
			trabajador.setEstado("Activo");
		}

		trabajadorRepository.save(trabajador);
		return "redirect:/trabajadores/lista";
	}

	@GetMapping("/editar/{id}")
	public String TrabajadorEditByAdministrador(@PathVariable("id") String id, Model model) {
		model.addAttribute("trabajador",
				trabajadorRepository.findById(id).orElseThrow(() -> new NotFoundException("Trabajador no encontrado")));
		return "trabajadores-form-administrador";
	}

	@GetMapping("/eliminar/{id}")
	public String TrabajadorDeleteByAdministrador(@PathVariable("id") String id) {
		trabajadorRepository.deleteById(id);
		return "redirect:/trabajadores/lista";
	}

	// Metodo para generar Pdf
	@GetMapping("/pdf")
	public ModelAndView generarPdf() {
		ArrayList<Trabajador> listadoTrabajadores = (ArrayList<Trabajador>) trabajadorRepository.findAll();

		// Crea el modelo con los datos que deseas pasar a la vista PDF
		Map<String, Object> model = new HashMap<>();
		model.put("trabajadores", listadoTrabajadores);

		return new ModelAndView(listarTrabajadoresPdf, model);
	}
}
