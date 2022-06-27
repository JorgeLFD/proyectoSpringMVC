package es.iesvjp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import es.iesvjp.model.Categoria;
import es.iesvjp.model.Producto;
import es.iesvjp.repository.IProductoRepository;

/**
 * Servicio para Productos. Implementa los métodos necesarios para obtener
 * datos del repositorio.
 * @author Jorge Luis Fernández Díaz
 * @version 31.01.2022
 */
@Service("productoService")
public class ProductoService implements IProductoService {
	
	@Autowired
	@Qualifier("productoRepository")
	private IProductoRepository productoRepository;
	
	
	/**
	 * Devuelve una lista de todos los productos de la BBDD
	 */
	@Override
	public List<Producto> listAllProductos() {
		List<Producto> productos = productoRepository.findAll();
		return productos;
	}
		
	/**
	 * Añade un producto a la base de datos.
	 * El método save hará un INSERT si el producto es nuevo.
	 * Si ya existe, hará un UPDATE.
	 */
	@Override
	public Producto addProducto(Producto producto) {
		Producto producto1 = productoRepository.save(producto);
		return producto1;
	}
	
	/**
	 * Busca el producto en el repositorio por su id y lo devuelve a través del Optional
	 */
	@Override
	public Optional<Producto> findProductoById(Long id) {
		return productoRepository.findById(id);
	}
	
	/**
	 * Elimina la categoría con la id que le llega por parámetros
	 */
	@Override
	public void removeProducto(Long id) {
		Optional<Producto> producto = findProductoById(id);
		productoRepository.delete(producto.get());
	}
	
	
	/**
	 * Retorna una lista de productos dada su categoría.
	 */
	@Override
	public List<Producto> listProductosByCategoria(Categoria categoria){
		return productoRepository.findByCategoria(categoria);
	}
	
	
	/**
	 * Retorna la lista de productos con mayor desucento.
	 */
	@Override
	public List<Producto> listProductosMasDescuento(){
		return productoRepository.findProductosMasDescuento();
	}

}
