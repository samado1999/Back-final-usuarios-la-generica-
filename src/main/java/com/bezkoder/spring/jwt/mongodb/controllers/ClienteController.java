package com.bezkoder.spring.jwt.mongodb.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.bezkoder.spring.jwt.mongodb.models.Cliente;
import com.bezkoder.spring.jwt.mongodb.repository.ClienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/test")
public class ClienteController {

	@Autowired
	ClienteRepository clienteRepository;

	// get single cliente by cedula
	@GetMapping("/buscar/{cedula}")
	public ResponseEntity<Cliente> getClienteByCedula(@PathVariable("cedula") String cedula) {
		Optional<Cliente> clienteData = clienteRepository.findByCedula(cedula);

		if (clienteData.isPresent()) {
			return new ResponseEntity<Cliente>(clienteData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/clientes/deleteAll")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteAllClientes() {
		clienteRepository.deleteAll();
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/clientes/save")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Cliente> createCliente(@RequestBody Cliente cliente) {
		try {
			Cliente _cliente = clienteRepository.save(cliente);
			return new ResponseEntity<>(_cliente, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/clientes")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Cliente>> getClientes(@RequestParam(required = false) String cedula) {
		try {
			List<Cliente> clientes = new ArrayList<Cliente>();

			if (cedula == null)
				clienteRepository.findAll().forEach(clientes::add);
			else
				clienteRepository.findByCedulaContaining(cedula).forEach(clientes::add);

			if (clientes.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(clientes, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/clientes/{cedula}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Cliente> updateCliente(@PathVariable("Cedula") String cedula,
			@RequestBody Cliente cliente) {
		Optional<Cliente> clienteData = clienteRepository.findByCedula(cedula);

		if (clienteData.isPresent()) {
			Cliente _cliente = clienteData.get();
			_cliente.setCedula(cliente.getCedula());
			_cliente.setNombre(cliente.getNombre());
			_cliente.setDireccion(cliente.getDireccion());
			_cliente.setCorreo(cliente.getCorreo());
			_cliente.setTelefono(cliente.getTelefono());
			return new ResponseEntity<>(clienteRepository.save(_cliente), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/clientes/{cedula}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> deleteCliente(@PathVariable("cedula") String cedula) {
		try {
			clienteRepository.deleteByCedula(cedula);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
