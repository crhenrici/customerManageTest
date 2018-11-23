package customer.management;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

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
    public CommandLineRunner loadDataCustomer(CustomerRepository repository) {
        return (args) -> {
            // save a couple of customers
            repository.save(new Customer("Pedro", "Martinez"));
            repository.save(new Customer("Steve", "Jobs"));
            repository.save(new Customer("Michael", "Jordan"));
            repository.save(new Customer("Kobe", "Bryant"));
            repository.save(new Customer("Amir", "Jones"));
            

            // fetch all customers
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for (Customer customer : repository.findAll()) {
                log.info(customer.toString());
            }
            log.info("");
            
            // fetch an individual customer by ID
            Customer customer = repository.findById(1L).get();
            log.info("Customer found with findOne(1L):");
            log.info("--------------------------------");
            log.info(customer.toString());
            log.info("");

            // fetch customers by last name
            log.info("Customer found with findByLastNameStartsWithIgnoreCase('Bauer'):");
            log.info("--------------------------------------------");
            for (Customer bauer : repository
                    .findByLastNameStartsWithIgnoreCase("Bauer")) {
                log.info(bauer.toString());
            }
        };
    }
       @Bean
        public CommandLineRunner loadDataProducts(ProductRepository repo) {
            return (args) -> {
                // save a couple of products
                repo.save(new Products("iPhone X", "Apple"));
                repo.save(new Products("iPhone XS", "Apple"));
                repo.save(new Products("Galaxy S9", "Samsung"));
                repo.save(new Products("Galaxy A9", "Samsung"));
                repo.save(new Products("Pixel", "Google"));
                
                log.info("Products found with findAll():");
                log.info("------------------------------");
                for (Products products : repo.findAll()) {
                	log.info(products.toString());
                }
                log.info("");
                
                log.info("Products found with findByModellWithIgnoreCase('iPhone X'):");
                log.info("-------------------------------------------");
                for (Products iPhone : repo.findByModell("iPhone X")) {
                	log.info(iPhone.toString());
                }
                log.info("");

            };
    }
       
       @Bean
       public CommandLineRunner loadDataOrder(OrderRepository repo) {
    	   return (args) -> {
    		   repo.save(new Orders("iPhone X", "Bauer"));
    		   repo.save(new Orders("Galaxy S9", "Dessler"));
    		   repo.save(new Orders("Pixel", "Palmer"));
    		   repo.save(new Orders("Galaxy A9", "O'Brian"));
    		   
    		   log.info("Orders found with findAll(): ");
    		   log.info("-----------------------------");
    		   for (Orders order : repo.findAll()) {
    			   log.info(order.toString());
    		   }
    		   log.info("");
    		   
    		   log.info("Products found with findByOrder( 'Bauer'");
    		   log.info("----------------------------------------");
    		   for (Orders bauer : repo.findByProductDescription("Bauer")) {
    			   log.info(bauer.toString());
    		   }
    		   log.info("");
    	   };
       }
}
