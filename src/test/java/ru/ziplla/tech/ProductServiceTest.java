package ru.ziplla.tech;

import ru.ziplla.tech.entity.Product;
import ru.ziplla.tech.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import ru.ziplla.tech.service.ProductService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllProducts() {
        List<Product> products = Arrays.asList(new Product("Product1", 10.0), new Product("Product2", 20.0));
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getAllProducts();

        assertEquals(2, result.size());
        assertEquals("Product1", result.get(0).getName());
        assertEquals(10.0, result.get(0).getPrice());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void testSaveProduct() {
        Product product = new Product("Product1", 10.0);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product result = productService.saveProduct(product);

        assertEquals("Product1", result.getName());
        assertEquals(10.0, result.getPrice());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void testDeleteProductById() {
        doNothing().when(productRepository).deleteById(anyLong());

        productService.deleteProductById(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testUpdateProductById() {
        Product existingProduct = new Product("Existing Product", 15.0);
        existingProduct.setId(1L);

        Product updatedProduct = new Product("Updated Product", 20.0);

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);

        Product result = productService.updateProductById(1L, updatedProduct);

        assertEquals("Updated Product", result.getName());
        assertEquals(20.0, result.getPrice());
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(existingProduct);
    }

    @Test
    public void testUploadCSV() throws Exception {
        String csvContent = "name,price\nProduct1,10.0\nProduct2,20.0";
        MockMultipartFile file = new MockMultipartFile("file", "products.csv", "text/csv", csvContent.getBytes());

        List<Product> products = Arrays.asList(new Product("Product1", 10.0), new Product("Product2", 20.0));
        when(productRepository.saveAll(anyList())).thenReturn(products);

        List<Product> result = productService.uploadCSV(file);

        assertEquals(2, result.size());
        assertEquals("Product1", result.get(0).getName());
        assertEquals(10.0, result.get(0).getPrice());
        verify(productRepository, times(1)).saveAll(anyList());
    }

    @Test
    public void testExportCSV() throws Exception {
        List<Product> products = Arrays.asList(new Product("Product1", 10.0), new Product("Product2", 20.0));
        when(productRepository.findAll()).thenReturn(products);

        byte[] result = productService.exportCSV();

        String expectedCsvContent = "name,price\nProduct1,10.0\nProduct2,20.0\n";
        assertEquals(expectedCsvContent, new String(result, StandardCharsets.UTF_8));
        verify(productRepository, times(1)).findAll();
    }
}
