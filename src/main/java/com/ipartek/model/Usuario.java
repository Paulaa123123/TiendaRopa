package com.ipartek.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="usuarios")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="usuario")
	private String usuario;
	
	@Column(name="pass")
	private String pass;
	
	@ManyToOne
	private Rol rol;

	public Usuario(int id, String usuario, String pass, Rol rol) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.pass = pass;
		this.rol = rol;
	}
	
	public Usuario() {
		super();
		this.id = 0;
		this.usuario = "";
		this.pass = "";
		this.rol = new Rol();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", usuario=" + usuario + ", pass=" + pass + ", rol=" + rol + "]";
	}
	
	
	
	

}
