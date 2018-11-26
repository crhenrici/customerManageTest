package customer.management.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import customer.management.model.Products;

/**
 * CustomerRepository interface extended by JpaRepository of Spring Data 
 * @author cristian
 *
 */
public interface ProductRepository extends JpaRepository<Products, Long> {
	/**Find all products with modell name
	 * @param modell
	 * @return list of products found by modell
	 */
	List<Products> findByModell(String modell);

}
