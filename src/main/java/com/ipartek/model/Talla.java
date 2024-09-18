package com.ipartek.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tallas")
public class Talla {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "talla")
	private String talla;

	@OneToMany
	private List<Producto> listaProductos;

	public Talla(int id, String talla, List<Producto> listaProductos) {
		super();
		this.id = id;
		this.talla = talla;
		this.listaProductos = listaProductos;
	}

	public Talla() {
		super();
		this.id = 0;
		this.talla = "";
		this.listaProductos = new ArrayList<Producto>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTalla() {
		return talla;
	}

	public void setTalla(String talla) {
		this.talla = talla;
	}

	public List<Producto> getListaProductos() {
		return listaProductos;
	}

	public void setListaProductos(List<Producto> listaProductos) {
		this.listaProductos = listaProductos;
	}

	@Override
	public String toString() {
		return "Talla [id=" + id + ", talla=" + talla + ", listaProductos=" + listaProductos + "]";
	}

}
