package customer.management;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import customer.management.db.CustomerRepository;
import customer.management.db.OrderRepository;
import customer.management.db.ProductRepository;
import customer.management.model.Customer;
import customer.management.model.Orders;
import customer.management.model.Products;

@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	@Bean
	public CommandLineRunner loadDataCustomer(CustomerRepository repo) {
		return (args) -> {
			// save a couple of customers
			repo.save(new Customer("Pedro", "Martinez", false));
			repo.save(new Customer("Steve", "Jobs", false));
			repo.save(new Customer("Michael", "Jordan", true));
			repo.save(new Customer("Kobe", "Bryant", false));
			repo.save(new Customer("Amir", "Jones", true));

			// fetch all customers
			log.info("Customers found with findAll():");
			log.info("-------------------------------");
			for (Customer customer : repo.findAll()) {
				log.info(customer.toString());
			}
			log.info("");

			// fetch an individual customer by ID
			Customer customer = repo.findById(1L).get();
			log.info("Customer found with findOne(1L):");
			log.info("--------------------------------");
			log.info(customer.toString());
			log.info("");

			// fetch customers by last name
			log.info("Customer found with findByLastName('Jobs'):");
			log.info("--------------------------------------------");
			for (Customer bauer : repo.findByLastName("Jobs")) {
				log.info(bauer.toString());
			}
		};
	}

	@Bean
	public CommandLineRunner loadDataOrder(OrderRepository repo) {
		return (args) -> {
			repo.save(new Orders("iPhone X", "Martinez", 999.00));
			repo.save(new Orders("Galaxy S9", "Jobs", 899.00));

			log.info("Orders found with findAll(): ");
			log.info("-----------------------------");
			for (Orders order : repo.findAll()) {
				log.info(order.toString());
			}
			log.info("");

			log.info("Products found with findByProductDescription( 'Bauer'");
			log.info("----------------------------------------");
			for (Orders bauer : repo.findByProductDescription("Bauer")) {
				log.info(bauer.toString());
			}
			log.info("");
		};
	}

	@Bean
	public CommandLineRunner loadDataProducts(ProductRepository repo) {
		return args -> {
			// save a couple of products
			repo.save(new Products("iPhone X", "Apple", 999.00));
			repo.save(new Products("iPhone XS", "Apple", 1099.00));
			repo.save(new Products("Galaxy S9", "Samsung", 899.00));
			repo.save(new Products("Galaxy A9", "Samsung", 879.00));
			repo.save(new Products("Pixel", "Google", 799.00));

			log.info("Products found with findAll():");
			log.info("------------------------------");
			for (Products products : repo.findAll()) {
				log.info(products.toString());
			}
			log.info("");

			log.info("Products found with findByModell('iPhone X'):");
			log.info("-------------------------------------------");
			for (Products iPhone : repo.findByModell("iPhone X")) {
				log.info(iPhone.toString());
			}
			log.info("");

		};
	}
}
