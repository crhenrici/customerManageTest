package customer.management.db;

import org.springframework.data.jpa.repository.JpaRepository;

import customer.management.model.Orders;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {
	
	List<Orders> findByProductDescription(String productDescription);

}
