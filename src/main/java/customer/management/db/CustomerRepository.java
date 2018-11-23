package customer.management.db;

import org.springframework.data.jpa.repository.JpaRepository;

import customer.management.model.Customer;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByLastNameStartsWithIgnoreCase(String lastName);
}
