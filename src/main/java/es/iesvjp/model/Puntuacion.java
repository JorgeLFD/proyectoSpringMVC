package es.iesvjp.model;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;

/**
 * Modelo para Puntuacion.
 * @author Jorge Luis Fernández Díaz
 * @version 31.01.2022
 */
@Entity
public class Puntuacion {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	@Column(name = "puntuacion", nullable = false)
	@Min(value = 1)
    @Max(value = 5)
	private int puntuacion;

	//Parte 'Many-to-one' de la asociación bidireccional con Producto. Lado 'hijo'.
	@ManyToOne
	@JoinColumn(name="idProducto")
	private Producto producto;

	public Puntuacion() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getPuntuacion() {
		return this.puntuacion;
	}

	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}

	public Producto getProducto() {
		return this.producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

}
