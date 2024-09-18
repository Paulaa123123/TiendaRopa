package com.ipartek.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ipartek.model.Talla;

@Repository
public interface TallaRepository extends JpaRepository<Talla, Integer> {

}
