package es.iesvjp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.iesvjp.model.Categoria;
import es.iesvjp.model.Producto;

/**
 * Interfaz del repositorio para los Productos. Extiende de JpaRepository
 * @author Jorge Luis Fernández Díaz
 * @version 31.01.2022
 */
@Repository("productoRepository")
public interface IProductoRepository extends JpaRepository<Producto, Long>{
	
	/* Utilizando un Long como id en el Modelo, JpaRepository tiene implementado
	 * los métodos 'findById' para que devuelvan un Optional en lugar del objeto en si.
	 * Optional es un patrón que se usa para evitar que se devuelva null.
	 * En cambio, para obtener el objeto Producto, deberemos de llamar explicitamente
	 * a un método (.get()). 
	 */	
	
	public Optional<Producto> findById(Long id);
	public List<Producto> findByCategoria(Categoria categoria); //Devuelve la lista de productos de una determinada categoría 
	
	/* Creamos este método a partir de una consulta JPQL. Una subconsulta
	 * buscará el valor máximo del campo 'descuento' en todos los productos de la base de datos.
	 * Luego listaremos los productos que coincidan con ese valor.
	 */
	@Query("select p from Producto p where p.descuento = (select max(p.descuento) from Producto p)")
	public List<Producto> findProductosMasDescuento();
}
