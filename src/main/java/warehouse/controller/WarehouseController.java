package warehouse.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import warehouse.model.ProductData;
import warehouse.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class WarehouseController {

    @Autowired
    private WarehouseRepository repository;

    // POST /product: Fügt ein neues Produkt (inklusive Lagerbestand) zu einem Lagerstandort hinzu
    @PostMapping("/product")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductData createProduct(@RequestBody ProductData product) {
        return repository.save(product);
    }

    // GET /product: Abrufen aller Produkte (die bereits auch die warehouseID enthalten)
    @GetMapping("/product")
    public List<ProductData> getAllProducts() {
        return repository.findAll();
    }

    // GET /warehouse: Abrufen aller Lagerstandorte (distinct warehouseIDs) und deren zugehörigen Lagerbestand (Produkte)
    @GetMapping("/warehouse")
    public List<Map<String, Object>> getAllWarehouses() {
        List<ProductData> allProducts = repository.findAll();
        // Gruppiere die Produkte anhand ihrer warehouseID
        Map<String, List<ProductData>> grouped = allProducts.stream()
                .collect(Collectors.groupingBy(ProductData::getWarehouseID));

        List<Map<String, Object>> response = new ArrayList<>();
        grouped.forEach((warehouseID, products) -> {
            Map<String, Object> warehouseGroup = new HashMap<>();
            warehouseGroup.put("warehouseID", warehouseID);
            warehouseGroup.put("products", products);
            response.add(warehouseGroup);
        });
        return response;
    }
}