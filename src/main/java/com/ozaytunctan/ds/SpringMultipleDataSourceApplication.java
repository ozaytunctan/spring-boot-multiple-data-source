package com.ozaytunctan.ds;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ozaytunctan.ds.h2.model.Person;
import com.ozaytunctan.ds.h2.repository.PersonRepository;
import com.ozaytunctan.ds.mssql.model.Product;
import com.ozaytunctan.ds.mssql.repository.ProductRepository;

@SpringBootApplication
public class SpringMultipleDataSourceApplication {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private PersonRepository personRepository;

	@PostConstruct
	public void initInsert() {

		Person p = new Person(0, "Ozay");
		this.personRepository.save(p);

		Product product = new Product();
		product.setId(0);
		product.setName("Bilgisayar");
		
		productRepository.save(product);

	}

	public static void main(String[] args) {
		SpringApplication.run(SpringMultipleDataSourceApplication.class, args);
	}

}
