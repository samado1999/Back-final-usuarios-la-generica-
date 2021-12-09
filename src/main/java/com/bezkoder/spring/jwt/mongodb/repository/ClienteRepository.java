package com.bezkoder.spring.jwt.mongodb.repository;

import java.util.List;
import java.util.Optional;

import com.bezkoder.spring.jwt.mongodb.models.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClienteRepository extends MongoRepository<Cliente, String> {
	List<Cliente> findByCedulaContaining(String cedula); // buscar elemento por cedula

	Optional<Cliente> findByCedula(String cedula); // actualizar por cedula

	void deleteByCedula(String cedula); // eliminar documento por cedula
}
