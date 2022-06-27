package es.iesvjp.service;

import java.util.List;
import java.util.Optional;
import es.iesvjp.model.Categoria;

/**
 * Interfaz para el servicio de las categorías
 * @author Jorge Luis Fernández Díaz
 * @version 31.01.2022
 */
public interface ICategoriaService {
	public List<Categoria> listAllCategorias();
	public void addCategoria(Categoria categoria);
	public Optional<Categoria> findCategoriaById(Long id);
	public void removeCategoria(Long id);
}
