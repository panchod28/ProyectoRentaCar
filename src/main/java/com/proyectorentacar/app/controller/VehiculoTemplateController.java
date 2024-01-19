package com.proyectorentacar.app.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.proyectorentacar.app.entity.Alquiler;
import com.proyectorentacar.app.entity.Vehiculo;
import com.proyectorentacar.app.exception.NotFoundException;
import com.proyectorentacar.app.repository.AlquilerRepository;
import com.proyectorentacar.app.repository.VehiculoRepository;
import com.proyectorentacar.app.utilities.ImageConverter;
import com.proyectorentacar.app.utilities.ListarVehiculosPdf;
import com.proyectorentacar.app.utilities.VehiculosDisponiblesPdf;

@Controller // Asegúrate de agregar la anotación @Controller
@RequestMapping("/vehiculos")
public class VehiculoTemplateController {

	@Autowired
	private VehiculoRepository vehiculoRepository;

	@Autowired
	private AlquilerRepository alquilerRepository;

	@Autowired
	private ImageConverter imageConverter;

	@Autowired
	private ListarVehiculosPdf listarVehiculosPdf;

	@Autowired
	private VehiculosDisponiblesPdf vehiculosDisponiblesPdf;

	// metodos para trabajador

	@GetMapping("/")
	public String VehiculosListByTrabajador(Model model) {
		model.addAttribute("vehiculos", vehiculoRepository.findAll());
		return "vehiculos-list-trabajador";
	}

	@GetMapping("/new")
	public String vehiculoNewByTrabajador(Model model) {
		model.addAttribute("vehiculo", new Vehiculo());
		return "vehiculos-form-trabajador";
	}

	@PostMapping("/save")
	public String VehiculoSaveByTrabajador(@ModelAttribute("vehiculo") Vehiculo vehiculo,
			@RequestParam("file") MultipartFile imagen) throws IOException {
		if (vehiculo.getId().isEmpty()) {
			vehiculo.setId(null);
		}

		if (vehiculo.getEstado().isEmpty()) {
			vehiculo.setEstado("Disponible");
		}

		if (!imagen.isEmpty()) {
			byte[] imageBytes = imagen.getBytes();
			String base64Image = imageConverter.encode(imageBytes); // Codificar la imagen en base64
			System.out.println(base64Image);
			vehiculo.setImagen(base64Image); // Asegúrate de que la entidad Vehiculo tenga un campo 'imagen' para
												// almacenar la imagen en base64.
		}

		vehiculoRepository.save(vehiculo);
		return "redirect:/vehiculos/";
	}

	@GetMapping("/edit/{id}")
	public String VehiculoEditByTrabajador(@PathVariable("id") String id, Model model) {
		model.addAttribute("vehiculo",
				vehiculoRepository.findById(id).orElseThrow(() -> new NotFoundException("Vehiculo no encontrado")));
		return "vehiculos-form-trabajador";
	}

	@GetMapping("/delete/{id}")
	public String VehiculoDeleteByTrabajador(@PathVariable("id") String id) {
		Vehiculo vehiculo = vehiculoRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
		Alquiler alquiler = alquilerRepository.findByVehiculo(vehiculo);
		if (alquiler != null) {
			alquilerRepository.deleteById(alquiler.getId());
		}
		vehiculoRepository.deleteById(id);
		return "redirect:/vehiculos/";
	}

	// metodos para administrador

	@GetMapping("/lista")
	public String VehiculosListByAdministrador(Model model) {
		model.addAttribute("vehiculos", vehiculoRepository.findAll());
		return "vehiculos-list-administrador";
	}

	@GetMapping("/nuevo")
	public String VehiculoNewByAdministrador(Model model) {
		model.addAttribute("vehiculo", new Vehiculo());
		return "vehiculos-form-administrador";
	}

