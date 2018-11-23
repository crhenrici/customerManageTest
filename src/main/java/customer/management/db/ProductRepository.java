package customer.management.db;

import org.springframework.data.jpa.repository.JpaRepository;

import customer.management.model.Products;

import java.util.List;

public interface ProductRepository extends JpaRepository<Products, Long> {
	List<Products> findByModell(String modell);

}
