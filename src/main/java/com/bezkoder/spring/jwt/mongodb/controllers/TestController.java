package com.bezkoder.spring.jwt.mongodb.controllers;

import java.util.List;

import com.bezkoder.spring.jwt.mongodb.models.Producto;
import com.bezkoder.spring.jwt.mongodb.models.Role;
import com.bezkoder.spring.jwt.mongodb.repository.ProductoRepository;
import com.bezkoder.spring.jwt.mongodb.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

	@Autowired
	ProductoRepository productoRepository;

	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}

	@Autowired
	private RoleRepository roleRepository;

	@GetMapping("/list")
	public List<Role> list() {
		return roleRepository.findAll();
	}

	@PostMapping("/createAll")
	public void createAll(@RequestBody List<Role> roles) {
		roleRepository.saveAll(roles);
	}

	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public String userAccess() {
		return "User Content.";
	}

	@GetMapping("/mod")
	@PreAuthorize("hasRole('MODERATOR')")
	public String moderatorAccess() {
		return "Moderator Board.";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Admin Board.";
	}

	@GetMapping("/admin-producto")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> adminAccessProducto(@RequestBody List<Producto> productos) {
		try {
			productoRepository.saveAll(productos);
			return new ResponseEntity<String>("Productos guardados", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Error al guardar productos", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/admin-cliente")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccessCliente() {
		return "Cliente";
	}

	@GetMapping("/admin-venta")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccessVenta() {
		return "Venta";
	}

	@GetMapping("/admin-reporte")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccessReporte() {
		return "Reporte";
	}

	@GetMapping("/admin-consolidado")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccessConsolidado() {
		return "Consolidado";
	}
}
