package customer.management.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import customer.management.model.Products;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRepositoryTest {

	@Autowired
	private ProductRepository repository;
	
	@Test
	public void testPreLoadedDataFindByModell() {
		List<Products> result = repository.findByModell("iPhone X");
		assertTrue(result.size() == 1);
		Products product = result.get(0);
		assertNotNull(product);
		assertEquals("Apple", product.getManufacteur());
		assertEquals("iPhone X", product.getModell());
	}
	
	@Test
	public void testSaveNewAndFindByModell() {
		repository.save(new Products("OnePlus 6", "OnePlus", 699.00));
		List<Products> result = repository.findByModell("OnePlus 6");
		assertTrue(result.size() == 1);
		Products product = result.get(0);
		assertNotNull(product);
		assertEquals("OnePlus", product.getManufacteur());
		assertEquals("OnePlus 6", product.getModell());
	}
	
	@Test
	public void testFindAll() {
		List<Products> result = repository.findAll();
		assertTrue(result.size() == 5);
	}
	
	
	
}
