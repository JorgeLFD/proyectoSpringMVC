package es.iesvjp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.iesvjp.model.Categoria;

/**
 * Interfaz del repositorio para las categorías. Extiende de JpaRepository
 * @author Jorge Luis Fernández Díaz
 * @version 31.01.2022
 */
@Repository("categoriaRepository")
public interface ICategoriaRepository extends JpaRepository<Categoria, Long>{
	
	/* Utilizando un Long como id en el Modelo, JpaRepository tiene implementado
	 * los métodos 'findById' para que devuelvan un Optional en lugar del objeto en si.
	 * Optional es un patrón que se usa para evitar que se devuelva null.
	 * En cambio, para obtener el objeto Categoría, deberemos de llamar explicitamente
	 * a un método (.get()).
	 */
	
	public Optional<Categoria> findById(Long id); 
}
