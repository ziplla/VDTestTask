package ru.ziplla.tech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ziplla.tech.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
