package customer.manage.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Products, Long> {
	List<Products> findByModell(String modell);

}
