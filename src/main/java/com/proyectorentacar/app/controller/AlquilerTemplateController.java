package com.proyectorentacar.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

import com.proyectorentacar.app.entity.Alquiler;
import com.proyectorentacar.app.entity.Cliente;
import com.proyectorentacar.app.entity.Vehiculo;
import com.proyectorentacar.app.exception.NotFoundException;
import com.proyectorentacar.app.repository.AlquilerRepository;
import com.proyectorentacar.app.repository.ClienteRepository;
import com.proyectorentacar.app.repository.VehiculoRepository;
import com.proyectorentacar.app.utilities.ListarAlquileresPdf;

import jakarta.servlet.http.HttpSession;

@Controller // Asegúrate de agregar la anotación @Controller
@RequestMapping("/alquileres")
public class AlquilerTemplateController {

	@Autowired
	private AlquilerRepository alquilerRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private VehiculoRepository vehiculoRepository;

	@Autowired
	private ListarAlquileresPdf listarAlquileresPdf;

	// metodos para trabajador

	@GetMapping("/")
	public String AlquileresListByTrabajador(Model model) {
		model.addAttribute("alquileres", alquilerRepository.findAll());
		return "alquileres-list-trabajador";
	}

	@GetMapping("/new")
	public String AlquilerNewByTrabajador(Model model, Model modelCliente, Model modelVehiculo) {
		List<Cliente> clientes = clienteRepository.findAll();
		List<Vehiculo> vehiculos = vehiculoRepository.findByEstado("Disponible");

		modelCliente.addAttribute("clientes", clientes);
		modelVehiculo.addAttribute("vehiculos", vehiculos);

		model.addAttribute("alquiler", new Alquiler());
		return "alquileres-form-trabajador";
	}

	@PostMapping("/save")
	public String AlquilerSaveByTrabajador(@ModelAttribute("alquiler") Alquiler alquiler, Model alerta,
			HttpSession session) {
		Vehiculo vehiculo = vehiculoRepository.findById(alquiler.getVehiculo().getId())
				.orElseThrow(() -> new NotFoundException("Vehiculo no encontrado"));
		Cliente cliente = clienteRepository.findById(alquiler.getCliente().getId())
				.orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
		List<Alquiler> alquileres = alquilerRepository.findByClienteAndEstado(cliente, "Activo");
		// Obten el cliente seleccionado en el formulario
		if (alquiler.getId().isEmpty()) {
			alquiler.setId(null);
		}

		if (alquiler.getEstado().isEmpty()) {
			alquiler.setEstado("Activo");
		}

		if (vehiculo.getEstado().equalsIgnoreCase("Disponible")) {
			vehiculo.setEstado("Ocupado");
		}

		if (alquiler.getEstado().equalsIgnoreCase("Finalizado")) {
			vehiculo.setEstado("Disponible");
		} else {
			vehiculo.setEstado("Ocupado");
		}

		// Verificar la variable de sesión para determinar si se está editando
		Boolean edicionEnCurso = (Boolean) session.getAttribute("edicionEnCurso");
		if (edicionEnCurso != null && edicionEnCurso) {
			vehiculoRepository.save(vehiculo);
			alquilerRepository.save(alquiler);
			// Eliminar la variable de sesión después de procesar
			session.removeAttribute("edicionEnCurso");
			return "redirect:/alquileres/";
		}

		if (!alquileres.isEmpty()) {
			alerta.addAttribute("alert", "El cliente tiene alquileres activos");
			System.out.println("Mensaje de alerta configurado: " + alerta.getAttribute("alert"));
			return "redirect:/alquileres/new?alert=El+cliente+tiene+alquileres+activos";
		}

		vehiculoRepository.save(vehiculo);
		alquilerRepository.save(alquiler);
		return "redirect:/alquileres/";
	}

	@GetMapping("/edit/{id}")
	public String AlquilerEditByTrabajador(@PathVariable("id") String id, HttpSession session, Model model,
			Model modelCliente, Model modelVehiculo) {
		List<Cliente> clientes = clienteRepository.findAll();
		List<Vehiculo> vehiculos = vehiculoRepository.findAll();

		modelCliente.addAttribute("clientes", clientes);
		modelVehiculo.addAttribute("vehiculos", vehiculos);

		session.setAttribute("edicionEnCurso", true);
		model.addAttribute("alquiler",
				alquilerRepository.findById(id).orElseThrow(() -> new NotFoundException("Alquiler no encontrado")));
		return "alquileres-form-trabajador";
	}

	@GetMapping("/delete/{id}")
	public String AlquilereDeleteByTrabajador(@PathVariable("id") String id) {
		Alquiler alquiler = alquilerRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Alquiler no encontrado"));
		Vehiculo vehiculo = vehiculoRepository.findById(alquiler.getVehiculo().getId())
				.orElseThrow(() -> new NotFoundException("Vehiculo no encontrado"));
		if (vehiculo != null) {
			vehiculo.setEstado("Disponible");
			vehiculoRepository.save(vehiculo);
		}
		alquilerRepository.deleteById(id);
		return "redirect:/alquileres/";
	}

