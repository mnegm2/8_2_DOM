NEGM Moumen 4CHIT

# MidEng GK8.2 Document Oriented Middleware using MongoDB

Erstmal habe ich Mongo mit Docker gepullt.

From Version Control und dann den Link vom Kurs zum Repository einfügen

Dann wurde das Image grunnt.    

![](C:\Users\user\AppData\Roaming\marktext\images\2025-03-18-16-10-13-image.png)

Controller erstellen

```java
package warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import warehouse.model.ProductData;
import warehouse.repository.WarehouseRepository;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class WarehouseController {

    @Autowired
    private WarehouseRepository repository;

    // POST /warehouse: fügt einen neuen Lagerstandort hinzu.
    @PostMapping("/warehouse")
    public ProductData addWarehouse(@RequestBody ProductData productData) {
        return repository.save(productData);
    }

    // GET /warehouse: abrufen aller Lagerstandorte und deren Lagerbestand
    @GetMapping("/warehouse")
    public List<ProductData> getAllWarehouses() {
        return repository.findAll();
    }

    // GET /warehouse/{id}: abrufen eines Lagerstandortes id und dessen Lagerbestand
    @GetMapping("/warehouse/{id}")
    public List<ProductData> getWarehouseById(@PathVariable String id) {
        return repository.findByWarehouseID(id);
    }

    // DELETE /warehouse/{id}: löschen eines Lagerstandortes id
    @DeleteMapping("/warehouse/{id}")
    public void deleteWarehouse(@PathVariable String id) {
        List<ProductData> products = repository.findByWarehouseID(id);
        repository.deleteAll(products);
    }

    // POST /product: fügt ein neues Produkt und dessen Lagerbestand zu einem Lagerstandort hinzu
    @PostMapping("/product")
    public ProductData addProduct(@RequestBody ProductData productData) {
        return repository.save(productData);
    }

    // GET /product: abrufen aller Produkte/Lagerbestand und deren Lagerstandort
    @GetMapping("/product")
    public List<ProductData> getAllProducts() {
        return repository.findAll();
    }

    // GET /product/{id}: abrufen eines Produktes id und dessen Lagerstandorte
    @GetMapping("/product/{id}")
    public Optional<ProductData> getProductById(@PathVariable String id) {
        return repository.findById(id);
    }
 
    // DELETE /product/{id}: löschen eines Produktes id auf einem Lagerstandort
    @DeleteMapping("/product/{id}")
    public void deleteProduct(@PathVariable String id) {
        repository.deleteById(id);
    }
}
```

![](C:\Users\user\AppData\Roaming\marktext\images\2025-03-18-17-05-33-image.png)    

![](C:\Users\user\AppData\Roaming\marktext\images\2025-03-18-17-16-44-image.png)

Produkt hinzufügen:

![](C:\Users\user\AppData\Roaming\marktext\images\2025-03-18-17-33-54-image.png)

hinzufügen:

curl -X POST "http://localhost:8080/api/product" -H "Content-Type: application/json" -d "{"warehouseID":"1", "productID":"01-123456", "productName":"Beispiel Produkt", "productCategory":"Kategorie A", "productQuantity":50.0}"



Löschen über Shell:

![](C:\Users\user\AppData\Roaming\marktext\images\2025-03-25-15-32-49-image.png)

## Fragen

**Nennen Sie 4 Vorteile eines NoSQL Repository im Vergleich zu einem relationalen DBMS**

1. **Hohe Skalierbarkeit** – NoSQL-Datenbanken lassen sich leichter horizontal skalieren.
2. **Flexible Datenmodelle** – Schemafrei, was Änderungen an der Datenstruktur erleichtert.
3. **Schnelle Verarbeitung** – Optimiert für schnelle Lese- und Schreibzugriffe, besonders bei großen Datenmengen.
4. **Verteilte Architektur** – Bietet starke Unterstützung für verteilte Systeme und Replikation.

**Nennen Sie 4 Nachteile eines NoSQL Repository im Vergleich zu einem relationalen DBMS**

1. **Geringere Konsistenz** – Oft eventual consistency anstelle von ACID-Transaktionen.
2. **Mangelnde Standardisierung** – Es gibt keinen einheitlichen Abfrage-Standard wie SQL.
3. **Eingeschränkte Abfrageoptionen** – Fehlende Joins erfordern häufig zusätzlichen Verarbeitungsaufwand.
4. **Schwächere Transaktionsunterstützung** – Besonders problematisch für Anwendungen, die hohe Datenintegrität erfordern, wie Finanzsysteme.

**Welche Herausforderungen entstehen bei der Datenzusammenführung?**  
Daten können unterschiedliche Formate, Strukturen und Herkunftssysteme haben, was eine aufwendige Harmonisierung nötig macht. Redundanzen und Inkonsistenzen müssen identifiziert und bereinigt werden. Unterschiedliche Konsistenzmodelle können zu Konflikten führen.

**Welche Arten von NoSQL-Datenbanken gibt es?**

1. Key-Value-Datenbanken
2. Dokumentenorientierte Datenbanken
3. Spaltenorientierte Datenbanken
4. Graphdatenbanken

**Nennen Sie ein Beispiel für jede Art**

1. **Key-Value** – Redis
2. **Dokumentenorientiert** – MongoDB
3. **Spaltenorientiert** – Apache Cassandra
4. **Graphbasiert** – Neo4j

**Erläutern Sie die Begriffe CA, CP und AP im Kontext des CAP-Theorems**

- **CA (Consistency & Availability)** – Garantiert Konsistenz und Verfügbarkeit, kann jedoch Partitionstoleranz nicht gewährleisten (z. B. klassische relationale Datenbanken).
- **CP (Consistency & Partition Tolerance)** – Gewährleistet Konsistenz und Partitionstoleranz, kann aber Verfügbarkeit einschränken (z. B. HBase).
- **AP (Availability & Partition Tolerance)** – Sichert Verfügbarkeit und Partitionstoleranz, verzichtet aber auf strikte Konsistenz (z. B. DynamoDB).

**Mit welchem Befehl koennen Sie den Lagerstand eines Produktes aller Lagerstandorte anzeigen.**

`db.warehouseData.aggregate([...])` --> Der Befehl durchläuft alle Lagerstandorte, filtert nach dem Produktnamen und summiert die Mengen. 

**Mit welchem Befehl koennen Sie den Lagerstand eines Produktes eines bestimmten Lagerstandortes anzeigen.**

`db.warehouseData.find(...)` --> Der Befehl sucht nach dem Lagerstandort mit der angegebenen ID und gibt nur die Daten des gesuchten Produkts zurück.
