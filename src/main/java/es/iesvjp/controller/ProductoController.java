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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.PathVariable;
import es.iesvjp.model.Producto;
import es.iesvjp.service.ICategoriaService;
import es.iesvjp.service.IProductoService;

/**
 * Controlador para la administración de los Productos.
 * @author Jorge Luis Fernández Díaz
 * @version 31.01.2022
 */
@Controller
@RequestMapping("admin/producto")
public class ProductoController {
	
	@Autowired
	@Qualifier("productoService")
	private IProductoService productoService;

	@Autowired
	@Qualifier("categoriaService")
	private ICategoriaService categoriaService;
	
	
	/**
	 * Creamos un ModelandView a partir de la plantilla list-producto.
	 * Le añadimos la lista de todos los productos para que se puedan mostrar en la vista.
	 * @return ModelAndView
	 */
	//localhost:9000/admin/producto
	@GetMapping("/")
	private ModelAndView adminProductos() {
		ModelAndView mav = new ModelAndView("admin/list-producto");
		mav.addObject("productos", productoService.listAllProductos());
		return mav;
	}
	
	
	/**
	 * Este método sirve para mostrar el formulario del producto cuando queremos añadir uno nuevo.
	 * Creamos un producto 'vacío' para pasarsela a la vista. 
	 * @param modelo
	 * @return el formulario del producto
	 */
	//localhost:9000/admin/producto/nuevo
	@GetMapping("/nuevo")
	private String showProductoForm(Model model)  {
		Producto producto = new Producto();
		model.addAttribute("producto", producto);
		model.addAttribute("categorias", categoriaService.listAllCategorias()); //Le añadimos también las categorías para que aparezcan en el desplegable
		return "admin/form-producto";
	}
	
	
	
	/**
	 * Recibimos el producto con los datos del formulario. Si pasa las validaciones se llama al servicio
	 * para que lo persista en la base de datos. Si no, se vuelve a mostrar el formulario con los errores.
	 * @param producto
	 * @param result
	 * @param model
	 * @return
	 */
	@PostMapping("/nuevo/addProducto")
	private String addProducto(@Valid @ModelAttribute("producto") Producto producto, BindingResult result, Model model) {
		if(!result.hasErrors()) {
			productoService.addProducto(producto);
			return "redirect:/admin/producto/";
		}
		model.addAttribute("categorias", categoriaService.listAllCategorias());
		return "admin/form-producto";
	}
	
	
	
	/**
	 * Buscamos el producto con la id que llega como variable en el path.
	 * El servicio busca el producto y devuelve el Optional que lo contiene.
	 * Añadimos ese producto al modelo y devolvemos el formulario con esos datos.
	 * También añadimos las categorías para que se muestre el desplegable.
	 * @param id
	 * @param model
	 * @return
	 */
	//localhost:9000/admin/producto/editar/id
	@GetMapping("/editar/{id}")
	private String editProducto(@PathVariable("id") Long id, Model model) {		
		Optional<Producto> producto = productoService.findProductoById(id);
		model.addAttribute("categorias", categoriaService.listAllCategorias());
		model.addAttribute("producto", producto.get());				
		return "admin/form-producto";
	}
	
	
	/**
	 * Recibimos como variable en el path la id del producto que vamos a borrar.
	 * Llamamos al método del servicio que bora el producto dandole su id.
	 * @param id
	 * @param model
	 * @return
	 */
	//localhost:9000/admin/producto/borrar/id
	@GetMapping("/borrar/{id}")
	private String deleteProducto(@PathVariable("id") Long id) {		
		productoService.removeProducto(id);		
		return "redirect:/admin/producto/";
	}
}