	// metodos para administrador

	@GetMapping("/lista")
	public String AlquileresListByAdministrador(Model model) {
		model.addAttribute("alquileres", alquilerRepository.findAll());
		return "alquileres-list-administrador";
	}

	@GetMapping("/nuevo")
	public String AlquilerNewByAdministrador(Model modelAlquiler, Model modelCliente, Model modelVehiculo) {
		List<Cliente> clientes = clienteRepository.findAll();
		List<Vehiculo> vehiculos = vehiculoRepository.findByEstado("Disponible");

		modelCliente.addAttribute("clientes", clientes);
		modelVehiculo.addAttribute("vehiculos", vehiculos);

		modelAlquiler.addAttribute("alquiler", new Alquiler());
		return "alquileres-form-administrador";
	}

	@PostMapping("/salvar")
	public String AlquilerSaveByAdministrador(@ModelAttribute("alquiler") Alquiler alquiler, Model alerta,
			HttpSession session) {
		Vehiculo vehiculo = vehiculoRepository.findById(alquiler.getVehiculo().getId())
				.orElseThrow(() -> new NotFoundException("Vehiculo no encontrado"));
		Cliente cliente = clienteRepository.findById(alquiler.getCliente().getId())
				.orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
		List<Alquiler> alquileres = alquilerRepository.findByClienteAndEstado(cliente, "Activo");
		// Obten el cliente seleccionado en el formulario
		if (alquiler.getId().isEmpty()) {
			alquiler.setId(null);
		}

		if (alquiler.getEstado().isEmpty()) {
			alquiler.setEstado("Activo");
		}

		if (vehiculo.getEstado().equalsIgnoreCase("Disponible")) {
			vehiculo.setEstado("Ocupado");
		}

		if (alquiler.getEstado().equalsIgnoreCase("Finalizado")) {
			vehiculo.setEstado("Disponible");
		} else {
			vehiculo.setEstado("Ocupado");
		}

		// Verificar la variable de sesión para determinar si se está editando
		Boolean edicionEnCurso = (Boolean) session.getAttribute("edicionEnCurso");
		if (edicionEnCurso != null && edicionEnCurso) {
			vehiculoRepository.save(vehiculo);
			alquilerRepository.save(alquiler);
			// Eliminar la variable de sesión después de procesar
			session.removeAttribute("edicionEnCurso");
			return "redirect:/alquileres/lista";
		}

		if (!alquileres.isEmpty()) {
			alerta.addAttribute("alerta", "El cliente tiene alquileres activos");
			System.out.println("Mensaje de alerta configurado: " + alerta.getAttribute("alerta"));
			return "redirect:/alquileres/nuevo?alerta=El+cliente+tiene+alquileres+activos";
		}

		vehiculoRepository.save(vehiculo);
		alquilerRepository.save(alquiler);
		return "redirect:/alquileres/lista";
	}

	@GetMapping("/editar/{id}")
	public String AlquilerEditByAdministrador(@PathVariable("id") String id, HttpSession session, Model model,
			Model modelCliente, Model modelVehiculo) {
		List<Cliente> clientes = clienteRepository.findAll();
		List<Vehiculo> vehiculos = vehiculoRepository.findAll();

		modelCliente.addAttribute("clientes", clientes);
		modelVehiculo.addAttribute("vehiculos", vehiculos);

		session.setAttribute("edicionEnCurso", true);
		model.addAttribute("alquiler",
				alquilerRepository.findById(id).orElseThrow(() -> new NotFoundException("Alquiler no encontrado")));
		return "alquileres-form-administrador";
	}

	@GetMapping("/eliminar/{id}")
	public String AlquilerDeleteByAdministrador(@PathVariable("id") String id) {
		Alquiler alquiler = alquilerRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Alquiler no encontrado"));
		Vehiculo vehiculo = vehiculoRepository.findById(alquiler.getVehiculo().getId())
				.orElseThrow(() -> new NotFoundException("Vehiculo no encontrado"));
		if (vehiculo != null) {
			vehiculo.setEstado("Disponible");
			vehiculoRepository.save(vehiculo);
		}
		alquilerRepository.deleteById(id);
		return "redirect:/alquileres/lista";
	}

	// Metodo para generar Pdf
	@GetMapping("/pdf")
	public ModelAndView generarPdf() {
		ArrayList<Alquiler> listadoAlquileres = (ArrayList<Alquiler>) alquilerRepository.findAll();

		// Crea el modelo con los datos que deseas pasar a la vista PDF
		Map<String, Object> model = new HashMap<>();
		model.put("alquileres", listadoAlquileres);

		return new ModelAndView(listarAlquileresPdf, model);
	}
}
