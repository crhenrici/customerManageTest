package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner loadData(CustomerRepository repository, ProductRepository repo) {
        return (args) -> {
            // save a couple of customers
            repository.save(new Customer("Jack", "Bauer"));
            repository.save(new Customer("Chloe", "O'Brian"));
            repository.save(new Customer("Kim", "Bauer"));
            repository.save(new Customer("David", "Palmer"));
            repository.save(new Customer("Michelle", "Dessler"));
            repo.save(new Products("iPhone X", "Apple"));
            repo.save(new Products("iPhone XS", "Apple"));
            repo.save(new Products("Galaxy S9", "Samsung"));
            repo.save(new Products("Galaxy A9", "Samsung"));
            repo.save(new Products("Pixel", "Google"));
            

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
            log.info("");
            
            log.info("Products found with findAll():");
            log.info("------------------------------");
            for (Products products : repo.findAll()) {
            	log.info(products.toString());
            }
            log.info("");
            
            Products products = repo.findById(1L).get();
            log.info("Products found with findOne(1L):");
            log.info("--------------------------------");
            log.info(products.toString());
            log.info("");
            
            log.info("Products found with findByModellWithIgnoreCase('iPhone X'):");
            log.info("-------------------------------------------");
            for (Products iPhone : repo.findByModellWithIgnoreCase("iPhone X")) {
            	log.info(iPhone.toString());
            }
            log.info("");

        };
    }

}
