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

import customer.management.model.Orders;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OrderRepositoryTest {
	
	@Autowired
	private OrderRepository repository;
	
	@Test
	public void testPreLoadedDataFindByProductDescription() {
		List<Orders> result = repository.findByProductDescription("iPhone X");
		assertTrue(result.size() == 1);
		Orders order = result.get(0);
		assertNotNull(order);
		assertEquals("Bauer", order.getCustomerName());
		assertEquals("iPhone X", order.getProductDescription());
	}
	
	@Test
	public void testSaveNewAndFindByProductDescription() {
		repository.save(new Orders("iPhone XR", "Jobs", 999.00));
		List<Orders> result = repository.findByProductDescription("iPhone XR");
		assertTrue(result.size() == 1);
		Orders order = result.get(0);
		assertNotNull(order);
		assertEquals("Jobs", order.getCustomerName());
		assertEquals("iPhone XR", order.getProductDescription());
	}
	
	@Test
	public void testFindAll() {
		List<Orders> result = repository.findAll();
		assertTrue(result.size() == 2);
	}

}
