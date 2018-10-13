package hello;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Products, Long> {
	List<Products> findByModellWithIgnoreCase(String modell);

}
