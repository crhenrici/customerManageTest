package customer.management.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import customer.management.model.Products;

public interface ProductRepository extends JpaRepository<Products, Long> {
	List<Products> findByModell(String modell);

}
