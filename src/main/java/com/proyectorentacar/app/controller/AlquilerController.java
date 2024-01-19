package com.proyectorentacar.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyectorentacar.app.entity.Alquiler;
import com.proyectorentacar.app.exception.NotFoundException;
import com.proyectorentacar.app.repository.AlquilerRepository;

@RestController
@RequestMapping(value = "/api/alquileres")
public class AlquilerController {

	@Autowired
    private AlquilerRepository alquilerRepository;
	
	@GetMapping("/")
    public List<Alquiler> getAllAlquileres() {
        return alquilerRepository.findAll();
    }

    @GetMapping("/{id}")
    public Alquiler getAlquilerById(@PathVariable String id) {
        return alquilerRepository.findById(id).orElseThrow(() -> new NotFoundException("Alquiler no encontrado"));
    }

    @PostMapping("/")
    public Alquiler saveAlquiler(@RequestBody Map<String, Object> body) {
        ObjectMapper mapper = new ObjectMapper();
        Alquiler alquiler = mapper.convertValue(body, Alquiler.class);
        return alquilerRepository.save(alquiler);
    }

    @PutMapping("/{id}")
    public Alquiler updateAlquiler(@PathVariable String id, @RequestBody Map<String, Object> body) {
        ObjectMapper mapper = new ObjectMapper();
        Alquiler alquiler = mapper.convertValue(body, Alquiler.class);
        alquiler.setId(id);
        return alquilerRepository.save(alquiler);
    }

    @DeleteMapping("/{id}")
    public Alquiler deleteAlquiler(@PathVariable String id) {
    	Alquiler alquiler = alquilerRepository.findById(id).orElseThrow(() -> new NotFoundException("Alquiler no encontrado"));
    	alquilerRepository.deleteById(id);
        return alquiler;
    }
	
}
