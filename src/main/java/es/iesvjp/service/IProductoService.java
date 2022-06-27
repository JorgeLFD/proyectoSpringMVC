package es.iesvjp.service;

import java.util.List;
import java.util.Optional;
import es.iesvjp.model.Categoria;
import es.iesvjp.model.Producto;

/**
 * Interfaz para el servicio de los Productos
 * @author Jorge Luis Fernández Díaz
 * @version 31.01.2022
 */
public interface IProductoService {
	public Producto addProducto(Producto producto);
	public List<Producto> listAllProductos();
	public Optional<Producto> findProductoById(Long id);
	public void removeProducto(Long id);
	public List<Producto> listProductosByCategoria(Categoria categoria);
	public List<Producto> listProductosMasDescuento();
}
