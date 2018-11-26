package customer.management.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import customer.management.model.Customer;

/**
 * 
 *
 * @author cristian
 *
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	/**
	 * S
	 * @param lastName
	 * @return 
	 */
	List<Customer> findByLastName(String lastName);
}
