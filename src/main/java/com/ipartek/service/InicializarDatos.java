package com.ipartek.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipartek.model.Categoria;
import com.ipartek.model.Genero;
import com.ipartek.repository.CategoriaRepository;
import com.ipartek.repository.GeneroRepository;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;

@Service
public class InicializarDatos {
	
	@Autowired
	private CategoriaRepository categoriaRepo ;
	
	@Autowired
	private GeneroRepository generoRepo ;
	
//	@PostConstruct
//	@Transactional
//	public void inicializarDatos() {
//		//lista de generos
//		generoRepo.save(new Genero(1, "Hombre"));
//		generoRepo.save(new Genero(2, "Mujer"));
//		generoRepo.save(new Genero(3, "Infantil"));
//		
//		//equipos en las aulas
//		categoriaRepo.save(new Categoria(1, "Pantalones"));
//		categoriaRepo.save(new Categoria(2, "Faldas"));
//		categoriaRepo.save(new Categoria(3, "Camisetas"));
//		categoriaRepo.save(new Categoria(4, "Camisas"));
//		
//		
//		
//		
//	
//	}

}
