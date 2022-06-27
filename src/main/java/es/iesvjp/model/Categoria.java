package es.iesvjp.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Modelo para Categoria.
 * @author Jorge Luis Fernández Díaz
 * @version 31.01.2022
 */
@Entity
public class Categoria {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "destacada", nullable = false)
	private boolean destacada;
	
	@Column(name = "imagen", nullable = true, length=512)
	@NotBlank(message = "El nombre de la Categoría no puede estar en blanco.")
	@Size(max = 512, message = "No se pueden introducir más de 512 caracteres.")
	private String imagen;
	
	@Column(name = "nombre", nullable = false, length=512)
	@NotBlank(message = "El nombre de la Categoría no puede estar en blanco.")
	@Size(max = 512, message = "No se pueden introducir más de 512 caracteres.")
	private String nombre;

	//Parte 'One-to-many' de la associación bidireccional con Producto. Lado 'padre'.
	/* Para que se borren los productos asociados a una categoría cuando esta se borra,
	 * abría que poner esto:
	 * @OneToMany(mappedBy="categoria", cascade = CascadeType.ALL, orphanRemoval = true)
	 * Tal y como está ahora no se podrá, lanzará un error (el cual capturaremos en CategoriaController
	 * para indicarselo al usuario en la vista)	
	 */
	@OneToMany(mappedBy="categoria")
	private List<Producto> productos;

	public Categoria() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean getDestacada() {
		return this.destacada;
	}

	public void setDestacada(boolean destacada) {
		this.destacada = destacada;
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

	public List<Producto> getProductos() {
		return this.productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	public Producto addProducto(Producto producto) {
		getProductos().add(producto);
		producto.setCategoria(this);

		return producto;
	}

	public Producto removeProducto(Producto producto) {
		getProductos().remove(producto);
		producto.setCategoria(null);

		return producto;
	}

}