	@PostMapping("/salvar")
	public String VehiculoSaveByAdministrador(@ModelAttribute("vehiculo") Vehiculo vehiculo,
			@RequestParam("file") MultipartFile imagen) throws IOException {
		if (vehiculo.getId().isEmpty()) {
			vehiculo.setId(null);
		}

		if (vehiculo.getEstado().isEmpty()) {
			vehiculo.setEstado("Disponible");
		}

		if (!imagen.isEmpty()) {
			byte[] imageBytes = imagen.getBytes();
			String base64Image = imageConverter.encode(imageBytes); // Codificar la imagen en base64
			System.out.println(base64Image);
			vehiculo.setImagen(base64Image); // Asegúrate de que la entidad Vehiculo tenga un campo 'imagen' para
												// almacenar la imagen en base64.
		}

		vehiculoRepository.save(vehiculo);
		return "redirect:/vehiculos/lista";
	}

	@GetMapping("/editar/{id}")
	public String VehiculoEditByAdministrador(@PathVariable("id") String id, Model model) {
		model.addAttribute("vehiculo",
				vehiculoRepository.findById(id).orElseThrow(() -> new NotFoundException("Vehiculo no encontrado")));
		return "vehiculos-form-administrador";
	}

	@GetMapping("/eliminar/{id}")
	public String VehiculoDeleteByAdministrador(@PathVariable("id") String id) {
		Vehiculo vehiculo = vehiculoRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
		Alquiler alquiler = alquilerRepository.findByVehiculo(vehiculo);
		if (alquiler != null) {
			alquilerRepository.deleteById(alquiler.getId());
		}
		vehiculoRepository.deleteById(id);
		return "redirect:/vehiculos/lista";
	}

	@GetMapping("/detalles/administrador/{id}")
	public String VehiculoDetallesByAdministrador(@ModelAttribute("vehiculo") Vehiculo vehiculo,
			@PathVariable("id") String id, Model model) {
		model.addAttribute("vehiculo",
				vehiculoRepository.findById(id).orElseThrow(() -> new NotFoundException("Vehiculo no encontrado")));
		return "detalle-vehiculo-administrador";
	}

	@GetMapping("/detalles/trabajador/{id}")
	public String VehiculoDetallesByTrabajador(@ModelAttribute("vehiculo") Vehiculo vehiculo,
			@PathVariable("id") String id, Model model) {
		model.addAttribute("vehiculo",
				vehiculoRepository.findById(id).orElseThrow(() -> new NotFoundException("Vehiculo no encontrado")));
		return "detalle-vehiculo-trabajador";
	}

	@GetMapping("/detalles/cliente/{id}")
	public String VehiculoDetallesByCliente(@ModelAttribute("vehiculo") Vehiculo vehiculo,
			@PathVariable("id") String id, Model model) {
		model.addAttribute("vehiculo",
				vehiculoRepository.findById(id).orElseThrow(() -> new NotFoundException("Vehiculo no encontrado")));
		return "detalle-vehiculo-cliente";
	}

	// Metodo para generar Pdf
	@GetMapping("/pdf")
	public ModelAndView generarPdf() {
		ArrayList<Vehiculo> listadoVehiculos = (ArrayList<Vehiculo>) vehiculoRepository.findAll();

		// Crea el modelo con los datos que deseas pasar a la vista PDF
		Map<String, Object> model = new HashMap<>();
		model.put("vehiculos", listadoVehiculos);

		return new ModelAndView(listarVehiculosPdf, model);
	}

	@GetMapping("/disponibles/pdf")
	public ModelAndView generarPdfDisponibles() {
		// Obtén todos los vehículos
		List<Vehiculo> listadoVehiculos = vehiculoRepository.findAll();

		// Filtra los vehículos disponibles
		List<Vehiculo> listadoVehiculosDisponibles = new ArrayList<>();
		for (Vehiculo vehiculo : listadoVehiculos) {
			if ("Disponible".equals(vehiculo.getEstado())) {
				listadoVehiculosDisponibles.add(vehiculo);
			}
		}
		Map<String, Object> model = new HashMap<>();
		model.put("vehiculos", listadoVehiculosDisponibles);

		return new ModelAndView(vehiculosDisponiblesPdf, model);
	}

}
