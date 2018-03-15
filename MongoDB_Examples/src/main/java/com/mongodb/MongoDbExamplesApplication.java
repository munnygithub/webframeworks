package com.mongodb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mongodb.customer.Customer;
import com.mongodb.customer.CustomerRepository;
import com.mongodb.domain.Domain;
import com.mongodb.domain.DomainRepository;

@SpringBootApplication
public class MongoDbExamplesApplication implements CommandLineRunner {

	@Autowired
	private CustomerRepository repository;
	@Autowired
	DomainRepository domainRepository;

	public static void main(String[] args) {
		SpringApplication.run(MongoDbExamplesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		repository.deleteAll();

		// save a couple of customers
		repository.save(new Customer("Alice", "Smith"));
		repository.save(new Customer("Bob", "Smith"));

		// fetch all customers
		System.out.println("Customers found with findAll():");
		System.out.println("-------------------------------");
		for (Customer customer : repository.findAll()) {
			System.out.println(customer);
		}
		System.out.println();

		// fetch an individual customer
		System.out.println("Customer found with findByFirstName('Alice'):");
		System.out.println("--------------------------------");
		System.out.println(repository.findByFirstName("Alice"));

		System.out.println("Customers found with findByLastName('Smith'):");
		System.out.println("--------------------------------");
		for (Customer customer : repository.findByLastName("Smith")) {
			System.out.println(customer);
		}
		
		
		
		
//		Domain obj = domainRepository.findOne(7L);
//        System.out.println(obj);
		
		Domain d = new Domain();
		d.setDisplayAds(true);
		d.setDomain("www.apple.com");
		d.setId(1);
		
		domainRepository.save(d);
		
		List<Domain> all = domainRepository.findAll();
		if (all != null) {
			all.forEach(dx -> System.out.println(dx));
		}

        Domain obj2 = domainRepository.findFirstByDomain("mkyong.com");
        System.out.println(obj2);

        long n = domainRepository.updateDomain("mkyong.com", true);
        System.out.println("Number of records updated : " + n);
	}
}
