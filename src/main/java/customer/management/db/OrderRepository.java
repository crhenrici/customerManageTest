package customer.management.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import customer.management.model.Orders;

public interface OrderRepository extends JpaRepository<Orders, Long> {

	List<Orders> findByProductDescription(String productDescription);

}
