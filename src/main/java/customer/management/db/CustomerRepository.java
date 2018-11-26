package customer.management.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import customer.management.model.Customer;

/**
 * CustomerRepository interface extended by JpaRepository of Spring Data
 * @author cristian
 *
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	/**
	 * Find all Customers with lastName
	 * @param lastName
	 * @return list of customers found by lastName
	 */
	List<Customer> findByLastName(String lastName);
}
