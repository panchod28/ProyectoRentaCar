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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.proyectorentacar.app.entity.Alquiler;
import com.proyectorentacar.app.entity.Cliente;
import com.proyectorentacar.app.entity.Vehiculo;
import com.proyectorentacar.app.exception.NotFoundException;
import com.proyectorentacar.app.repository.AlquilerRepository;
import com.proyectorentacar.app.repository.ClienteRepository;
import com.proyectorentacar.app.repository.VehiculoRepository;
import com.proyectorentacar.app.utilities.ListarClientesPdf;

@Controller // Asegúrate de agregar la anotación @Controller
@RequestMapping("/clientes")
public class ClienteTemplateController {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private AlquilerRepository alquilerRepository;

	@Autowired
	private VehiculoRepository vehiculoRepository;

	@Autowired
	private ListarClientesPdf listarClientesPdf;

	// metodos para trabajador

	@GetMapping("/")
	public String ClientesListByTrabajador(Model model) {
		model.addAttribute("clientes", clienteRepository.findAll());
		return "clientes-list-trabajador";
	}

	@GetMapping("/new")
	public String ClienteNewByTrabajador(Model model) {
		model.addAttribute("cliente", new Cliente());
		return "clientes-form-trabajador";
	}

	@PostMapping("/save")
	public String ClienteSaveByTrabajador(@ModelAttribute("cliente") Cliente cliente) {
		if (cliente.getId().isEmpty()) {
			cliente.setId(null);
		}

		if (cliente.getEstado().isEmpty()) {
			cliente.setEstado("Activo");
		}

		clienteRepository.save(cliente);
		return "redirect:/clientes/";
	}

	@GetMapping("/edit/{id}")
	public String ClienteEditByTrabajador(@PathVariable("id") String id, Model model) {
		model.addAttribute("cliente",
				clienteRepository.findById(id).orElseThrow(() -> new NotFoundException("Cliente no encontrado")));
		return "clientes-form-trabajador";
	}

	@GetMapping("/delete/{id}")
	public String ClienteDeleteByTrabajador(@PathVariable("id") String id) {
		Cliente cliente = clienteRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
		Alquiler alquiler = alquilerRepository.findByCliente(cliente);
		if (alquiler != null) {
			Vehiculo vehiculo = vehiculoRepository.findById(alquiler.getVehiculo().getId())
					.orElseThrow(() -> new NotFoundException("Vehiculo no encontrado"));
			vehiculo.setEstado("Disponible");
			vehiculoRepository.save(vehiculo);
			alquilerRepository.deleteById(alquiler.getId());
		}
		clienteRepository.deleteById(id);
		return "redirect:/clientes/";
	}

	// metodos para administrador

	@GetMapping("/lista")
	public String ClientesListByAdministrador(Model model) {
		model.addAttribute("clientes", clienteRepository.findAll());
		return "clientes-list-administrador";
	}

	@GetMapping("/nuevo")
	public String ClienteNewByAdministrador(Model model) {
		model.addAttribute("cliente", new Cliente());
		return "clientes-form-administrador";
	}

	@PostMapping("/salvar")
	public String ClienteSaveByAdministrador(@ModelAttribute("cliente") Cliente cliente) {
		if (cliente.getId().isEmpty()) {
			cliente.setId(null);
		}

		if (cliente.getEstado().isEmpty()) {
			cliente.setEstado("Activo");
		}

		clienteRepository.save(cliente);
		return "redirect:/clientes/lista";
	}

	@PostMapping("/salvar/registro")
	public String ClienteRegistro(@ModelAttribute("cliente") Cliente cliente) {
		if (cliente.getId().isEmpty()) {
			cliente.setId(null);
		}

		if (cliente.getEstado().isEmpty()) {
			cliente.setEstado("Activo");
		}

		clienteRepository.save(cliente);
		return "redirect:/login/";
	}

	@GetMapping("/editar/{id}")
	public String ClienteEditByAdministrador(@PathVariable("id") String id, Model model) {
		model.addAttribute("cliente",
				clienteRepository.findById(id).orElseThrow(() -> new NotFoundException("Cliente no encontrado")));
		return "clientes-form-administrador";
	}

	@GetMapping("/eliminar/{id}")
	public String ClienteDeleteByAdministrador(@PathVariable("id") String id) {
		Cliente cliente = clienteRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
		Alquiler alquiler = alquilerRepository.findByCliente(cliente);
		if (alquiler != null) {
			Vehiculo vehiculo = vehiculoRepository.findById(alquiler.getVehiculo().getId())
					.orElseThrow(() -> new NotFoundException("Vehiculo no encontrado"));
			vehiculo.setEstado("Disponible");
			vehiculoRepository.save(vehiculo);
			alquilerRepository.deleteById(alquiler.getId());
		}
		clienteRepository.deleteById(id);
		return "redirect:/clientes/lista";
	}

	// metodos para logeo y registro

	@GetMapping("/registro")
	public String registroTemplate(Model model) {
		model.addAttribute("cliente", new Cliente());
		return "registro-cliente";
	}

	@PostMapping("/guardar")
	public String ClienteGuardarByAdministrador(@ModelAttribute("cliente") Cliente cliente) {
		if (cliente.getId().isEmpty()) {
			cliente.setId(null);
		}

		if (cliente.getEstado().isEmpty()) {
			cliente.setEstado("Activo");
		}

		clienteRepository.save(cliente);
		return "redirect:/home/hogar";
	}

	@GetMapping("/login")
	public String LoginTemplate(Model model) {
		return "login-general";
	}

	@PostMapping("/ingresar")
	public String login(@RequestParam("correo") String correo, @RequestParam("password") String contrasena,
			Model model) {
		// Verificar las credenciales
		System.out.println("correo: " + correo + " contraseña:" + contrasena);

		Cliente cliente = clienteRepository.findByCorreo(correo);
		Cliente cliente1 = clienteRepository.findByContrasena(contrasena);
		if (cliente != null && cliente1 != null) {

			if (cliente.getEstado().equalsIgnoreCase("bloqueado")) {
				model.addAttribute("authenticationFailed", true);
				model.addAttribute("errorMessage", "Su cuenta se encuentra bloqueada");
				return "login-general";
			} else {
				// Inicio de sesión exitoso, redirigir a la página de resultado con la variable
				// en la URL
				System.out.println("correo: " + cliente.getCorreo() + " contraseña:" + cliente.getContrasena());
				return "redirect:/home/hogar";
			}
		} else {
			// Inicio de sesión fallido, mostrar mensaje de error en la página de inicio
			model.addAttribute("authenticationFailed", true);
			model.addAttribute("errorMessage", "Correo o contraseña incorrectos");
			return "login-general";
		}
	}

	@GetMapping("/preguntas")
	public String PreguntasFrecuentes(Model model) {
		return "preguntasfrecuentes-cliente";
	}

	// Metodo para generar Pdf
	@GetMapping("/pdf")
	public ModelAndView generarPdf() {
		ArrayList<Cliente> listadoClientes = (ArrayList<Cliente>) clienteRepository.findAll();

		// Crea el modelo con los datos que deseas pasar a la vista PDF
		Map<String, Object> model = new HashMap<>();
		model.put("clientes", listadoClientes);

		return new ModelAndView(listarClientesPdf, model);
	}
}
