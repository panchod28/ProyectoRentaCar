package com.proyectorentacar.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyectorentacar.app.entity.Administrador;
import com.proyectorentacar.app.entity.Trabajador;
import com.proyectorentacar.app.repository.AdministradorRepository;
import com.proyectorentacar.app.repository.TrabajadorRepository;

@Controller // Asegúrate de agregar la anotación @Controller
@RequestMapping("/empleados")
public class LoginEmpleadoController {
	@Autowired
	private AdministradorRepository administradorRepository;
	
	@Autowired
	private TrabajadorRepository trabajadorRepository;
	
	//metodos para logeo y registro
	
		@GetMapping("/login")
		public String LoginTemplate(Model model) {
			return "login-general";
		}

		@PostMapping("/ingresar")
		public String login(@RequestParam("usuario") String usuario,
				@RequestParam("contrasena") String contrasena, Model model) {
			// Verificar las credenciales
			System.out.println("usuario: " + usuario + " contraseña:" + contrasena);

			Administrador administrador = administradorRepository.findByUsuario(usuario);
			Administrador administrador1 = administradorRepository.findByContrasena(contrasena);
			Trabajador trabajador = trabajadorRepository.findByUsuario(usuario);
			Trabajador trabajador1 = trabajadorRepository.findByContrasena(contrasena);
			if (administrador != null && administrador1 != null) {
				
				if(administrador.getEstado().equalsIgnoreCase("Bloqueado")){
					model.addAttribute("authenticationFailed", true);
					model.addAttribute("errorMessage", "Su cuenta se encuentra bloqueada");
					return "login-general";
				}else {
				// Inicio de sesión exitoso, redirigir a la página de resultado con la variable
				// en la URL
				System.out.println(
						"correo: " + administrador.getUsuario() + " contraseña:" + administrador.getContrasena());
				return "redirect:/home/principal";
				}
			} else if(trabajador != null && trabajador1 != null) {
				
				if(trabajador.getEstado().equalsIgnoreCase("Bloqueado")){
					model.addAttribute("authenticationFailed", true);
					model.addAttribute("errorMessage", "Su cuenta se encuentra bloqueada");
					return "login-general";
				}else {
				// Inicio de sesión exitoso, redirigir a la página de resultado con la variable
				// en la URL
				System.out.println(
						"correo: " + trabajador.getUsuario() + " contraseña:" + trabajador.getContrasena());
				return "redirect:/home/";
				}
			}else {
				
				// Inicio de sesión fallido, mostrar mensaje de error en la página de inicio
				model.addAttribute("authenticationFailed", true);
				model.addAttribute("errorMessage", "Correo o contraseña incorrectos");
				return "login-general";
			}
		}
}
