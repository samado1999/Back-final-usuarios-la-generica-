package com.bezkoder.spring.jwt.mongodb.repository;

import java.util.List;
import java.util.Optional;
import com.bezkoder.spring.jwt.mongodb.models.Producto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductoRepository extends MongoRepository<Producto, String> {
    List<Producto> findByProductNameContaining(String productName); // buscar elemento por nombre

    Optional<Producto> findByProductName(String productName); // actualizar por nombre

    void deleteByProductName(String productName); // eliminar documento por nombre
}
