package com.ipartek.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ipartek.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
	
	@Query(value = "SELECT * FROM Usuarios WHERE usuario= :user and pass= :passw", nativeQuery = true)
	Usuario validarUsuario(@Param("user") String usu,@Param("passw") String pas);
	
	@Query(value = "SELECT * FROM Usuarios WHERE usuario= :user", nativeQuery = true)
	Usuario validarUsuario(@Param("user") String usu);

}
