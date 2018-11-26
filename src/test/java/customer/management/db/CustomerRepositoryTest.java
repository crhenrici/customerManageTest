package customer.management.db;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import customer.management.model.Customer;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerRepositoryTest {
	
	@Autowired
	private CustomerRepository repository;
	
	@Test
	public void testPreLoadedDataFindByName() {
		List<Customer> result = repository.findByLastName("Martinez");
		assertTrue(result.size() == 1);
		Customer customer = result.get(0);
		assertNotNull(customer);
		assertEquals("Pedro", customer.getFirstName());
		assertEquals("Martinez", customer.getLastName());
	}
	
	@Test
	public void testSaveNewAndFindById() {
		repository.save(new Customer("Jose", "Hernandez", false));
		List<Customer> result = repository.findByLastName("Hernandez");
		assertTrue(result.size() == 1);
		Customer customer = result.get(0);
		assertNotNull(customer);
		assertEquals("Jose", customer.getFirstName());
		assertEquals("Hernandez", customer.getLastName());
	}
	
	@Test
	public void testFindAll() {
		List<Customer> result = repository.findAll();
		assertTrue(result.size() == 5);
	}
	

}
