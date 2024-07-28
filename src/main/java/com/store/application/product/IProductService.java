package com.store.application.product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IProductService {
    List<Product> getAllProducts();
    Optional<Product> getProductById(UUID id);
    Product createProduct(Product product);
    Product updateProduct(Product updatedProduct);
    void deleteProduct(UUID id);
    List<Product> getProductsByCategory(Category category);
    Product changePrice(UUID id, Double amount);
    Product increaseQuantity(UUID id, int amount);
}