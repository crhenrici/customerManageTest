package customer.management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import customer.management.db.CustomerRepository;
import customer.management.db.ProductRepository;
import customer.management.model.Customer;
import customer.management.model.Products;

/**
 * @author cristian
 *PriceCalculationService class
 */
@Service
public class PriceCalculationService {
	
	private static Double studentDiscount = 10.00;
	
	@Autowired
	private CustomerRepository customerRepo;
	@Autowired
	private ProductRepository productRepo;
	

	/**
	 * calculates the discounted price if it is a student
	 * @param modellName
	 * @param customerName
	 * @return final price of product
	 */
	public Double calculatePrice(String modellName, String customerName) {
		//loads list of products from ProductRepository
		List<Products> resultProductList = productRepo.findByModell(modellName);
		Preconditions.checkState(resultProductList.size() == 1, "No products found for Model: " + modellName);
		Products products = resultProductList.get(0);
		
		//loads list of customers from CustomerRepository
		List<Customer> resultCustomerList = customerRepo.findByLastName(customerName);
		Preconditions.checkState(resultCustomerList.size() == 1, "No name found for Customer: " + customerName);
		Customer customer = resultCustomerList.get(0);
		Double currentPrice = products.getPrice();
		
		Boolean isStudent = customer.getIsStudent();
		
		//calculates discounted price if it is a student
		if(isStudent) {
			Double discount = currentPrice / 100 * studentDiscount;
			return currentPrice - discount;
		}
		return currentPrice;
		
	}
}
