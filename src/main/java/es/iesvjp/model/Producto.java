package es.iesvjp.model;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Modelo para Producto.
 * @author Jorge Luis Fernández Díaz
 * @version 31.01.2022
 */
@Entity
public class Producto{	

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "descripcion", nullable = false, length=512)
	@NotBlank(message = "La descripción del producto no puede estar en blanco.")
	@Size(max = 512, message = "No se pueden introducir más de 512 caracteres.")
	private String descripcion;
	
	@Column(name = "descuento")
	@DecimalMax(value = "1.00", message = "El descuento debe ser menor o igual que 0.99")
	@DecimalMin(value = "0.00", message = "El descuento debe ser mayor o igual que 0")
	private float descuento;
	
	@Column(name = "imagen", nullable = true, length=512)
	@NotBlank(message = "El campo Imagen no puede estar en blanco.")
	@Size(max = 512, message = "No se pueden introducir más de 512 caracteres.")
	private String imagen;
	
	@Column(name = "nombre", nullable = false, length=512)
	@NotBlank(message = "El nombre del Producto no puede estar en blanco.")
	@Size(max = 512, message = "No se pueden introducir más de 512 caracteres.")
	private String nombre;
	
	@Column(name = "pvp", nullable = false)
	@NotNull(message = "El campo Precio no puede estar en blanco.")
	@Positive(message = "El precio debe ser positivo")
	private float pvp;

	//Parte 'Many-to-one' de la asociación bidireccional con Categoría. Lado 'hijo'.
	@ManyToOne
	@JoinColumn(name="idCategoria")
	@NotNull(message = "Debes seleccionar una categoría")
	private Categoria categoria;

	//Parte 'One-to-many' de la associación bidireccional con Puntuacion. Lado 'padre'.
	//En este caso si se borra un producto se borrarán todas las puntuaciones asociadas
	@OneToMany(mappedBy="producto", fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Puntuacion> puntuaciones;

	public Producto() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public float getDescuento() {
		return this.descuento;
	}

	public void setDescuento(float descuento) {
		this.descuento = descuento;
	}

	public String getImagen() {
		return this.imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public float getPvp() {
		return this.pvp;
	}

	public void setPvp(float pvp) {
		this.pvp = pvp;
	}

	public Categoria getCategoria() {
		return this.categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public List<Puntuacion> getPuntuacions() {
		return this.puntuaciones;
	}

	public void setPuntuacions(List<Puntuacion> puntuacions) {
		this.puntuaciones = puntuacions;
	}

	public Puntuacion addPuntuacion(Puntuacion puntuacion) {
		getPuntuacions().add(puntuacion);
		puntuacion.setProducto(this);

		return puntuacion;
	}

	public Puntuacion removePuntuacion(Puntuacion puntuacion) {
		getPuntuacions().remove(puntuacion);
		puntuacion.setProducto(null);

		return puntuacion;
	}
	
	/*
	 * Método que devuelve la media de las puntuaciones. 
	 */
	public float getAverageRating() {
		float avg = 0.0f;
		//Sumamos todas las puntuaciones asociadas al producto.
		for(int i = 0; i < this.puntuaciones.size(); i++) {
			avg += this.puntuaciones.get(i).getPuntuacion();
		}
		
		try {
			//Dividimos la suma por el número de puntuaciones para obtener la media.
			avg = avg / this.puntuaciones.size();
		} catch(ArithmeticException e) {
			//Capturamos la excepción si intentamos dividir por 0.
		}
		return avg;
	}

}
