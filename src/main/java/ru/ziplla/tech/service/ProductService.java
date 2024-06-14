package ru.ziplla.tech.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.ziplla.tech.entity.Product;
import ru.ziplla.tech.repository.ProductRepository;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product saveProduct(Product product) {
        try {
            return productRepository.save(product);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Product name or price may be invalid", e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save product", e);
        }
    }

    public void deleteProductById(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete product with id: " + id, e);
        }
    }

    public Product updateProductById(Long id, Product product) {
        try {
            Product updatedProduct = productRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));

            updatedProduct.setName(product.getName());
            updatedProduct.setPrice(product.getPrice());

            return productRepository.save(updatedProduct);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update product with id: " + id, e);
        }
    }

    public List<Product> uploadCSV(MultipartFile file) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            List<Product> products = br.lines()
                    .skip(1) // Skip header row
                    .map(line -> {
                        String[] fields = line.split(",");
                        Product product = new Product();
                        product.setName(fields[0]);
                        product.setPrice(Double.valueOf(fields[1]));
                        return product;
                    }).collect(Collectors.toList());

            return productRepository.saveAll(products);
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload CSV file", e);
        }
    }

    public byte[] exportCSV() {
        try {
            List<Product> products = productRepository.findAll();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try (OutputStreamWriter writer = new OutputStreamWriter(byteArrayOutputStream)) {
                writer.write("name,price\n");
                for (Product product : products) {
                    writer.write(product.getName() + "," + product.getPrice() + "\n");
                }
            }

            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to export CSV", e);
        }
    }
}

