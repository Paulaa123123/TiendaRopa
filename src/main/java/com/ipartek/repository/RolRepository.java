package com.ipartek.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ipartek.model.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {

	
	
}
