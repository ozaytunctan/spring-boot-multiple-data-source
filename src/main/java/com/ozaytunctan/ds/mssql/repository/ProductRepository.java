package com.ozaytunctan.ds.mssql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ozaytunctan.ds.mssql.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

}
