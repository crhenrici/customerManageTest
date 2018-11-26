package customer.management.service;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PriceCalculationServiceTest {
	
	@Autowired
	private PriceCalculationService priceCalculation;
	
	@Test
	public void testPriceCalculation() {
		Double result = priceCalculation.calculatePrice("iPhone XS", "Jones");
		assertTrue(989.1 == result);
	}
	
	@Test
	public void testPriceCalculationWithoutStudentDiscount() {
		Double result = priceCalculation.calculatePrice("iPhone XS", "Martinez");
		assertTrue(1099 == result);
	}
}
