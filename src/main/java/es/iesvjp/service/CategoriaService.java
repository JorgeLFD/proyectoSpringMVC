package es.iesvjp.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import es.iesvjp.model.Categoria;
import es.iesvjp.repository.ICategoriaRepository;

/**
 * Servicio para Categoría. Implementa los métodos necesarios para obtener
 * datos del repositorio.
 * @author Jorge Luis Fernández Díaz
 * @version 31.01.2022
 */
@Service("categoriaService")
public class CategoriaService implements ICategoriaService{
	
	@Autowired
	@Qualifier("categoriaRepository")
	private ICategoriaRepository categoriaRepository;
	
	
	/**
	 * Devuelve una lista con todas las categorías de la BBDD.
	 */
	@Override
	public List<Categoria> listAllCategorias() {
		List<Categoria> categorias = categoriaRepository.findAll();
		return categorias;
	}
	
	
	/**
	 * Añade una categoría a la base de datos.
	 * El método save hara un INSERT si la categoría no existe.
	 * Si ya existe, hará un UPDATE.
	 */
	@Override
	public void addCategoria(Categoria categoria) {
		categoriaRepository.save(categoria);
	}
	
	
	/**
	 * Busca la categoría en el repositorio y la devuelve a través del Optional
	 */
	@Override
	public Optional<Categoria> findCategoriaById(Long id) {
		return categoriaRepository.findById(id);
	}
	
	
	/**
	 * Elimina la categoría con la id que le llega por parámetros
	 */
	@Override
	public void removeCategoria(Long id){
		Optional<Categoria> categoria = findCategoriaById(id);
		categoriaRepository.delete(categoria.get());
	}

}
