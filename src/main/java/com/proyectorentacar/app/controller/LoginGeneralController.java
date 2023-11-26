package com.proyectorentacar.app.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // Asegúrate de agregar la anotación @Controller
@RequestMapping("/login")
public class LoginGeneralController {


	// metodos para logeo y registro

	@GetMapping("/")
	public String LoginTemplate(Model model) {
		return "login-general";
	}
}
