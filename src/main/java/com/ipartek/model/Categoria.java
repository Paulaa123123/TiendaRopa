package com.ipartek.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * <p><b>Clase Categoria</b></p>
 * 
 * <p>Clase DTO para generar de manera automatica la tabla categorias dentro de la BD</p>
 * 
 * <p>Esta clase hereda de la clase Mother para por polimorfismo poder ayudarnos a
 * tener menos funciones en la creacion de las copias de seguridad en CSV</p>
 * 
 * @author Paula
 * 
 * @version 1.0
 * 
 * @see Mother
 */

@Entity
@Table(name="categorias")
public class Categoria {
	
	/**
	 * <p><b>Atributo ID</b></p>
	 * 
	 * <p>Este parametro con las anotaciones que lleva genera el campo "id" dentro de la tabla categorias
	 * en la base de datos</p>
	 * 
	 * <p>Se generara como autonumerico por el Strategy Indentity, not null
	 * y primary key por la anotacion ID.
	 * 
	 * 
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="categoria", length = 45)
	private String categoria;

	/**
	 * <p><b>Constructor completo</b></p>
	 * 
	 * <p>Este constructor nos reservará espacio en memoria y construirá el objeto
	 * Categoria con los parametros que enviemos</p>
	 * 
	 * <p>Observaciones</p>
	 * <ul>
	 * 		<li>Si guardamos en la BD, el id no debera estar registrado en esta, si lo esta,
	 * 		nos modificarra ese registro</li>
	 * 		<li>El parametro categoria deberia estar limitado a 45 caracteres, si no llega,
	 * 		recorta la parte sobrante</li>
	 * </ul>
	 * 
	 * 
	 * @param id El identificador de la BD, valor int
	 * @param categoria
	 */
	
	public Categoria(int id, String categoria) {
		super();
		this.id = id;
		this.categoria = categoria;
	}
	
	
	/**
	 * <p><b>Constructor vacio</b></p>
	 * 
	 * <p>Este constructor nos reservara espacio en memoria y construira el objeto
	 * Categoria 
	 */
	public Categoria() {
		super();
		this.id = 0;
		this.categoria = "";
	}

	public int getId() {
		return id;
	}

	/**
	 * <p><b>Metodo Setter para el campo ID</b></p>
	 * 
	 * <p>Modifica el parametro ID dentro del objeto Categoria<p>
	 * 
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	@Override
	public String toString() {
		return "Categoria [id=" + id + ", categoria=" + categoria + "]";
	}
	
	
	
}
