package es.iesvjp.controller;

import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import es.iesvjp.model.Categoria;
import es.iesvjp.service.ICategoriaService;

/**
 * Controlador para la administración de las Categorías.
 * @author Jorge Luis Fernández Díaz
 * @version 31.01.2022
 */
@Controller
@RequestMapping("admin/categoria")
public class CategoriaController {

	@Autowired
	@Qualifier("categoriaService")
	private ICategoriaService categoriaService;
	
	
	/**
	 * Creamos un ModelandView a partir del html del listado de las categorías.
	 * Le añadimos la lista de todas las categorias para que se puedan mostrar en la vista.
	 * @return ModelAndView.
	 */
	//localhost:9000/admin/categoria
	@GetMapping("/")
	private ModelAndView adminCategorias() {
		ModelAndView mav = new ModelAndView("admin/list-categoria");
		mav.addObject("categorias", categoriaService.listAllCategorias());
		return mav;
	}
	
	/**
	 * Este método sirve para mostrar el formulario de la categoria.
	 * Creamos una sin datos para pasarsela a la vista.
	 * @param modelo
	 * @return el formulario de categoria
	 */
	//localhost:9000/admin/categoria/nueva
	@GetMapping("/nueva")
	private String showCategoriaForm(Model model)  {
		Categoria categoria = new Categoria();
		model.addAttribute("categoria", categoria);
		return "admin/form-categoria";
	}
	
	/**
	 * Recibimos la categoría con los datos del formulario. Si pasa las validaciones se llama al servicio
	 * para que añada la categoría. Si no, se vuelve a mostrar el formulario con los errores.
	 * @param categoria
	 * @param result resultado del formulario (para ver los errores)
	 * @return
	 */
	@PostMapping("/nueva/addCategoria")
	private String addCategoria(@Valid @ModelAttribute("categoria") Categoria categoria, BindingResult result) {
		if(!result.hasErrors()) {
			categoriaService.addCategoria(categoria);
			return "redirect:/admin/categoria/";
		}
		return "admin/form-categoria";
	}
	
	/**
	 * Buscamos la categoría con la id que llega como variable en el path.
	 * El servicio busca la categoría y devuelve el Optional con la categoría.
	 * Añadimos esa categoría al modelo y devolvemos el formulario con esos datos.
	 * @param id
	 * @param model
	 * @return
	 */
	//localhost:9000/admin/categoria/editar/id
	@GetMapping("/editar/{id}")
	private String editCategoria(@PathVariable("id") Long id, Model model) {		
		Optional<Categoria> categoria = categoriaService.findCategoriaById(id);
		model.addAttribute("categoria", categoria.get());
		return "admin/form-categoria";
	}
	
	/**
	 * Recibimos como variable en el path el id de la categoría que vamos a borrar. Llamamos al método
	 * del servicio que borra la Categoria, pasándole ese id. En el caso de que queramos borrar una 
	 * categoría que contiene productos, nos dará un error, ya que no pusimos el Cascade.ALL ni el 
	 * orphanRemoval en la entidad Categoria.java. En cambio, capturaremos la excepción y le añadiremos como atributos
	 * de redirección. Cuando redireccionemos, la plantilla mostrará el error.
	 * @param id
	 * @param model
	 * @param ra
	 * @return
	 */
	//localhost:9000/admin/categoria/borrar/id
	@GetMapping("/borrar/{id}")
	private String deleteCategoria(@PathVariable("id") Long id, Model model, RedirectAttributes ra) {
		try {
			categoriaService.removeCategoria(id);			
		}
		catch(org.springframework.dao.DataIntegrityViolationException e) { //Capturamos la excepción
			ra.addFlashAttribute("error", 1);
		}
		return "redirect:/admin/categoria/";
	}
}
