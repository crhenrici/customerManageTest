package customer.management.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import customer.management.model.Orders;

/**
 * CustomerRepository interface extended by JpaRepository of Spring Data
 * @author cristian
 *
 */
public interface OrderRepository extends JpaRepository<Orders, Long> {

	/**Find all Order with producr description
	 * @param productDescription
	 * @return list of orders found by product description
	 */
	List<Orders> findByProductDescription(String productDescription);

}
