package com.example.developerassignment.endpoints;

import com.example.developerassignment.model.Product;
import com.example.developerassignment.repositories.ProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//I could have used @RepositoryRestResource in ProductRepository, but I prefer to have it explicitly
@RestController
@RequestMapping("api/products")
@AllArgsConstructor
@Tag(name = "Products", description = "Products access point")
public class ProductController {

    final ProductRepository productRepository;

    @GetMapping
    @Operation(summary = "Fetch all products stored in db")
    public ResponseEntity<List<Product>> listAllProducts() {
        return ResponseEntity.ok(productRepository.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Fetch single product stored in db")
    public ResponseEntity<Product> fetchProduct(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productRepository.findById(id).orElse(new Product()));
    }

    @PostMapping
    @Operation(summary = "Store new product")
    public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productRepository.save(product));
    }

    @PutMapping
    @Operation(summary = "Update single product", description = "Must provide product with valid id")
    //Could use DTO when necessary and set new values
    public ResponseEntity<Product> updateProduct(@RequestBody Product newProduct) {
        final Optional<Product> oldProduct = productRepository.findById(newProduct.getId());
        if (!oldProduct.isPresent()) {
//            We can map a generic handler that catches and returns exceptions
//            throw new RestClientException("Product id should be defined!")
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(productRepository.save(newProduct));
    }
}
