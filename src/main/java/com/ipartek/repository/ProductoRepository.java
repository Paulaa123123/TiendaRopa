package com.ipartek.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ipartek.model.Categoria;
import com.ipartek.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer>{

	
	
	
	
}
